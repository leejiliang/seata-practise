//package com.seata.util.mybatis;
//
//
//import com.baomidou.mybatisplus.annotation.DbType;
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.core.toolkit.StringPool;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.InjectionConfig;
//import com.baomidou.mybatisplus.generator.config.*;
//import com.baomidou.mybatisplus.generator.config.po.TableInfo;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MybatisCodeGenerator {
//    public static final String bashPath = "";
//    public static final String jdbcUrl = "jdbc:mysql://localhost:3307/seata_uk?useUnicode=true&characterEncoding=utf8";
//    public static final String jdbcName = "uk";
//    public static final String jdbcPwd = "123456";
//
////    public static final String[] includeTables = {"t_order"};
////
////    public static final String partentPackage = "com.seata.order.entity";
//
//    public static void createEntity(String packages, String ...includeTables){
//        AutoGenerator mpg = new AutoGenerator();
//
//        //todo 可能需要修改，到当前项目主目录
//        String projectPath = System.getProperty("user.dir");
//        String mapperXmlPath = "/templates/mapper.xml.vm";
//
//        //全局配置
//        globalConfig(mpg, projectPath);
//        // 数据源配置
//        dataSourceConfig(mpg);
//        //模版配置
//        templateConfig(mpg);
//
//        // 自定义配置，即使模版配置设置了不生成xml，自定义配置重新定义了xml生成路径，依旧会生成，自定义配置优先级高
//        cfg(mpg, projectPath, mapperXmlPath);
//
//        // 策略配置
//        strategy(mpg, includeTables);
//
//        // 包配置
//        packageInfo(mpg, packages);
//
//        // 执行生成
//        mpg.execute();
//    }
//
//    private static void globalConfig(AutoGenerator mpg, String projectPath) {
//        // 全局配置
//        GlobalConfig gc = new GlobalConfig();
//        gc.setAuthor("ukar");
//        //改成自己的项目路径
//        gc.setOutputDir(projectPath + "/src/main/java");
//        gc.setFileOverride(false);// 是否覆盖同名文件，默认是false
//        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
//        gc.setEnableCache(false);// XML 二级缓存
//        gc.setBaseResultMap(true);// XML ResultMap
//        gc.setBaseColumnList(true);// XML columList
//        gc.setIdType(IdType.AUTO);// 主键生成类型
//
//        // 自定义文件命名，注意 %s 会自动填充表实体属性！
//        gc.setMapperName("%sMapper");
//        gc.setXmlName("%sMapper");
//        gc.setServiceName("%sService");
//        gc.setServiceImplName("%sServiceImpl");
//        gc.setControllerName("%sController");
//
//        mpg.setGlobalConfig(gc);
//    }
//
//    private static void dataSourceConfig(AutoGenerator mpg) {
//        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setDbType(DbType.MYSQL);
//        //数据源参数改一下
//        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
//        dsc.setUsername(jdbcName);
//        dsc.setPassword(jdbcPwd);
//        dsc.setUrl(jdbcUrl);
//        mpg.setDataSource(dsc);
//    }
//
//    private static void templateConfig(AutoGenerator mpg) {
//        // 配置模板
//        TemplateConfig templateConfig = new TemplateConfig();
//        //控制 不生成 controller  设置null就行
//        templateConfig.setController(null);
//        templateConfig.setService(null);
//        templateConfig.setServiceImpl(null);
//        templateConfig.setXml(null);
//        mpg.setTemplate(templateConfig);
//    }
//
//    private static void cfg(AutoGenerator mpg, String projectPath, String mapperXmlPath) {
//        //这里只设置了mapper.xml的生成路径配置，同时可以设置java实体类的路径，此优先级比默认的高
//        InjectionConfig cfg = new InjectionConfig() {
//            @Override
//            public void initMap() {
//                // to do nothing
//            }
//        };
//        // 自定义输出配置
//        List<FileOutConfig> focList = new ArrayList<>();
//        // 自定义配置优先于默认配置生效
//        focList.add(new FileOutConfig(mapperXmlPath) {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义xml 文件名和生成路径
//                return projectPath + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
//            }
//        });
//        cfg.setFileOutConfigList(focList);
//        mpg.setCfg(cfg);
//    }
//
//    private static void strategy(AutoGenerator mpg, String ...includeTables) {
//        StrategyConfig strategy = new StrategyConfig();
//        strategy.setTablePrefix(new String[] { "t_"});// 表名匹配前缀，此处可以修改为您的表前缀
//        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
//        strategy.setEntityLombokModel(true); // 启用lombok增加实体类的get，set方法简化代码；如果不启用可以改为false
//        strategy.setInclude(includeTables); // 需要生成的表，多个用逗号隔开或数组
//        mpg.setStrategy(strategy);
//    }
//
//    private static void packageInfo(AutoGenerator mpg, String packages) {
//        PackageConfig pc = new PackageConfig();
//        //在哪个父包下生成  改成自己的
//        pc.setParent(packages);
//        pc.setEntity("entity");
//        pc.setMapper("mapper");
//        pc.setXml("mapper.xml");
//        pc.setService("service");
//        pc.setServiceImpl("service.impl");
//        pc.setController("controller");
//
//        mpg.setPackageInfo(pc);
//    }
//}