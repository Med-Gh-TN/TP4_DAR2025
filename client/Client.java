package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 1234;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress serverIP = InetAddress.getByName(SERVER_ADDRESS);

            // Start Receiver Thread
            new Thread(new Receiver(socket)).start();

            System.out.println("You can now start chatting!");
            while (true) {
                String msg = scanner.nextLine();
                if (msg.equalsIgnoreCase("exit"))
                    break;

                String fullMessage = "[" + username + "] : " + msg;
                byte[] data = fullMessage.getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, serverIP, SERVER_PORT);
                socket.send(packet);
            }
        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        }
    }

    private static class Receiver implements Runnable {
        private DatagramSocket socket;

        public Receiver(DatagramSocket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            byte[] buffer = new byte[BUFFER_SIZE];
            try {
                while (true) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    String received = new String(packet.getData(), 0, packet.getLength());
                    System.out.println("\n" + received);
                    System.out.print("> "); // Prompt for typing
                }
            } catch (IOException e) {
                if (!socket.isClosed()) {
                    System.err.println("Receiver error: " + e.getMessage());
                }
            }
        }
    }
}
