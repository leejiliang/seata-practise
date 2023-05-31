package com.seata.util.mybatis;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 使用该工具类需要引入4个包
 *         <dependency>
 *             <groupId>com.baomidou</groupId>
 *             <artifactId>mybatis-plus-boot-starter</artifactId>
 *         </dependency>
 *
 *         <dependency>
 *             <groupId>mysql</groupId>
 *             <artifactId>mysql-connector-java</artifactId>
 *         </dependency>
 *
 *         <!-- mybatis-plus 代码生成器 -->
 *         <dependency>
 *             <groupId>com.baomidou</groupId>
 *             <artifactId>mybatis-plus-generator</artifactId>
 *         </dependency>
 *         <!--mybatis-plus freemarker引擎模板-->
 *         <dependency>
 *             <groupId>org.freemarker</groupId>
 *             <artifactId>freemarker</artifactId>
 *         </dependency>
 */
public class MybatisCodeGenerator {

    //手动配置数据源
    String url = "jdbc:mysql://localhost:3307/seata_uk?useUnicode=true&characterEncoding=utf8";
    String name = "uk";
    String password = "123456";

    //数据库表的设置
    List<String> listTableSuffix = Arrays.asList("_b");    //设置 过滤 表的后缀
    List<String> listTablePrefix = Arrays.asList("t_", "c_"); //设置 过滤 表的后缀

    //基本信息
    String author = "";    //作者
    String parent = "com.seata";   //父包名
    String module = "warehouse";   //模块包名

    public static void main(String[] args) {
        new MybatisCodeGenerator().createEntity("uk-warehouse", List.of("pay_record"));
    }

    /**
     * @param subModule 项目模块名称
     * @param listTable 需要自动代码生成的表名
     */
    public void createEntity(String subModule, List<String> listTable) {

        final String path = System.getProperty("user.dir") + "/" + subModule;

        //1、配置数据源
        FastAutoGenerator.create(url, name, password)
                //2、全局配置
                .globalConfig(builder -> {
                    builder.author(author) // 设置作者名
                            .outputDir(path + "/src/main/java")   //设置输出路径：项目的 java 目录下【System.getProperty("user.dir")意思是获取到项目所在的绝对路径】
                            .commentDate("yyyy-MM-dd hh:mm:ss")   //注释日期
                            .dateType(DateType.ONLY_DATE)   //定义生成的实体类中日期的类型 TIME_PACK=LocalDateTime;ONLY_DATE=Date;
                            .fileOverride()   //覆盖之前的文件
//                            .enableSwagger()   //开启 swagger 模式
                            .disableOpenDir();   //禁止打开输出目录，默认打开
                })
                //3、包配置
                .packageConfig(builder -> {
                    builder.parent(parent) // 设置父包名
                            .moduleName(module)   //设置模块包名
                            .entity("entity")   //pojo 实体类包名
//                            .service("service") //Service 包名
//                            .serviceImpl("service.impl") // ***ServiceImpl 包名
                            .mapper("mapper")   //Mapper 包名
                            .xml("mapper.xml")  //Mapper XML 包名
//                            .controller("controller") //Controller 包名
                            .pathInfo(Collections.singletonMap(OutputFile.mapper.xml, path + "/src/main/resources/mapper"));    //配置 mapper.xml 路径信息：项目的 resources 目录下
                })
                //4、策略配置
                .strategyConfig(builder -> {
                    builder.enableCapitalMode()    //开启大写命名
                            .enableSkipView()   //创建实体类的时候跳过视图
                            .addInclude(listTable) // 设置需要生成的数据表名
                            .addTableSuffix(listTableSuffix) //设置 过滤 表的后缀
                            .addTablePrefix(listTablePrefix) // 设置 过滤 表的前缀

                            //4.1、实体类策略配置
                            .entityBuilder().enableTableFieldAnnotation()       // 开启生成实体时生成字段注解
                            .enableLombok() //开启 Lombok
                            .naming(NamingStrategy.underline_to_camel)  //数据库表映射到实体的命名策略：默认是下划线转驼峰命。这里可以不设置
                            .columnNaming(NamingStrategy.underline_to_camel)    //数据库表字段映射到实体的命名策略：下划线转驼峰命。（默认是和naming一致，所以也可以不设置）
                            .idType(IdType.AUTO)
//                            .addTableFills(
//                                    new Column("create_time", FieldFill.INSERT),
//                                    new Column("modify_time", FieldFill.INSERT_UPDATE)
//                            )   //添加表字段填充，"create_time"字段自动填充为插入时间，"modify_time"字段自动填充为插入修改时间
//                            .idType(IdType.AUTO)    //设置主键自增

                            //4.2、Controller策略配置
//                            .controllerBuilder()
//                            .enableHyphenStyle()    //开启驼峰连转字符
//                            .formatFileName("%sController") //格式化 Controller 类文件名称，%s进行匹配表名，如 UserController
//                            .enableRestStyle()  //开启生成 @RestController 控制器

                            //4.3、service 策略配置
//                            .serviceBuilder()
//                            .formatServiceFileName("%sService") //格式化 service 接口文件名称，%s进行匹配表名，如 UserService
//                            .formatServiceImplFileName("%sServiceImpl") //格式化 service 实现类文件名称，%s进行匹配表名，如 UserServiceImpl

                            //4.4、Mapper策略配置
                            .mapperBuilder().superClass(BaseMapper.class)   //设置父类
                            .enableBaseResultMap()  //启用 BaseResultMap 生成
                            .enableBaseColumnList() //启用 BaseColumnList
                            .formatMapperFileName("%sMapper")   //格式化 mapper 文件名称
//                            .enableMapperAnnotation()       //开启 @Mapper 注解
                            .formatXmlFileName("%sMapper") //格式化Xml文件名称
                            .formatMapperFileName("%sMapper");   //格式化Mapper文件名称
                })
                //5、模板
                .templateEngine(new FreemarkerTemplateEngine())
                .templateConfig(builder -> {
                    builder.disable(TemplateType.CONTROLLER) //不生成controller
                            .disable(TemplateType.SERVICE) //不生成service
                            .disable(TemplateType.SERVICE_IMPL);//不生成service.impl
                })
                //6、执行
                .execute();

    }
}