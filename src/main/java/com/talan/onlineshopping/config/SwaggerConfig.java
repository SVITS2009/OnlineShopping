package com.talan.onlineshopping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {
  @Bean
  public Docket productApi() {
    return new Docket(DocumentationType.SWAGGER_2)
            .select()                
            .apis(RequestHandlerSelectors.basePackage("com.talan.onlineshopping"))
            .paths(PathSelectors.any())
            .build()            
            .apiInfo(metaData())
            .securitySchemes(basicScheme());
  }

  private List<SecurityScheme> basicScheme() {
    List<SecurityScheme> schemeList = new ArrayList<>();
    schemeList.add(new BasicAuth("basicAuth"));
    return schemeList;
  }
  private ApiInfo metaData() {
    return new ApiInfoBuilder()
            .title("Online Shopping Item API")
            .description("Online Shopping Item API reference for developers")
            .version("1.0.0")
            .license("Apache License Version 2.0")
            .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
            .build();
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("swagger-ui.html")
    .addResourceLocations("classpath:/META-INF/resources/");

    registry.addResourceHandler("/webjars/**")
    .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }
}