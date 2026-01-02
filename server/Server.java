package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashSet;
import java.util.Set;

public class Server {
    private static final int PORT = 1234;
    private static final int BUFFER_SIZE = 1024;
    private static Set<SocketAddress> clients = new HashSet<>();

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(new InetSocketAddress(PORT))) {
            System.out.println("UDP Chat Server started on port " + PORT);
            
            byte[] buffer = new byte[BUFFER_SIZE];
            
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                
                SocketAddress senderAddress = packet.getSocketAddress();
                String message = new String(packet.getData(), 0, packet.getLength());
                
                System.out.println("[RECEPTION] from " + senderAddress + " - Message: " + message);
                
                // Register client if new
                clients.add(senderAddress);
                
                // Broadcast to all other clients
                broadcast(socket, message, senderAddress);
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    private static void broadcast(DatagramSocket socket, String message, SocketAddress sender) {
        byte[] data = message.getBytes();
        for (SocketAddress client : clients) {
            if (!client.equals(sender)) {
                try {
                    DatagramPacket packet = new DatagramPacket(data, data.length, client);
                    socket.send(packet);
                } catch (IOException e) {
                    System.err.println("Error broadcasting to " + client + ": " + e.getMessage());
                }
            }
        }
    }
}
