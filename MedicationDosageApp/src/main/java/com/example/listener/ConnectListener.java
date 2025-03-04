package com.example.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Component
public class ConnectListener implements ApplicationListener<SessionConnectEvent>{

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        System.out.println("SessionConnectEvent!");
        System.out.println(event);
    }

}
