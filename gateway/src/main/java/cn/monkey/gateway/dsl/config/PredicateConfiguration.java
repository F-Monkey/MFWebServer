package cn.monkey.gateway.dsl.config;

import cn.monkey.gateway.dsl.DefaultServerHttpRequestPredicateFactory;
import cn.monkey.gateway.dsl.DefaultServerHttpResponsePredicateFactory;
import cn.monkey.gateway.dsl.ServerHttpRequestPredicateFactory;
import cn.monkey.gateway.dsl.ServerHttpResponsePredicateFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class PredicateConfiguration {
    @Bean
    ServerHttpResponsePredicateFactory serverHttpResponsePredicateFactory() {
        return new DefaultServerHttpResponsePredicateFactory();
    }

    @Bean
    ServerHttpRequestPredicateFactory serverHttpRequestPredicateFactory() {
        return new DefaultServerHttpRequestPredicateFactory();
    }
}
