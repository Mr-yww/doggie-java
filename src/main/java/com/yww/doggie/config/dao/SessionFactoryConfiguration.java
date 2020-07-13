package com.yww.doggie.config.dao;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * 数据库sqlSession配置类
 */

@Configuration
public class SessionFactoryConfiguration {
//    mybatis:
//    config-location: mybatis-config.xml # mybatis配置文件所在路径
//    type-aliases-package: com.yww.doggie.entity # 所有Entity别名类所在包
//    mapper-locations: /mapper/**/*.xml # mapper映射文件
//
    @Value("${mybatis.mapper-locations}")
    private String mapperPath;

    @Value("${mybatis.config-location}")
    private String mybatisConfigFilePath;

    @Autowired
    private DataSource dataSouce;
    @Value("${mybatis.type-aliases-package}")
    private String entityPackage;

    @Bean(name="sqlSessionFactory")
    public SqlSessionFactoryBean createSqlSessionFactoryBean() throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(mybatisConfigFilePath));
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        String packageSearchPath = PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX+mapperPath;
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(packageSearchPath));
        sqlSessionFactoryBean.setDataSource(dataSouce);
        sqlSessionFactoryBean.setTypeAliasesPackage(entityPackage);
        return sqlSessionFactoryBean;
    }
}