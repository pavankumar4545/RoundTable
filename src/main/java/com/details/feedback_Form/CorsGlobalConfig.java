//package com.details.feedback_Form;  // Make sure the package matches your project structure
//
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//import java.util.List;
//
//@Configuration
//public class CorsGlobalConfig {
//
//    @Bean
//    public FilterRegistrationBean<CorsFilter> corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//
//        // REPLACE WITH YOUR FRONTEND URL
//        config.setAllowedOrigins(List.of("http://localhost:3000", "https://yourfrontend.com"));
//
//        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
//
//        config.setAllowCredentials(true);
//        source.registerCorsConfiguration("/**", config);
//
//        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
//        bean.setOrder(0); // Ensure it runs before other filters
//
//        return bean;
//    }
//}
