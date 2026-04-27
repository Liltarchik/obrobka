package com.lab2cegla;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CeglaService {
    // Файл даних буде зберігатися в домашній папці користувача
    private final String FILE_PATH = System.getProperty("user.home") + "/bricks_data.json";
    private final Gson gson = new Gson();

    public List<Cegla> findAll() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();
        try (Reader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<ArrayList<Cegla>>(){}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void saveToFile(List<Cegla> bricks) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(bricks, writer);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void add(Cegla brick) {
        List<Cegla> bricks = findAll();
        int newId = bricks.stream().mapToInt(Cegla::getId).max().orElse(0) + 1;
        brick.setId(newId); // Автогенерація ID
        bricks.add(brick);
        saveToFile(bricks);
    }

    public void update(Cegla updatedBrick) {
        List<Cegla> bricks = findAll();
        for (int i = 0; i < bricks.size(); i++) {
            if (bricks.get(i).getId() == updatedBrick.getId()) {
                bricks.set(i, updatedBrick);
                break;
            }
        }
        saveToFile(bricks);
    }

    public void delete(int id) {
        List<Cegla> bricks = findAll();
        bricks.removeIf(b -> b.getId() == id);
        saveToFile(bricks);
    }
}