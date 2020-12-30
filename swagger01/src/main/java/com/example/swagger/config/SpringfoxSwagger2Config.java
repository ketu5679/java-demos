package com.example.swagger.config;

import com.example.swagger.annotation.SwaggerCustomIgnore;
import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static com.google.common.base.Predicates.*;
import static springfox.documentation.builders.RequestHandlerSelectors.*;
import static springfox.documentation.builders.PathSelectors.*;


/**
 * @Description Swagger配置文件
 * http://localhost:8080/swagger-ui.html
 * @Author ketu5679
 * @Date 2020/12/31
 **/
@Configuration
@EnableSwagger2
@ComponentScan(basePackages = "com.example.swagger.controller")
public class SpringfoxSwagger2Config {

    //组织Docket对象，翻译过来就是摘要的意思，它是生成API文档的核心对象，里面配置一些必要的信息
    @Bean
    public Docket swaggerSpringMvcPlugin(){
        //指定规范，这里是SWAGGER_2
        return new Docket(DocumentationType.SWAGGER_2)
                //设定Api文档头信息，这个信息会展示在文档UI的头部位置
                .apiInfo(swaggerDemoApiInfo())
                .select()
                //添加过滤条件，谓词过滤predicate，这里是自定义注解进行过滤
                .apis(not(withMethodAnnotation(SwaggerCustomIgnore.class)))
                //这里配合@ComponentScan一起使用，又再次细化了匹配规则(当然，我们也可以只选择@ComponentScan、paths()方法当中的一中)
                .paths(allowPaths())
                .build();
    }

    /**
     * 自定义API文档基本信息实体
     * @return
     */
    private ApiInfo swaggerDemoApiInfo(){
        //构建联系实体，在UI界面会显示
        Contact contact = new Contact("ketu5679", "http://www.zaomianbao.com", "zaomianbao@163.com");
        return new ApiInfoBuilder()
                .contact(contact)
                //文档标题
                .title("Swagger2构建RESTful API文档")
                //文档描述
                .description("SpringBoot集成Springbox开源项目，实现OAS，构建成RESTful API文档")
                //文档版本
                .version("1.0.0")
                .build();
    }

    /**
     * path匹配规则
     * @return
     */
    private Predicate<String> allowPaths(){
        return or(
                regex("/user.*"),
                regex("/role.*")
        );
    }
}