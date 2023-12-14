package projeto;

import java.util.Scanner;

public class Middleware {
    public void getUserInput(Kernel kernel) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insere um número: ");
        String userInput = scanner.nextLine();

        kernel.displayData(userInput);
    }
}
