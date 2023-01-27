package com.in;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		return builder.routes()
//				.route(p -> p
//						.path("/get")
//						.filters(f -> f
//								.addRequestHeader("MyHeader", "MyURI")
//								.addRequestParameter("Param", "MyValue"))
//						.uri("http://httpbin.org:80"))
				.route(p -> p.path("/api/users/**")
						.uri("lb://USER"))
				.route(p -> p.path("/api/v1/auth/**")
						.uri("lb://USER"))
				.route(p -> p.path("api/admin/**")
						.uri("lb://USER"))
				.route(p -> p.path("/api/post/**")
						.uri("lb://POST"))
				.route(p -> p.path("/api/comment/**")
						.uri("lb://COMMENT"))
				.route(p -> p.path("/api/categories/**")
						.uri("lb://CATEGORIES"))
				
				
				
//				.route(p -> p.path("/currency-conversion/**")
//						.uri("lb://currency-conversion"))
//				.route(p -> p.path("/currency-conversion-feign/**")
//						.uri("lb://currency-conversion"))
//				.route(p -> p.path("/currency-conversion-new/**")
//						.filters(f -> f.rewritePath(
//								"/currency-conversion-new/(?<segment>.*)",
//								"/currency-conversion-feign/${segment}"))
//						.uri("lb://currency-conversion"))
				.build();
	}
	
	
	
	
	
	
//	@Bean
//	public Docket api() {
//		return new Docket(DocumentationType.SWAGGER_2)
//				.select()
//				.apis(RequestHandlerSelectors.any())
//				.paths(PathSelectors.any())
//				.build();
//	}
}