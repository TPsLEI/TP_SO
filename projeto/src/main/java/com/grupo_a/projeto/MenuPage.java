package com.grupo_a.projeto;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.LinkedBlockingQueue;

public class MenuPage extends BaseFrame {
    LinkedBlockingQueue<String> dataQueue = new LinkedBlockingQueue<>();

    MenuPage() {
        setTitle("Menu");
        Middleware middleware = new Middleware(dataQueue);

        MEM mem = new MEM("dados.csv");
        CPU thread = new CPU(dataQueue, mem);
        thread.start();
    }

}
