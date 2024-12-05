package com.NAKexample.demoSpring.database;

import com.NAKexample.demoSpring.models.Product;
import com.NAKexample.demoSpring.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Database {
    //logger
    private static final Logger logger = LoggerFactory.getLogger(Database.class);

    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository){
        return  new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Product productA = new Product("Iphone6", 2020, "", 2000.0);
                Product productB = new Product("Samsung", 2020, "", 1000.0);
                logger.info("Insert product: "+ productRepository.save(productA));
                logger.info("Insert product: "+ productRepository.save(productB));
            }
        };
    }
}
