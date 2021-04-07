package org.apache.ranger.authorization.hive.authorizer;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: zjh
 * @Date: 2021/1/5 17:30
 */
public class Test {
    private static RangerHivePlugin plugin;

    public static void main(String[] args) {
        plugin = new RangerHivePlugin("hive");
        plugin.init();

        System.out.println("1. "+ isHasSelectAuth("dm", "dm_test_d", "10000114"));
        System.out.println("1. "+ isHasSelectAuth("dm", "dm_test_d1", "10000114"));
        System.out.println("1. "+ isHasSelectAuth("dm", "dm_xx_d1", "10000114"));
        System.out.println("1. "+ isHasSelectAuth("rs", "dm_xx_d1", "10000114"));
        System.out.println("1. "+ isHasSelectAuth("rs", "b", "10000114"));
    }

    public static boolean isHasSelectAuth(String db, String tableName, String empId) {
        Set<String> groups = new HashSet<>();
        groups.add("ai");

        RangerHiveResource resource = new RangerHiveResource(HiveObjectType.TABLE, db, tableName);
        RangerHiveAccessRequest request = new RangerHiveAccessRequest(resource, empId, groups, "", HiveAccessType.SELECT, null, null);
        return plugin.isAccessAllowed(request).getIsAllowed();
    }
}
