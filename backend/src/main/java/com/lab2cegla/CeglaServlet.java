package com.lab2cegla;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/cegla") // Залишаємо шлях /cegla
public class CeglaServlet extends HttpServlet {
    private final CeglaService service = new CeglaService(); // Використовуємо сервіс цегли
    private final Gson gson = new Gson();

    // GET: Отримати список всієї цегли з JSON файлу
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write(gson.toJson(service.findAll()));
    }

    // POST: Створити нову цеглу
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Cegla newBrick = gson.fromJson(req.getReader(), Cegla.class);
        service.add(newBrick);
        resp.setStatus(201); // Created
    }

    // PUT: Оновити існуючу цеглу
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Cegla updatedBrick = gson.fromJson(req.getReader(), Cegla.class);
        service.update(updatedBrick);
    }

    // DELETE: Видалити цеглу за ID (передається як параметр)
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        service.delete(id);
    }
}