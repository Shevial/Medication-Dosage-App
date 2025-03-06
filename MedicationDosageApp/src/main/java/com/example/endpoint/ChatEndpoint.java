package com.example.endpoint;

import com.example.dto.ChatMessage;
import com.example.dto.JoinMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Clase que maneja las comunicaciones WebSocket para un chat en tiempo real.
 * Gestiona las conexiones, desconexiones y mensajes de los usuarios.
 *
 * @author Shevia
 * @version 1.0
 */
@Controller
public class ChatEndpoint {

    @Autowired
    private SimpMessageSendingOperations ops;

    //Guardamos los atributos de la sesion de cada usuario con respecto al ID de cada sesion
    private static final Map<String, Map<String, Object>> sessions  = new ConcurrentHashMap<>();

    /**
     * Maneja la entrada de un usuario al chat. Este método se llama cuando un usuario se une a la sala.
     * Asocia el nombre del usuario a su sesión y envía un mensaje de bienvenida al canal de respuestas.
     *
     * @param message El mensaje que contiene el nombre del usuario.
     * @param accessor El accesorio de la sesión para obtener el ID y atributos de la sesión.
     */

    @MessageMapping("/join")
    public void onJoin(@Payload JoinMessage message, SimpMessageHeaderAccessor accessor){


        System.out.println("=== NUEVO USUARIO ===");
        System.out.println("Username: " + message.username());

        if (accessor.getSessionAttributes() == null) {
            accessor.setSessionAttributes(new HashMap<>());
        }

        String sessionId = accessor.getSessionId();


        Map<String,Object> sessionAttributes = accessor.getSessionAttributes();
        System.out.println("sessionId: " + sessionId);
        System.out.println("sessionAttributes: " + sessionAttributes);

        String username = message.username();
        sessionAttributes.put("username", username);
        sessions.put(sessionId, sessionAttributes);


        var msg = new ChatMessage(username, username + " joined the chat");
        //enviar el mensaje
        this.ops.convertAndSend("/topic/responses", msg);
        //cuantos usuarios hay
        var usersMsg = new ChatMessage(username, getUserNames());
        this.ops.convertAndSend("/topic/users", usersMsg);

    }


    /**
     * Maneja el evento de desconexión de un usuario. Este método es llamado cuando un usuario abandona el chat.
     * Elimina al usuario de la lista de sesiones y actualiza la lista de usuarios conectados.
     *
     * @param event El evento de desconexión que contiene los detalles del usuario desconectado.
     */

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        System.out.println("=== DESCONEXION ===");
        System.out.println("Usuario: " + event.getUser());
        System.out.println("Headers: " + event.getMessage().getHeaders());

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        if (headerAccessor.getSessionAttributes() != null) {
            String username = (String) headerAccessor.getSessionAttributes().get("username");
            if (username != null) {
                System.out.println("User Disconnected: " + username);

                var msg = new ChatMessage(username, username + " left the chat");
                ops.convertAndSend("/topic/responses", msg);

                //remove session associated with username
                sessions.remove(headerAccessor.getSessionId());

                var updateUserList = new ChatMessage(username, getUserNames());
                ops.convertAndSend("/topic/users", updateUserList);
            }
        }
    }

    /**
     * Maneja los mensajes enviados por los usuarios al chat. Reenvía el mensaje al canal de respuestas.
     *
     * @param message El mensaje del usuario a ser enviado.
     * @param accessor El accesorio de la sesión que contiene el nombre del usuario.
     */

    @MessageMapping("/chat")
    public void onMessage(@Payload ChatMessage message, SimpMessageHeaderAccessor accessor) {
        System.out.println("[MENSAJE RECIBIDO] De: " + accessor.getSessionAttributes().get("username"));
        System.out.println("[CONTENIDO] " + message.body());
        System.out.println("Mensaje recibido: " + message);
        String username = (String) accessor.getSessionAttributes().get("username");
        if (username != null) {
            // Reasignamos el username desde la sesión para prevenir suplantación
            ChatMessage verifiedMessage = new ChatMessage(username, message.body());
            this.ops.convertAndSend("/topic/responses", verifiedMessage);
        }
    }
    
    /**
     * Obtiene los nombres de usuario de todos los usuarios conectados en la sesión actual.
     *
     * @return Una cadena con los nombres de usuario, separados por comas.
     */
    private String getUserNames() {
        return sessions.values().stream()
                .map(v -> v.get("username"))
                .filter(Objects::nonNull)
                .map(String::valueOf)
                .collect(Collectors.joining(","));

    }
}
