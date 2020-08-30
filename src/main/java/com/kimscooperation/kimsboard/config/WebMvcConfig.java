// package com.kimscooperation.kimsboard.config;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration
// public class WebMvcConfig implements WebMvcConfigurer {

//     @Override
//     public void addCorsMappings(CorsRegistry registry) {
//         registry.addMapping("/**")
//         .allowedHeaders("*")
//         .allowedMethods(
//             HttpMethod.GET.name(),
//             HttpMethod.HEAD.name(),
//             HttpMethod.POST.name(),
//             HttpMethod.PUT.name(),
//             HttpMethod.DELETE.name(),
//             HttpMethod.OPTIONS.name())
//         .allowedOrigins("http://localhost:8081","http://127.0.0.1:8081")
//         .allowCredentials(false)
//         .maxAge(3600);
//     }
// }
