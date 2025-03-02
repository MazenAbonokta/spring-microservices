package com.bank.tech.gatewayserver.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Component
@Order(1)
public class RequestTraceFilter implements GlobalFilter {
    private static final Logger logger= LoggerFactory.getLogger(RequestTraceFilter.class);
    @Autowired
    FilterUtility filterUtility;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders requestHeaders=exchange.getRequest().getHeaders();
        if(isColorationIdIsPresent(requestHeaders)){
            logger.debug("ColorationId found in RequestTraceFilter {}",filterUtility.getCorrelationId(requestHeaders));
        }
        else {
            String CorrelationId=generateCorrelationId();
            exchange=filterUtility.setCorrelatonId(exchange,CorrelationId);
            logger.debug("ColorationId generated in RequestTraceFilter {}",CorrelationId);
        }

        return chain.filter(exchange);
    }

    private String generateCorrelationId() {
        return UUID.randomUUID().toString();
    }

    private boolean isColorationIdIsPresent(HttpHeaders requestHeaders) {
        if (filterUtility.getCorrelationId(requestHeaders)!=null)
        {
            return  true;
        }
        else {
            return  false;
        }
    }


}
