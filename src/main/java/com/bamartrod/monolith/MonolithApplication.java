package com.bamartrod.monolith;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
/**
 * MonolithApplication — component of com.bamartrod.monolith bounded context.
 *
 * @author Brandon Martinez
 */


@SpringBootApplication

public class MonolithApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonolithApplication.class, args);
    }

    @Bean
    XmlMapper xmlMapper() {
        return new XmlMapper();
    }
}
