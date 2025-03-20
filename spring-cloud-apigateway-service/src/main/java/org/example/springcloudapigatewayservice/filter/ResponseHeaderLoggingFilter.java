package org.example.springcloudapigatewayservice.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ResponseHeaderLoggingFilter extends AbstractGatewayFilterFactory<ResponseHeaderLoggingFilter.Config> {

    public static class Config {
        // 설정 속성
    }

    public ResponseHeaderLoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                ServerHttpResponse response = exchange.getResponse();
                HttpHeaders headers = response.getHeaders();

                log.info("Response Status: {}", response.getStatusCode());
                log.info("Response Headers: {}", headers);

                // 특정 헤더가 있는지 확인
                if (headers.containsKey("Authorization")) {
                    log.info("Authorization 헤더 존재: {}", headers.getFirst("Authorization"));
                } else {
                    log.info("Authorization 헤더 없음");
                }

                if (headers.containsKey("userId")) {
                    log.info("userId 헤더 존재: {}", headers.getFirst("userId"));
                } else {
                    log.info("userId 헤더 없음");
                }
            }));
        };
    }
}