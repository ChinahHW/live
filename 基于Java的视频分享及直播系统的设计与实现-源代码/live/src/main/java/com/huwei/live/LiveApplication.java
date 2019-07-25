package com.huwei.live;

import com.huwei.live.util.StringUtil;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.MultipartConfigElement;
import java.text.SimpleDateFormat;
import java.util.Date;

@EnableTransactionManagement
@SpringBootApplication
@MapperScan("com.huwei.live.mapper")
public class LiveApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(LiveApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        setRegisterErrorPageFilter(false);
        return application.sources(LiveApplication.class);
    }

    /**
     * 文件上传配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize("50MB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("60MB");
        return factory.createMultipartConfig();
    }


    @Bean
    public Converter<String, Date> addNewConvert() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                Date date = null;
                try {
                    if (!StringUtil.isNotEmpty(source)) {
                        return null;
                    } else {
                        SimpleDateFormat simpleDateFormat = null;
                        if (source.length() == 4) {
                            simpleDateFormat = new SimpleDateFormat("yyyy");
                            date = simpleDateFormat.parse(source);
                        } else if (source.contains("-")) {
                            if (source.length() == 7) {
                                simpleDateFormat = new SimpleDateFormat("yyyy-MM");
                                date = simpleDateFormat.parse(source);
                            } else if (source.length() == 10) {
                                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                date = simpleDateFormat.parse(source);
                            } else if (source.length() == 13) {
                                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
                                date = simpleDateFormat.parse(source);
                            } else if (source.length() == 16) {
                                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                date = simpleDateFormat.parse(source);
                            } else {
                                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                date = simpleDateFormat.parse(source);
                            }
                        } else {
                            logger.error("未知数据类型：" + source);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(ExceptionUtils.getFullStackTrace(e));
                }
                return date;
            }
        };
    }
}
