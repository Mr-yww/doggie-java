package com.yww.doggie.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Component
public class InterceptorConfig extends WebMvcConfigurationSupport {
	
	private static final String FAVICON_URL = "/sansan/account/**";	
	
	@Autowired
	LoginInterceptor loginInterceptor;
	
	/**
     * 发现如果继承了WebMvcConfigurationSupport，则在yml中配置的相关内容会失效。
     *
     * @param registry
     */
	
    /**
     * 配置servlet处理
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
   
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	//设置（模糊）匹配的url
        registry.addInterceptor(loginInterceptor).addPathPatterns("/sansan/**").excludePathPatterns(FAVICON_URL);
        super.addInterceptors(registry);
    }
    
    /** 
     * 资源处理器 
     * @param registry 
     */  
    @Override  
    public void addResourceHandlers(ResourceHandlerRegistry registry) {  
        registry.addResourceHandler("/swagger-ui.html")  
                .addResourceLocations("classpath:/META-INF/resources/");  
        registry.addResourceHandler("/webjars/**")  
                .addResourceLocations("classpath:/META-INF/resources/webjars/");  
    } 

}
