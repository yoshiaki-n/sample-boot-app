package com.example.samplebootapp.infrastructure.config;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan("com.example.samplebootapp.infrastructure")
public class MyBatisConfig {

  @Bean
  public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
    SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    sessionFactory.setDataSource(dataSource);
    sessionFactory.setMapperLocations(
        new PathMatchingResourcePatternResolver()
            .getResources("classpath*:com/example/samplebootapp/infrastructure/**/*.xml"));
    sessionFactory.setTypeHandlersPackage("com.example.samplebootapp.infrastructure");
    return sessionFactory.getObject();
  }
}

```
