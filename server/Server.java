package server;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        ServerSocket server;
        
        final int PORT = 12345;

        try {
            server = new ServerSocket(PORT);

            while (true) {
                System.out.println("Aguardando um cliente...");
                Socket client = server.accept();
                ThreadJogo jogo = new ThreadJogo(client);
                jogo.start();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}