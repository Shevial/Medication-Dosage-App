import React, { useState, useEffect, useRef } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
/**
 *  Usuario se conecta (connect()), se suscribe a los canales y envía su nombre (submitJoin()).
 *  Al recibir un mensaje, se agrega a la lista sin refrescar (setMessages([...prev, msg])).
 *  Cuando cambia la lista de usuarios, se notifica a otras pestañas con broadcast.postMessage(userList).
 */
const ChatComponent = () => {
    const [messages, setMessages] = useState([]);
    const [users, setUsers] = useState([]);
    const [username, setUsername] = useState('');
    const [newMessage, setNewMessage] = useState('');
    const [isConnected, setIsConnected] = useState(false);
    const [showChat, setShowChat] = useState(false);
    const stompClient = useRef(null);
    const broadcast = useRef(new BroadcastChannel("chat_users"));


    useEffect(() => {
        if (stompClient.current?.connected) {
            // Suscripción a mensajes
            const responsesSubscription = stompClient.current.subscribe(
                '/topic/responses',
                (message) => {
                    try {
                        const msg = JSON.parse(message.body);
                        console.log('Mensaje recibido:', msg);
                        setMessages(prev => [...prev, {
                            username: msg.username,
                            content: msg.body 
                        }]);
                    } catch (error) {
                        console.error('Error parseando mensaje:', error);
                    }
                }
            );
    
            // Suscripción a usuarios
            const usersSubscription = stompClient.current.subscribe(
                '/topic/users',
                (message) => {
                    try {
                        const msg = JSON.parse(message.body);
                        const userList = msg.body.split(',').filter(u => u && u !== 'null');

                        setUsers(userList);
                        console.log('Usuarios recibidos:', userList);
                        broadcast.current.postMessage(userList); 
                    } catch (error) {
                        console.error('Error parseando usuarios:', error);
                    }
                }
            
            );
    
            return () => {
               if(responsesSubscription) responsesSubscription.unsubscribe();
                usersSubscription.unsubscribe();
            };
        }
    }, [stompClient.current?.connected]);

    useEffect(() => {
        broadcast.current.onmessage = (event) => {
            console.log("Usuarios sincronizados entre pestañas:", event.data);
            setUsers(event.data);
        };
    }, []);

    const connect = () => {
        const socket = new SockJS('http://localhost:8080/websocket-server');
        stompClient.current = new Client({
            webSocketFactory: () => socket,
            debug: (str) => console.log("[STOMP] " + str),
            onConnect: () => {
                console.log("Conectado exitosamente al WebSocket");
                setIsConnected(true);
                setTimeout(() => {
                    if (username) {
                        submitJoin(username);
                    }
                }, 100); // 100ms de espera para asegurar que la conexión esté establecida
                onStompError: (frame) => {
                    console.error("Error en STOMP:", frame.headers.message);
                };
                
            }
        });
        stompClient.current.activate();
    };

    const disconnect = () => {
        if (stompClient.current) {
            stompClient.current.deactivate();
            setIsConnected(false);
            setMessages([]);
            setUsers([]);
        }
    };

    const submitJoin = (username) => {
        if (stompClient.current && stompClient.current.connected) {
            const joinMessage = { username };
            stompClient.current.publish({
                destination: '/app/join',
                body: JSON.stringify(joinMessage)
            });
        }
    };

    const submitMessage = (e) => {
        e.preventDefault();
        if (stompClient.current && stompClient.current.connected && newMessage.trim()) {
            const chatMessage = {
                username,
                body: newMessage
            };
            stompClient.current.publish({
                destination: '/app/chat',
                body: JSON.stringify(chatMessage)
            });
            setNewMessage('');
        }
    };

    const updateUserList = (userList) => {
        setUsers(userList.filter(user => user !== 'null' && user !== ''));
    };

    const showMsg = (message) => {
        setMessages(prev => [...prev, {
            username: message.username,
            content: message.body 
        }]);
    };

    const handleConnect = (e) => {
        e.preventDefault();
        if (!isConnected && username.trim()) {
            connect();
        }
    };

    return (
        <div className="chat-container">
            <button 
                className="chat-toggle-button"
                onClick={() => setShowChat(!showChat)}
            >
                {showChat ? 'Close Chat' : 'Open Chat'}
            </button>

            {showChat && (
                <div className="chat-window">
                    {!isConnected ? (
                        <form onSubmit={handleConnect} className="chat-form">
                            <input
                                type="text"
                                placeholder="Enter username"
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                                required
                            />
                            <button type="submit">Join Chat</button>
                        </form>
                    ) : (
                        <div className="chat-content">
                            <div className="user-list">
                                <h3>Online Users:</h3>
                                <ul>
                                    {users.map((user, index) => (
                                        <li key={index}>{user}</li>
                                    ))}
                                </ul>
                            </div>
                            
                            <div className="message-container">
                                {messages.map((msg, index) => (
                                     <div key={index} className="message">
                                    <strong>{msg.username}:</strong> {msg.content}
                                    </div>
                                        ))}
                            </div>
                            
                            <form onSubmit={submitMessage} className="message-form">
                                <input
                                    type="text"
                                    value={newMessage}
                                    onChange={(e) => setNewMessage(e.target.value)}
                                    placeholder="Type a message..."
                                />
                                <button type="submit">Send</button>
                            </form>
                        </div>
                    )}
                </div>
            )}
        </div>
    );
};

export default ChatComponent;