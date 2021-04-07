-- ----------------------------
-- Table structure for test
-- ----------------------------

DROP TABLE IF EXISTS `test`;
-- 创建一个表，指定了4个属性：id、年龄、身高、体重。最后指定了id是唯一不能重复的键值
CREATE TABLE IF NOT EXISTS `test`
(
    `id`   int         not null auto_increment,
    `name` varchar(20) NOT NULL,
    PRIMARY KEY (`id`)
);

