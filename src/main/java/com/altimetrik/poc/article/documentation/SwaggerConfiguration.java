package com.altimetrik.poc.article.documentation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Value("${api.path.selectors.pattern}")
    private String API_PATH_SELECTORS_PATTERN;

    @Value("${api.title}")
    private String API_TITLE;

    @Value("${api.description}")
    private String API_DESCRIPTION;

    @Value( "${api.contact.name}" )
    private String API_CONTACT_NAME;

    @Value( "${api.contact.url}" )
    private String API_CONTACT_URL;

    @Value( "${api.contact.email}" )
    private String API_CONTACT_EMAIL;

    @Bean
    public Docket articlePublishingApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(orderBookApiInfo())
                .select()
                .paths(PathSelectors.ant(API_PATH_SELECTORS_PATTERN))
                .build();
    }

    private ApiInfo orderBookApiInfo() {
        return  new ApiInfoBuilder()
                .title(API_TITLE)
                .version(getClass().getPackage().getImplementationVersion())
                .description(API_DESCRIPTION)
                .contact(new Contact(
                        API_CONTACT_NAME,
                        API_CONTACT_URL,
                        API_CONTACT_EMAIL))
                .build();
    }
}

