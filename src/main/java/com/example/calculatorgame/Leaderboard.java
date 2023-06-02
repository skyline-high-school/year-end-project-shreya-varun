package com.example.calculatorgame;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Leaderboard {
    public static ArrayList<Record> records = new ArrayList<>();
    static TypeReference<ArrayList<Record>> typeReference = new TypeReference<>() {};
    static ObjectMapper objectMapper = new ObjectMapper();
    static File leaderboardJSON = new File("src/main/resources/com/example/calculatorgame/leaderboard.json");

    public static void initialize() {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            importLeaderboards();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void updateLeaderboards() throws IOException {
        Collections.sort(records);
        objectMapper.writeValue(leaderboardJSON, records);
    }
    public static void importLeaderboards() throws IOException {
        records = objectMapper.readValue(leaderboardJSON, typeReference);
        Collections.sort(records);
    }
    public static void submitScore(String username, double finalTime) throws IOException {
        if (username.length() > 16) username = username.substring(0, 16);
        records.add(new Record(username, finalTime));
        updateLeaderboards();
    }
}

