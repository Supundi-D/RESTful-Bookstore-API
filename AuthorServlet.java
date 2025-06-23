package com.supundi.bookstoresample;

import com.supundi.bookstoresample.model.Author;
import com.supundi.bookstoresample.services.AuthorService;
import com.supundi.bookstoresample.services.BookService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.supundi.bookstoresample.model.Book;
import com.supundi.bookstoresample.services.DataStore;
import java.io.PrintWriter;
import java.util.List;

public class AuthorServlet extends HttpServlet {
    
   

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String pathInfo = request.getPathInfo(); 
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    Gson gson = new Gson();

    if (pathInfo == null || pathInfo.equals("/")) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(gson.toJson(DataStore.authors.values()));
    } else {
        String idStr = pathInfo.substring(1);
        try {
            int authId = Integer.parseInt(idStr);
            Author author = DataStore.authors.get(authId);

            if (author != null) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(gson.toJson(author));
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"Author not found\"}");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid author ID\"}");
        }
    }
}



@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setContentType("application/json");
    PrintWriter out = response.getWriter();

    // Read JSON body
    StringBuilder jsonBuffer = new StringBuilder();
    String line;
    BufferedReader reader = request.getReader();
    while ((line = reader.readLine()) != null) {
        jsonBuffer.append(line);
    }

    // Parse JSON
    Gson gson = new Gson();
    JsonObject jsonObject = JsonParser.parseString(jsonBuffer.toString()).getAsJsonObject();

    int authId = jsonObject.get("auth_id").getAsInt();
    String authName = jsonObject.get("auth_name").getAsString();
    String biography = jsonObject.get("biography").getAsString();

    // Create Author object
    Author newAuthor = new Author(authId, authName, biography);

    // Save into DataStore
    DataStore.authors.put(authId, newAuthor);

    // Build response JSON
    JsonObject responseJson = new JsonObject();
    responseJson.addProperty("message", "POST request received successfully!");

    JsonObject authorJson = new JsonObject();
    authorJson.addProperty("auth_id", authId);
    authorJson.addProperty("auth_name", authName);
    authorJson.addProperty("biography", biography);

    responseJson.add("author", authorJson);

    out.println(gson.toJson(responseJson));
}



    
    @Override
protected void doPut(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String pathInfo = request.getPathInfo();  // e.g., /1
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    if (pathInfo == null || pathInfo.equals("/")) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().println("{\"error\": \"Author ID is required.\"}");
        return;
    }

    String authIdStr = pathInfo.substring(1); // remove leading '/'
    try {
        int authId = Integer.parseInt(authIdStr);

        // Check if the author exists
        Author existingAuthor = DataStore.authors.get(authId);

        if (existingAuthor != null) {
            // Read the updated data from body
            StringBuilder jsonBuffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }

            Gson gson = new Gson();
            JsonObject jsonObject = JsonParser.parseString(jsonBuffer.toString()).getAsJsonObject();

            String newAuthName = jsonObject.get("auth_name").getAsString();
            String newBiography = jsonObject.get("biography").getAsString();

            // Update author object
            existingAuthor.setAuth_name(newAuthName);
            existingAuthor.setBiography(newBiography);

            response.getWriter().println("{\"message\": \"Author updated successfully.\", \"author\":" + gson.toJson(existingAuthor) + "}");

        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().println("{\"error\": \"Author with ID " + authId + " not found.\"}");
        }

    } catch (NumberFormatException e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().println("{\"error\": \"Invalid author ID format.\"}");
    }
}


@Override
protected void doDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String pathInfo = request.getPathInfo();  // e.g., /1 for /api/authors/1
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    if (pathInfo == null || pathInfo.equals("/")) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().println("{\"error\": \"Author ID is required.\"}");
        return;
    }

    String authIdStr = pathInfo.substring(1); // Remove the starting '/'
    try {
        int authId = Integer.parseInt(authIdStr);

        if (DataStore.authors.containsKey(authId)) {
            //  Actually delete the author
            DataStore.authors.remove(authId);

            response.getWriter().println("{\"message\": \"Author with ID " + authId + " deleted.\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().println("{\"error\": \"Author with ID " + authId + " not found.\"}");
        }

    } catch (NumberFormatException e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().println("{\"error\": \"Invalid author ID format.\"}");
    }
}

   
}