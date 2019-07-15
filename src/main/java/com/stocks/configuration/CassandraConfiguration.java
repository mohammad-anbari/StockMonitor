package com.stocks.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableCassandraRepositories
public class CassandraConfiguration extends AbstractCassandraConfiguration {

	@Value("${spring.data.cassandra.keyspace-name}")
	String keyspaceName;

	@Value("classpath:startup.cql")
	Resource startupCql;

	@Override
	protected List<String> getStartupScripts() {
		try {
			String script = new String(Files.readAllBytes(startupCql.getFile().toPath()));
			return Arrays.stream(script.split(";")).filter(cql -> !cql.isEmpty()).collect(Collectors.toList());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return Collections.emptyList();
	}

	@Override
	protected List<String> getShutdownScripts() {
		return Collections.emptyList();
	}

	@Override
	protected String getKeyspaceName() {
		return keyspaceName;
	}

	@Override
	protected boolean getMetricsEnabled() { return false; }
}
