package com.oracle.labs.notebookserver.endpoint.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.oracle.labs.notebookserver.endpoint.*")
public class EndpointConfiguration {
}
