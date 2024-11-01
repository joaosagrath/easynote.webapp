package app.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration


public class Base2Config {
	
	@Bean
	@ConfigurationProperties(prefix = "base2.datasource")
	public DataSource base1DataSource() {
		
		return DataSourceBuilder.create().build();
	}
}	
	
