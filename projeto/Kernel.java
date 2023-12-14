package projeto;

public class Kernel {

    public void start() {
        Middleware middleware = new Middleware();
        middleware.getUserInput(this);
    }

    public void displayData(String data) {
        System.out.println("Número: " + data);
    }

    public static void main(String[] args) {
        Kernel kernel = new Kernel();

        kernel.start();
    }
}
