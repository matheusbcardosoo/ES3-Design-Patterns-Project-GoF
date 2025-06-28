package org.fatec.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "org.fatec.repository")
public class MongoConfig {
    // Removidas as configurações manuais para evitar conflito com a autoconfiguração do Spring Boot
    // O Spring Boot já configura o MongoDB automaticamente com base nas propriedades em application.properties
}
