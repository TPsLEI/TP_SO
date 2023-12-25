package com.grupo_a.projeto;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class MEM {
    private String csvFileName;

    public MEM(String csvFileName) {
        String currentPath = "./projeto/src/main/java/com/grupo_a/projeto/files";
        this.csvFileName = Paths.get(currentPath, csvFileName).toString();
    }

    public synchronized void writeMessage(String message, String name) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(csvFileName, StandardCharsets.UTF_8, true))) {
            Logs.log("O Utilizador " + name + " enviou uma mensagem.");
            writer.write(message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

