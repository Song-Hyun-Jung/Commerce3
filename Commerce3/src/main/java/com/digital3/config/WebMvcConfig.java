package com.digital3.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.digital3.interceptor.AuthInterceptor;

@ComponentScan(basePackages = {"com.digital3.interceptor"}) 
@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	@Autowired
	AuthInterceptor authInterceptor; 
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {	

		// 인증 관련 인터셉터
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/rest/**")
                .excludePathPatterns("/rest/person/signUp", "/rest/person/logIn", "/rest/person/searchByLoginId/*"); //authorize 제외
    }
}
