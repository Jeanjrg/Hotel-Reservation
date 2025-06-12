package com.vscode.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vscode.model.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

import com.google.gson.reflect.TypeToken;

public class JsonDatabase {
private static final String BASE_PATH = "data/";
private static final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).setPrettyPrinting().create();
}