package com.lab2cegla;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cegla")
public class CeglaServlet extends HttpServlet {
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        List<Cegla> inventory = new ArrayList<>();
        // Використовуємо твій клас cegla
        inventory.add(new Cegla("Цегла силікатна", "М150", "Одинарна", 6.50));
        inventory.add(new Cegla("Цегла керамічна*/", "М125", "Пустотіла", 8.20));
        inventory.add(new Cegla("Клінкерна цегла", "М300", "Облицювальна", 15.00));

        response.getWriter().write(gson.toJson(inventory));
    }
}