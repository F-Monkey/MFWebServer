package cn.monkey.gateway.dsl;

import org.springframework.http.server.reactive.ServerHttpResponse;

import java.util.function.Predicate;

public interface ResponsePredicate extends Predicate<ServerHttpResponse> {
}
