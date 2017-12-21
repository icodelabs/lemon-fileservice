package cn.lemon.fileservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class SpringConfiguration extends WebMvcConfigurerAdapter {

    @Value("${spring.cross.mapping}")
    private String crossMapping; //设置允许跨域访问的域名

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(crossMapping)
                .allowedMethods("GET","POST","PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        resolver.setMaxInMemorySize(40960);
        resolver.setMaxUploadSize(50 * 1024 * 1024);//上传文件大小 50M 50*1024*1024
        return resolver;
    }

    @Bean
    public SignAuthIntercept signAuthIntercept() {
        SignAuthIntercept signAuthIntercept = new SignAuthIntercept();
        return signAuthIntercept;
    }

    /**加入拦截器 */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(signAuthIntercept()).addPathPatterns("/**");
    }

    @Bean(name = "aliossConfig")
    public AliossConfig aliossConfig() {
        return new AliossConfig();
    }
}
