package com.rainysoft.http2Sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.context.annotation.Bean;

import io.undertow.Undertow.Builder;

@SpringBootApplication
public class Springboot2Http2Application {

	public static void main(String[] args) {
		SpringApplication.run(Springboot2Http2Application.class, args);
	}
	
	@Bean
	public UndertowServletWebServerFactory servletWebServerFactory() {
		UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();
		factory.addBuilderCustomizers(new UndertowBuilderCustomizer() {

			@Override
			public void customize(Builder builder) {
				builder.addHttpListener(8080, "0.0.0.0");				
			}

		});
		return factory;
	}
}
