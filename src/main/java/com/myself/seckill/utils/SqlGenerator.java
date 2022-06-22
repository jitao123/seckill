package com.myself.seckill.utils;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author :AT
 * @date : 2022/6/21 2:30 下午
 */
public class SqlGenerator {
    public static Map javaProperty2SqlColumnMap = new HashMap<>();

    static {
        javaProperty2SqlColumnMap.put("Integer", "int");
        javaProperty2SqlColumnMap.put("Short", "tinyint(4)");
        javaProperty2SqlColumnMap.put("Long", "bigint");
        javaProperty2SqlColumnMap.put("BigDecimal", "decimal(19,2)");
        javaProperty2SqlColumnMap.put("Double", "double precision not null");
        javaProperty2SqlColumnMap.put("Float", "float");
        javaProperty2SqlColumnMap.put("boolean", "bit(1)");
        javaProperty2SqlColumnMap.put("Timestamp", "datetime");
        javaProperty2SqlColumnMap.put("Date", "datetime");
        javaProperty2SqlColumnMap.put("LocalDateTime", "datetime");
        javaProperty2SqlColumnMap.put("String", "varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL");
    }

    private static final Logger logger = LoggerFactory.getLogger(SqlGenerator.class);

    public static void main(String[] args) {
        //实体类所在的package在磁盘上的绝对路径
        String packageName = "";
        //生成sql的文件夹 文件的绝对路径
        String filePath = "";
        //项目中实体类的路径 com.*.entity.  路径+ .
        String prefix = "";
        String className = "";

        StringBuffer sqls = new StringBuffer();
        //获取包下的所有类名称
        List<String> list = getAllClasses(packageName);
        for (String str : list) {
            className = prefix + str.substring(0, str.lastIndexOf("."));
            String sql = generateSql(className, "");
            sqls.append(sql);
        }
        System.out.println(sqls.toString());
        StringToSql(sqls.toString(), filePath);

    }
    /**
     * 根据实体类生成建表语句
     * @author
     * @date
     * @param className 全类名
     * @param tableName 表名
     */
    public static String generateSql(String className, String tableName){
        try {
            Class clz = Class.forName(className);
            className = clz.getSimpleName();
            Field[] fields = clz.getDeclaredFields();
            String param = "";
            String column = "";
            StringBuilder sql = null;
            sql = new StringBuilder(50);
            if (tableName == null || ("").equals(tableName)) {
                tableName = clz.getName();
                tableName = tableName.substring(tableName.lastIndexOf(".") + 1);
            }

            sql.append("\n\n/*========= "+tableName+" ==========*/\n");
            sql.append("DROP TABLE IF EXISTS `"+className+"`; \n");
            sql.append("CREATE TABLE `").append(tableName).append("` ( \n");

            String keyField = "";
            for (Field f : fields) {
                column = f.getName();
                if (("serialVersionUID").equals(column)) {
                    continue;
                }
                param = f.getType().getSimpleName();

                //将大写字母转小写，并添加下划线
                for (int i = 0; i < column.length(); i++) {
                    char c = column.charAt(i);
                    if (Character.isUpperCase(c) && i>0) {
                        column = column.replaceAll(
                                Character.toString(c), "_"+Character.toLowerCase(c));
                    }
                }

                sql.append("`"+ column +"`");
                sql.append(" ").append(javaProperty2SqlColumnMap.get(param));
                //默认第一个是主键
                if ("".equals(keyField)) {
                    keyField = column;
                }
                sql.append(",\n");
            }
            sql.append("PRIMARY KEY (`"+keyField+"`) USING BTREE,");
            sql.append("\nINDEX `"+keyField+"`(`"+keyField+"`) USING BTREE ) ");
            sql.append("\nENGINE = INNODB DEFAULT CHARSET= utf8;");

            return sql.toString();

        } catch (ClassNotFoundException e) {
            logger.debug("该类未找到！");
            return null;
        }
    }

    /**
     * 获取包下的所有类名称,获取的结果类似于 XXX.java
     * @author
     * @date
     * @param packageName
     * @return
     */
    public static List getAllClasses(String packageName){
        List<String> classList = new ArrayList();
        String className="";
        File f = new File(packageName);
        if(f.exists() && f.isDirectory()){
            File[] files = f.listFiles();
            for (File file : files) {
                className = file.getName();
                classList.add(className);
            }
            return classList;
        }else{
            logger.debug("包路径未找到！");
            return null;
        }
    }
    /**
     * 将string 写入sql文件
     * @author
     * @date
     * @param str
     * @param path
     */
    public static void StringToSql(String str,String path){
        byte[] sourceByte = str.getBytes();
        if(null != sourceByte){
            try {
                //文件路径（路径+文件名）
                File file = new File(path);
                //文件不存在则创建文件，先创建目录
                if (!file.exists()) {
                    File dir = new File(file.getParent());
                    dir.mkdirs();
                    file.createNewFile();
                }
                //文件输出流用于将数据写入文件
                FileOutputStream outStream = new FileOutputStream(file);
                outStream.write(sourceByte);
                outStream.flush();
                outStream.close();
                System.out.println("生成成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
}
