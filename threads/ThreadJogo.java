package threads;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import util.server.RespostaJogoServidor;

public class ThreadJogo extends Thread {

  private Socket client;
  Scanner scanner;
  PrintWriter printWriter;
  String msg = "";

  public ThreadJogo(Socket client) {
    this.client = client;
  }

  @Override
  public void run() {
    try {
      System.out.println(
        "Conectado ao endereço " + client.getInetAddress().getHostAddress()
      );
      System.out.println("\r\n Conectado a porta " + client.getLocalPort());
    } catch (Exception e) {
      System.out.println("Error on thread: " + e.getMessage());
    }

    try {
      scanner = new Scanner(client.getInputStream());
      printWriter = new PrintWriter(client.getOutputStream(), true);

      System.out.println("O servidor está sendo carregado...");

      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {}

      System.out.println("Jogo carregado. \r\n");

      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {}

      while (!msg.equalsIgnoreCase("Sair")) {
        msg = scanner.nextLine();
        if (msg.equalsIgnoreCase("1")) {
          System.out.println("O jogador escolheu a opção Jogador x Servidor");
          RespostaJogoServidor jogoVsServidor = new RespostaJogoServidor(client);
          jogoVsServidor.executarJogoVersusServidor();
        }
          if (msg.equalsIgnoreCase("2")) {
          System.out.println("O jogador escolheu a opção Jogador x Jogador");
        }

        if (msg.equalsIgnoreCase("Sair")){
          System.out.println("O jogador escolheu sair do jogo...");
        }
      }
    } catch (Exception e) {
      System.out.println("Erro na comunicação com o servidor");
    }

    //etapa de encerramento
    try {
      System.out.println(
        "Encerrando conexão com client " +
        client.getInetAddress().getHostAddress()
      );

      scanner.close();
      printWriter.close();
      client.close();
    } catch (Exception e) {
      System.out.println("Erro ao fechar as conexões");
    }
  }
}
