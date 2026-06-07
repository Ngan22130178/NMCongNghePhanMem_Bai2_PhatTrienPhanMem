package vn.edu.nlu.fit.nmcnpm.my_silly_bestie;

import org.springframework.boot.SpringApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class  MySillyBestieApplication {

	public static void main(String[] args) {

		SpringApplication.run(MySillyBestieApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer webMvcConfigurer(){
		return new WebMvcConfigurer() {
			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) {
				registry.addResourceHandler("/static/**")
						.addResourceLocations("classpath:/static/");
			}
		};
	}
}
