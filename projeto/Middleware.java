package projeto;

import java.util.concurrent.*;

public class Middleware {
    private Estacao estacao;
    private BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();

    public Middleware(Estacao estacao) {
        this.estacao = estacao;
    }

    public Middleware() {
    }

    public void sendDataToKernel() {
        Thread thread = new Thread(() -> {
            String name = estacao.getName();
            String username = estacao.getUsername();
            String password = estacao.getPassword();

            Kernel kernel = new Kernel();
            kernel.receiveData(name, username, password);
        });

        thread.start();
    }

    public void waitMessageFromStation() {
        Thread thread = new Thread(() -> {
            try {
                String message = messageQueue.take();

                System.out.println("Mensagem recebida: " + message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread.start();
    }

    public void sendMessageToStation(String message) {
        messageQueue.offer(message);
    }
}
