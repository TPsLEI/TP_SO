package com.grupo_a.projeto;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MEM {
    private String csvFileName;

    public MEM(String csvFileName) {
        this.csvFileName = csvFileName;
    }

    public void writeMessage(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFileName, true))) {
            writer.write(message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
