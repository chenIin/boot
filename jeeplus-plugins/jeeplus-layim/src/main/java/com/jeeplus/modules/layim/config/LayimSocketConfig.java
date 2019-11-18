package com.jeeplus.modules.layim.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebMvc
@EnableWebSocket
public class LayimSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //注册layIM socket服务
        registry.addHandler(layImSocketHandler(), "/layIMSocketServer").addInterceptors(new LayIMSocketHandshakeInterceptor());
        registry.addHandler(layImSocketHandler(), "/sockjs/layIMSocketServer").addInterceptors(new LayIMSocketHandshakeInterceptor())
                .withSockJS();

    }

    @Bean
    public WebSocketHandler layImSocketHandler() {
        return new LayIMSocketHandler();
    }

}