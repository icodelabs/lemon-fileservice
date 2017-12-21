package cn.lemon.fileservice;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * 图片文件处理服务
 * Created by lonyee on 2017/4/6.
 */
@SpringBootApplication
public class FileServiceApplication {
    static Logger logger = LoggerFactory.getLogger(FileServiceApplication.class);

    /**
     * 启动项目DiscoveryClientService
     */
    public static void main(String[] args) {
        SpringApplicationBuilder springApplication = new SpringApplicationBuilder(FileServiceApplication.class);
        springApplication.web(true);
        springApplication.run(args);
    }
    
}
