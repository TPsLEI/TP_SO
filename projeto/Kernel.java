package projeto;

import java.util.Scanner;

public class Kernel {

    static Scanner scanner = new Scanner(System.in);
    static Middleware middleware = new Middleware();

    public void receiveData(String name, String username, String password) {
        System.out.println("Received data in Kernel:");
        System.out.println("------------------------------");
        System.out.println("Name     : " + name);
        System.out.println("Username : " + username);
        System.out.println("Password : " + password);
        System.out.println("------------------------------");
    }

    public Estacao askForData() {
        Estacao estacao = new Estacao();

        System.out.println("Qual é o Nome? ");
        String name = scanner.nextLine();
        System.out.println("Qual é o Username? ");
        String username = scanner.nextLine();
        System.out.println("Qual é a Password? ");
        String password = scanner.nextLine();

        estacao.setName(name);
        estacao.setUsername(username);
        estacao.setPassword(password);

        return estacao;
    }

    public static void main(String[] args) {
        Kernel kernel = new Kernel();

        boolean dataProvided = false;
        while (!dataProvided) {
            Estacao estacao = kernel.askForData();

            if (!estacao.name.isEmpty() && !estacao.username.isEmpty() && !estacao.password.isEmpty()) {
                dataProvided = true;

                Middleware middleware = new Middleware(estacao);
                middleware.sendDataToKernel();
            } else {
                System.out.println("Por favor, preencha todos os campos.");
            }
        }

        middleware.waitMessageFromStation();
        middleware.sendMessageToStation("Olá");

        scanner.close();
    }
}
