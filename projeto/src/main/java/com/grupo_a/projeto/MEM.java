package com.grupo_a.projeto;

import java.io.*;
import java.nio.file.*;

public class MEM {
    private String csvFileName;

    public MEM(String csvFileName) {
        String currentPath = "./projeto/src/main/java/com/grupo_a/projeto/files";
        this.csvFileName = Paths.get(currentPath, csvFileName).toString();
    }

    public void writeMessage(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFileName, true))) {
            writer.write(message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
