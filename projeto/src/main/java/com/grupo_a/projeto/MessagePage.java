package com.grupo_a.projeto;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.LinkedBlockingQueue;

public class MessagePage extends BaseFrame {
    LinkedBlockingQueue<String> dataQueue = new LinkedBlockingQueue<>();

    MessagePage() {
        super("Message");
        
    }

}
