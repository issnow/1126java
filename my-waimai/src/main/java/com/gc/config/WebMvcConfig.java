package com.gc.config;

import com.gc.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
@Slf4j
public class WebMvcConfig extends WebMvcConfigurationSupport {
  //静态资源映射,适用于backend和front直接放在resources/ 路径下
  @Override
  protected void addResourceHandlers(ResourceHandlerRegistry registry) {
    log.info("开始进行静态资源映射...");
    registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
    registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
  }

  //扩展mvn框架的消息转换器,说人话就是将发送给前端的json进行二次处理,例如将很长的long类型数字转成string,防止前端丢失精度
  //或者统一处理时间日期对象,自动格式化成规范的时间类型
  @Override
  protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    log.info("扩展消息转换器...");
    MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
    //设置对象转化器，底层使用jackson将java对象转为json
    messageConverter.setObjectMapper(new JacksonObjectMapper());
    //将上面的消息转换器对象追加到mvc框架的转换器集合当中(index设置为0，表示设置在第一个位置，避免被其它转换器接收，从而达不到想要的功能)
    converters.add(0, messageConverter);
  }
}
