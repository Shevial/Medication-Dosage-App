package com.example.ai.client;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ClientePaciente {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int PORT = 5000;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("✅ Conectado al servidor");

            // Hilo para RECIBIR mensajes del servidor
            new Thread(() -> {
                try {
                    String mensaje;
                    while ((mensaje = in.readLine()) != null) {
                        System.out.println("\nServidor dice: " + mensaje);
                        //System.out.print("Tú: "); // Mantener prompt visible
                    }
                } catch (IOException e) {
                    System.err.println("Conexión cerrada por el servidor");
                }
            }).start();

            // Hilo principal para ENVIAR mensajes al servidor
            while (true) {
                //System.out.print("Tú: ");
                String mensaje = scanner.nextLine();
                out.write(mensaje);
                out.newLine();
                out.flush();
            }

        } catch (IOException e) {
            System.err.println("Error en cliente: " + e.getMessage());
        }
    }
}