package com.momo2x.dungeon.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import static com.momo2x.dungeon.config.WebSocketConstants.APP_DESTINATION_PREFIX;
import static com.momo2x.dungeon.config.WebSocketConstants.SIMPLE_BROKER_DESTINATION;
import static com.momo2x.dungeon.config.WebSocketConstants.STOMP_REGISTRY_ENDPOINT;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry config) {
        config.enableSimpleBroker(SIMPLE_BROKER_DESTINATION);
        config.setApplicationDestinationPrefixes(APP_DESTINATION_PREFIX);
    }

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        registry.addEndpoint(STOMP_REGISTRY_ENDPOINT);
        registry.addEndpoint(STOMP_REGISTRY_ENDPOINT).withSockJS();
    }

}
