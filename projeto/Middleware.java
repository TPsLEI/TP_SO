package projeto;

public class Middleware {
    private Estacao estacao;

    public Middleware(Estacao estacao) {
        this.estacao = estacao;
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
}
