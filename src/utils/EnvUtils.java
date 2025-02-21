package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnvUtils {
    private static final Map<String, String> envMap = new HashMap<>();

    static {
        Path path = null;
        try {
            path = Paths.get(".env");
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                if (!line.trim().isEmpty() && !line.startsWith("#") && line.contains("=")) {
                    String[] parts = line.split("=", 2);
                    envMap.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load .env file at path: " + path, e);
        }
    }

    public static String get(String key) {
        return envMap.get(key);
    }
}