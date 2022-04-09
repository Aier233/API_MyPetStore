package org.csu.mypetstore.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan("org.csu.mypetstore.api.persistence")
public class MyPetStoreApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyPetStoreApiApplication.class, args);
    }

}
