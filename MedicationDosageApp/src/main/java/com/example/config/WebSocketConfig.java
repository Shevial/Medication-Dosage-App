package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;



@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    //configure message broker
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
       // config.setPreservePublishOrder(true);

    }

    //configure stomp endpoints
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
      //  registry.addEndpoint("/websocket-server"); sin js
        registry.addEndpoint("/websocket-server")
                .setAllowedOrigins("http://localhost:5173")
                .withSockJS();
    }
}
