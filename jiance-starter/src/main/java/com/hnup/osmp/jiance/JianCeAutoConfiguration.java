package com.hnup.osmp.jiance;

import com.hnup.common.core.starter.EnableVenusCoreConfiguration;
import com.hnup.common.core.starter.prefix.VenusCoreModuleUrlPrefix;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author demo
 * @Date 2020/8/8 10:58
 * @Version 1.0
 */
@Configuration
@EnableVenusCoreConfiguration
@EnableTransactionManagement
@VenusCoreModuleUrlPrefix(scanBasePackage = "com.hnup.osmp.jiance", value = "jiance")
@ComponentScan(basePackages = {"com.hnup.osmp.jiance"})
@MapperScan("com.hnup.osmp.jiance.repository.mapper")
public class JianCeAutoConfiguration {

}


