package com.dongho.demo.springcloudconfigserverdemo.web.controller.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dongho.demo.springcloudconfigserverdemo.domain.config.ConfigService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RequestMapping("/config/demo")
@RestController
public class DemoConfigController {

	@Autowired
	private ConfigService configService;

	@GetMapping("/spring-cloud-gateway-demo.yaml")
	public Mono<String> getSpringCloudGatewayDemo(ServerHttpRequest request) {
		log.info("path: {}, queryParams: {}", request.getPath(), request.getQueryParams());

		return Mono.defer(() -> Mono.fromCallable(() ->
				configService.loadConfigAsString("classpath:config/demo/spring-cloud-gateway-demo.yaml")))
			.onErrorReturn("");
	}

}
