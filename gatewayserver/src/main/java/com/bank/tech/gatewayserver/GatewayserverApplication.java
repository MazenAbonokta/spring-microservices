package com.bank.tech.gatewayserver;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootApplication

public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

@Bean
	public RouteLocator customeRouteLocator(RouteLocatorBuilder routeLocatorBuilder)
	{
		return routeLocatorBuilder.routes()
				.route(
						p->p.path("/ACCOUNTS/**")
								.filters(f->f.rewritePath("/ACCOUNTS/?(?<remaining>.*)","/${remaining}")
										.addResponseHeader("time-request", LocalDateTime.now().toString())
										.circuitBreaker(config -> config.setName("accountCircuitBreaker")
												.setFallbackUri("forward:/contactsupport"))
										.retry(retryConfig -> retryConfig.setRetries(5)
												.setMethods(HttpMethod.GET,HttpMethod.POST)
												.setBackoff(Duration.ofMillis(100),Duration.ofMillis(1000),2,true)))
								.uri("lb://ACCOUNTS")
				).route(
						p->p.path("/LOANS/**")
								.filters(f->f.rewritePath("/LOANS/?(?<remaining>.*)","/${remaining}")
										.addResponseHeader("time-request", LocalDateTime.now().toString())
										.circuitBreaker(config -> config.setName("loanCircuitBreaker")
												)
										.retry(retryConfig -> retryConfig.setRetries(5)
												.setMethods(HttpMethod.GET,HttpMethod.POST)
												.setBackoff(Duration.ofMillis(100),Duration.ofMillis(1000),2,true)))
								.uri("lb://LOANS")
				).route(
						p->p.path("/CARDS/**")
								.filters(f->f.rewritePath("/CARDS/?(?<remaining>.*)","/${remaining}")
										.addResponseHeader("time-request", LocalDateTime.now().toString())
										.circuitBreaker(config -> config.setName("loanCircuitBreaker")
												).
										retry(retryConfig -> retryConfig.setRetries(5)
												.setMethods(HttpMethod.GET,HttpMethod.POST)
												.setBackoff(Duration.ofMillis(100),Duration.ofMillis(1000),2,true))
									/*	.requestRateLimiter(config -> config.setKeyResolver(userKeyResolver())
												.setRateLimiter(redisRateLimiter()))*/
								)
								.uri("lb://CARDS")
				).build();
	}
	@Bean
	public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
				.circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
				.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build()).build());
	}

	/*@Bean
	public RedisRateLimiter redisRateLimiter() {
		return new RedisRateLimiter(1, 1, 1);
	}

	@Bean
	KeyResolver userKeyResolver() {
		return exchange -> Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("user"))
				.defaultIfEmpty("anonymous");
	}*/
}
