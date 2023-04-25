package com.sr.datagen;

import com.sr.datagen.security.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class DatagenApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatagenApplication.class, args);
    }

}
