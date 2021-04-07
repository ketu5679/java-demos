package org.apache.ranger.authorization.hive.authorizer;

import groovy.util.logging.Slf4j;
import org.mortbay.log.Log;

import java.util.HashSet;

/**
 * @Author: zjh
 * @Date: 2021/1/5 17:30
 */
public class Test {
    public static void main(String[] args) {
        RangerHivePlugin plugin = new RangerHivePlugin("hive");
        plugin.init();

        RangerHiveResource resource = null;
        resource = new RangerHiveResource(HiveObjectType.TABLE, "dm", "dm_test_d");
//        resource = new RangerHiveResource(HiveObjectType.DATABASE, "dm", null);
        RangerHiveAccessRequest request = new RangerHiveAccessRequest(resource, "10000114", new HashSet<>(), "hiveOpType.name()", HiveAccessType.SELECT, null, null);
        System.out.println("1. {}"+ plugin.isAccessAllowed(request).getIsAllowed());
        request.setResource(new RangerHiveResource(HiveObjectType.TABLE, "dm", "dm_tests_d"));
        System.out.println("1. {}"+ plugin.isAccessAllowed(request).getIsAllowed());
    }
}
