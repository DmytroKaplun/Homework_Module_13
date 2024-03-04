package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class HttpUtil {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final String USER_URL = "https://jsonplaceholder.typicode.com/users/";
    private static final String USER_UPDATE = "https://jsonplaceholder.typicode.com/users/1";
    private static final String LAST_POST_COMMENTS = "https://jsonplaceholder.typicode.com/comments?postId=";
    private static final String USERNAME = "https://jsonplaceholder.typicode.com/users?username=";
//    private static final String USER_TODOS = "https://jsonplaceholder.typicode.com/users/%d/todos";

    public static User sendPost(User user) throws IOException, InterruptedException {
        String requestBody = new Gson().toJson(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(USER_URL))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-type", "application/json; charset=UTF-8")
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        return new Gson().fromJson(response.body(), User.class);
    }

    public static User sendPut(User user) throws IOException, InterruptedException {
        String requestBody = new Gson().toJson(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(USER_UPDATE))
                .header("Content-type", "application/json; charset=UTF-8")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return new Gson().fromJson(response.body(), User.class);
    }

    public static int sendRemoveUser(User user) throws IOException, InterruptedException {
        String requestBody = new Gson().toJson(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(USER_UPDATE))
                .method("DELETE", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode();
    }

    public static List<User> sendGetAllUsers() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(USER_URL))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        return new Gson().fromJson(response.body(), new TypeToken<List<User>>(){}.getType());
    }

    public static User sendGet(Integer userId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(USER_URL + userId))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        return new Gson().fromJson(response.body(), User.class);
    }

    public static User sendGetByUsername(String username) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(USERNAME + username))
                .GET()
                .build();
        HttpResponse<String>  response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        List<User> userList = new Gson().fromJson(response.body(), new TypeToken<List<User>>(){}.getType());
        return userList.getFirst();
    }

    public static void commentsToFile(Integer userId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(String.format("%s=%d", USER_COMMENTS, userId)))
                .uri(URI.create(LAST_POST_COMMENTS + userId))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("JSON Response: " + response.body());
        List<Comment> comments = new Gson().fromJson(response.body(), new TypeToken<List<Comment>>(){}.getType());
        int lastPostId = userId * 10;
        try(FileOutputStream fOs = new FileOutputStream(String.format("user-%d-post-%d-comments.json", userId, lastPostId));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fOs))) {
            for (Comment comment : comments) {
                bufferedWriter.write(new Gson().toJson(comment));
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<UserTodos> sendTodos(Integer userId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("https://jsonplaceholder.typicode.com/users/%d/todos", userId)))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        List<UserTodos> todosList = new Gson().fromJson(response.body(), new TypeToken<List<UserTodos>>(){}.getType());
        return todosList.stream().filter(todos -> !todos.getCompleted()).toList();
    }

}
