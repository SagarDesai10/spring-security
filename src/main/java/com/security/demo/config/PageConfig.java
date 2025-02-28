package com.security.demo.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;


// for serialize PageImpl<T>
// Spring Boot does not know how to serialize PageImpl<T> properly into JSON.
// if you don't use this and return response in PageImple<T> it will give warning

@Configuration
@EnableSpringDataWebSupport(
    pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO
)
public class PageConfig {

}
