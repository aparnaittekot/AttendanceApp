package com.bits.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class AppConfig {

	@Profile("cloud")
	static class CloudConfig extends AbstractCloudConfig {

		@Bean
		@ConfigurationProperties("spring.datasource")
		public DataSource dataSource() {
			return connectionFactory().dataSource();
		}
	}

	@Profile("IntegrationTest")
	static class IntegrationTestConfig {
		@Bean
		public DataSource dataSource() {
			EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
			EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2).build();
			return db;
		}
	}

	static class EclipseConfig {

		@Bean
		public JpaVendorAdapter eclipseLink() {
			EclipseLinkJpaVendorAdapter adapter = new EclipseLinkJpaVendorAdapter();
			return adapter;
		}

		@Bean(name = "entityManagerFactory")
		public LocalContainerEntityManagerFactoryBean emf(JpaVendorAdapter adapter, DataSource ds) {
			LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
			factory.setJpaVendorAdapter(adapter);
			factory.setDataSource(ds);
			factory.setPersistenceUnitName("default");
			return factory;
		}

		@Bean
		public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
			return new JpaTransactionManager(emf);
		}

		
	}
}