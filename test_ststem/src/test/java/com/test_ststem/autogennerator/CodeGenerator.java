package com.test_ststem.autogennerator;

import java.util.Collections;
import java.sql.Types;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

// 代码生成器
public class CodeGenerator {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/wms02?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8"; // 连接数据库地址
                                                                                                                                      // 修改wms02为数据库名字
        FastAutoGenerator.create(url, "root", "higashi")
                .globalConfig(builder -> {
                    builder.author("higashi") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .outputDir("H:\\project\\ruanjiancheshiqimo\\autoproject"); // 指定输出目录
                })
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);
                }))
                .packageConfig(builder -> builder.parent("com.baomidou.mybatisplus.samples.generator") // 设置父包名
                        .moduleName("") // 设置父包模块名
                        .pathInfo(Collections.singletonMap(OutputFile.xml,
                                "H:\\project\\ruanjiancheshiqimo\\autoproject")) // 设置mapperXml生成路径
                )
                .strategyConfig(builder -> builder.addInclude("record") // 设置需要生成的表名
                        .addTablePrefix("t_", "c_") // 设置过滤表前缀
                )
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
