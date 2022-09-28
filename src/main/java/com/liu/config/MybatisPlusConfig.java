package com.liu.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan("com.liu.mapper")
@Configuration
public class MybatisPlusConfig {
}
