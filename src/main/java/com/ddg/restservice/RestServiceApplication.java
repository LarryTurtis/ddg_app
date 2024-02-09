package com.ddg.restservice;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.ddg.restservice.services.AsanaService;
import com.ddg.restservice.services.DBService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class RestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestServiceApplication.class, args);
	}

	@Bean
	public SessionFactory getSessionFactory() {
		log.info(System.getenv("DATABASE_URL"));
		try {
			StandardServiceRegistryBuilder standardRegistryBuilder = new StandardServiceRegistryBuilder()
					.configure("hibernate.cfg.xml");
			standardRegistryBuilder.applySetting("hibernate.connection.url", System.getenv("DATABASE_URL"));
			standardRegistryBuilder.applySetting("hibernate.connection.username", System.getenv("DATABASE_USER"));
			standardRegistryBuilder.applySetting("hibernate.connection.password", System.getenv("DATABASE_PASSWORD"));
			StandardServiceRegistry standardRegistry = standardRegistryBuilder.build();
			Metadata metaData = new MetadataSources(standardRegistry)
					.getMetadataBuilder()
					.build();
			return metaData.getSessionFactoryBuilder().build();
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	@Bean
	public AsanaService getAsanaService() {
		return new AsanaService();
	}

	@Bean
	public DBService getDbService() {
		return new DBService(getSessionFactory());
	}

}
