package com.chris.security;

import java.time.Duration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.DispatcherServlet;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@SpringBootApplication
@EnableEurekaClient // To enable eureka client
@EnableResourceServer
public class EurekaClientConfig {

	public static void main(String[] args) {
		SpringApplication.run(EurekaClientConfig.class, args); // it wil start application
	}

	@Bean
	DispatcherServlet dispatcherServlet() { // To allow RequestContextHolder in ClientHttpRequestInterceptor
		DispatcherServlet servlet = new DispatcherServlet();
		servlet.setThreadContextInheritable(true);
		return servlet;
	}

	/*
	 * @LoadBalanced
	 * 
	 * @Bean
	 * 
	 * @Primary public RestTemplate orderService() { RestTemplate restTemplate = new
	 * RestTemplate();
	 * 
	 * List<ClientHttpRequestInterceptor> interceptors =
	 * restTemplate.getInterceptors(); if (CollectionUtils.isEmpty(interceptors)) {
	 * interceptors = new ArrayList<>(); } interceptors.add(new
	 * ClientHttpRequestInterceptor() {
	 * 
	 * @Override public ClientHttpResponse intercept(HttpRequest httpRequest, byte[]
	 * bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws
	 * IOException { httpRequest.getHeaders().add("Authorization",
	 * getBearerTokenHeader()); return
	 * clientHttpRequestExecution.execute(httpRequest, bytes); } });
	 * restTemplate.setInterceptors(interceptors); return restTemplate; }
	 */

	@LoadBalanced
	@Bean(name = "tokenVerifier")
	public RestTemplate tokenVerifier() {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate;
	}

	public String getBearerTokenHeader() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getHeader("Authorization");
	}

	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
				.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build())
				.circuitBreakerConfig(CircuitBreakerConfig.custom().minimumNumberOfCalls(5).build()).build());
	}
}
