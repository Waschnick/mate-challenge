package de.axelspringer.ideas.mate.five;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Application extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FormHttpMessageConverter converter = new FormHttpMessageConverter();
//        converter.setSupportedMediaTypes(Arrays.asList(MediaType.ALL, MediaType.APPLICATION_FORM_URLENCODED, MediaType.MULTIPART_FORM_DATA));
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED, MediaType.MULTIPART_FORM_DATA));
        converters.add(converter);
        super.configureMessageConverters(converters);
    }


}
