package com.alexcorp.homeblog.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "{}");
        System.setProperty("resources.local.path", "/src/main/resources");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/public/images/favicon.ico");
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
        registry.addResourceHandler("/styles/**").addResourceLocations("classpath:/static/styles/");
        registry.addResourceHandler("/scripts/**").addResourceLocations("classpath:/static/scripts/");
        registry.addResourceHandler("**.bundle.js").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("**.chunk.js").addResourceLocations("classpath:/static/js/");

        registry.addResourceHandler("/topic/**").addResourceLocations("classpath:/static/posts/");
        registry.addResourceHandler("/posts/**").addResourceLocations("classpath:/static/posts/");
        registry.addResourceHandler("/post/**").addResourceLocations("classpath:/static/posts/");
        //registry.addResourceHandler("/public/**").addResourceLocations("static/public/");
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerCustomizer() {
        return container -> {
            container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/"));
        };
    }
}
