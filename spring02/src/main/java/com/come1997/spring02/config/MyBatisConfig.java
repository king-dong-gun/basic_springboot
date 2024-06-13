package com.come1997.spring02.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
// 해당 패키지에서 Mapper 인식
@MapperScan(basePackages = { "com.come1997.spring02.mapper" })

public class MyBatisConfig {

}
