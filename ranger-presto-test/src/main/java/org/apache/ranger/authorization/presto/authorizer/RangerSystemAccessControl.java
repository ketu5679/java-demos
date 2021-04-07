/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.ranger.authorization.presto.authorizer;

import io.prestosql.spi.connector.CatalogSchemaName;
import io.prestosql.spi.connector.CatalogSchemaTableName;
import io.prestosql.spi.connector.SchemaTableName;
import io.prestosql.spi.security.*;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.ranger.plugin.audit.RangerDefaultAuditHandler;
import org.apache.ranger.plugin.policyengine.RangerAccessRequestImpl;
import org.apache.ranger.plugin.policyengine.RangerAccessResourceImpl;
import org.apache.ranger.plugin.policyengine.RangerAccessResult;
import org.apache.ranger.plugin.policyengine.RangerPolicyEngine;
import org.apache.ranger.plugin.service.RangerBasePlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.*;

import static java.util.Locale.ENGLISH;

public class RangerSystemAccessControl{

  public static String RANGER_CONFIG_KEYTAB = "ranger.keytab";
  public static String RANGER_CONFIG_PRINCIPAL = "ranger.principal";

  public static String RANGER_PRESTO_SERVICETYPE = "presto";
  public static String RANGER_PRESTO_APPID = "presto";

  private static Logger LOG = LoggerFactory.getLogger(RangerSystemAccessControl.class);

  private RangerBasePlugin rangerPlugin;



  public RangerSystemAccessControl() {
//    rangerPlugin = new RangerBasePlugin("hive", "hive");
    rangerPlugin = new RangerBasePlugin("presto", "presto");
    rangerPlugin.init();
    rangerPlugin.setResultProcessor(new RangerDefaultAuditHandler());
  }
  public static void main(String[] args) {
    RangerSystemAccessControl r = new RangerSystemAccessControl();
    boolean res = r.checkPermission(createResource("hive",
            "dm",
            "dm_test_d", Optional.empty())
            , "10000114", PrestoAccessType.SELECT);
    System.out.println(res);
  }

  private boolean checkPermission(RangerPrestoResource resource, String user, PrestoAccessType accessType) {
    boolean ret = false;

    UserGroupInformation ugi = UserGroupInformation.createRemoteUser(user);

    String[] groups = ugi != null ? ugi.getGroupNames() : null;

    Set<String> userGroups = null;
    if (groups != null && groups.length > 0) {
      userGroups = new HashSet<>(Arrays.asList(groups));
    }

    RangerPrestoAccessRequest request = new RangerPrestoAccessRequest(
      resource,
      user,
      userGroups,
      accessType
    );

    RangerAccessResult result = rangerPlugin.isAccessAllowed(request);
    if (result != null && result.getIsAllowed()) {
      ret = true;
    }

    return ret;
  }

  private static RangerPrestoResource createResource(String catalogName, String schemaName, final String tableName, final Optional<String> column) {
    return new RangerPrestoResource(catalogName, Optional.of(schemaName), Optional.of(tableName), column);
  }

  private static List<RangerPrestoResource> createResource(CatalogSchemaTableName table, Set<String> columns) {
    List<RangerPrestoResource> colRequests = new ArrayList<>();

    if (columns.size() > 0) {
      for (String column : columns) {
        RangerPrestoResource rangerPrestoResource = createResource(table.getCatalogName(),
          table.getSchemaTableName().getSchemaName(),
          table.getSchemaTableName().getTableName(), Optional.of(column));
        colRequests.add(rangerPrestoResource);
      }
    } else {
      colRequests.add(createResource(table.getCatalogName(),
        table.getSchemaTableName().getSchemaName(),
        table.getSchemaTableName().getTableName(), Optional.empty()));
    }
    return colRequests;
  }
}

class RangerPrestoResource
  extends RangerAccessResourceImpl {


  public static final String KEY_CATALOG = "catalog";
  public static final String KEY_SCHEMA = "schema";
  public static final String KEY_TABLE = "table";
  public static final String KEY_COLUMN = "column";

  public RangerPrestoResource() {}

  public RangerPrestoResource(String catalogName, Optional<String> schema, Optional<String> table) {
    setValue(KEY_CATALOG, catalogName);
    if (schema.isPresent()) {
      setValue(KEY_SCHEMA, schema.get());
    }
    if (table.isPresent()) {
      setValue(KEY_TABLE, table.get());
    }
  }

  public RangerPrestoResource(String catalogName, Optional<String> schema, Optional<String> table, Optional<String> column) {
    setValue(KEY_CATALOG, catalogName);
    if (schema.isPresent()) {
      setValue(KEY_SCHEMA, schema.get());
    }
    if (table.isPresent()) {
      setValue(KEY_TABLE, table.get());
    }
    if (column.isPresent()) {
      setValue(KEY_COLUMN, column.get());
    }
  }

  public String getCatalogName() {
    return (String) getValue(KEY_CATALOG);
  }

  public String getTable() {
    return (String) getValue(KEY_TABLE);
  }

  public String getCatalog() {
    return (String) getValue(KEY_CATALOG);
  }

  public String getSchema() { return (String) getValue(KEY_SCHEMA); }

  public Optional<SchemaTableName> getSchemaTable() {
    final String schema = getSchema();
    if (StringUtils.isNotEmpty(schema)) {
      return Optional.of(new SchemaTableName(schema, Optional.ofNullable(getTable()).orElse("*")));
    }
    return Optional.empty();
  }
}

class RangerPrestoAccessRequest
  extends RangerAccessRequestImpl {
  public RangerPrestoAccessRequest(RangerPrestoResource resource,
                                   String user,
                                   Set<String> userGroups,
                                   PrestoAccessType prestoAccessType)

  {
    super(resource,
      prestoAccessType == PrestoAccessType.USE ? RangerPolicyEngine.ANY_ACCESS :
        prestoAccessType == PrestoAccessType.ADMIN ? RangerPolicyEngine.ADMIN_ACCESS :
          prestoAccessType.name().toLowerCase(ENGLISH), user,
      userGroups);
  }
}

enum PrestoAccessType {
  CREATE, DROP, SELECT, INSERT, DELETE, USE, ALTER, ALL, ADMIN;
}