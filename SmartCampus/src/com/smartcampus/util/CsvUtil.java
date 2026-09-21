package com.smartcampus.util;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CsvUtil {

    public static void ensureFile(String path, String header) {
        try {
            Path p = Paths.get(path);
            if (p.getParent() != null) {
                Files.createDirectories(p.getParent());
            }
            if (!Files.exists(p)) {
                Files.write(p, (header + System.lineSeparator()).getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not create data file: " + path, e);
        }
    }

    public static List<String[]> readRows(String path) {
        List<String[]> rows = new ArrayList<>();
        Path p = Paths.get(path);
        if (!Files.exists(p)) return rows;
        try (BufferedReader br = Files.newBufferedReader(p)) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                rows.add(line.split(",", -1));
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not read data file: " + path, e);
        }
        return rows;
    }

    public static void appendRow(String path, String... fields) {
        try (BufferedWriter bw = Files.newBufferedWriter(
                Paths.get(path), StandardOpenOption.APPEND)) {
            bw.write(String.join(",", fields));
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Could not write to data file: " + path, e);
        }
    }

    public static void writeAll(String path, String header, List<String[]> rows) {
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(path))) {
            bw.write(header);
            bw.newLine();
            for (String[] row : rows) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not rewrite data file: " + path, e);
        }
    }
}
