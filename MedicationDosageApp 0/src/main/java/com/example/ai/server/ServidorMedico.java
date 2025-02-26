package com.example.ai.server;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ServidorMedico {
    private static final int PORT = 5000;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("🟢 Servidor iniciado en puerto " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                // Hilo para manejar comunicación bidireccional
                new Thread(() -> manejarCliente(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Error en servidor: " + e.getMessage());
        }
    }

    /**
     * Maneja la conexión con un cliente específico.
     * - Crea dos hilos: uno para recibir mensajes y otro para enviar.
     * @param socket Socket del cliente conectado.
     */
    private static void manejarCliente(Socket socket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Scanner scanner = new Scanner(System.in);

            // Hilo para RECIBIR mensajes del cliente
            new Thread(() -> {
                try {
                    String mensaje;
                    while ((mensaje = in.readLine()) != null) { // Bloquea hasta recibir datos
                        synchronized(System.out) {
                            // Borra (o salta) la línea actual si es posible (usar secuencias ANSI) o simplemente imprime en una nueva línea.
                            System.out.println("\nCliente dice: " + mensaje);
                            //System.out.print("Servidor (escribe mensaje): ");
                        }                    }
                } catch (IOException e) {
                    System.err.println("Error leyendo del cliente: " + e.getMessage());
                }
            }).start();

            // Hilo para ENVIAR mensajes al cliente
            new Thread(() -> {
                try {
                    while (true) {
                        //System.out.print("Servidor (escribe mensaje): ");
                        String mensaje = scanner.nextLine();
                        out.write(mensaje);
                        out.newLine();
                        out.flush(); // Forzar envío inmediato
                    }
                } catch (IOException e) {
                    System.err.println("Error enviando al cliente: " + e.getMessage());
                }
            }).start();

        } catch (IOException e) {
            System.err.println("Error inicializando streams: " + e.getMessage());
        }
    }

    /** Cierra el socket de manera segura */
    private static void cerrarSocket(Socket socket) {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
                System.out.println("Conexión cerrada con cliente");
            }
        } catch (IOException e) {
            System.err.println("Error cerrando socket: " + e.getMessage());
        }
    }
}