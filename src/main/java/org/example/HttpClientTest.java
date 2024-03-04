package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class HttpClientTest {
//    private static final String USER_URL = "https://jsonplaceholder.typicode.com/users";
    public static void main(String[] args) throws IOException, InterruptedException {
        Address myAddress = new Address("Heroiv Krut", "Apt. 440", "Odesa", "768", new Geo("46.482952", "30.712481"));
        Company myCompany = new Company("Considine-Lockman", "User-centric fault-tolerant solution", "generate enterprise e-tailers");
        User user = new User.Builder("Dmytro", "Json")
                        .setEmail("email@gmail.com")
                        .setPhone("5555-6666")
                        .setWebsite("MyWebsite")
                        .setAddress(myAddress)
                        .setCompany(myCompany)
                                .build();
        //Task #1.1
        User createdUser = HttpUtil.sendPost(user);
        System.out.println(createdUser);

        //Task #1.2
        User putUser = HttpUtil.sendPut(createdUser);
        System.out.println(putUser);

        //Task #1.3
        int statusRemovedUser = HttpUtil.sendRemoveUser(createdUser);
        System.out.println("status of removed createdUser = " + statusRemovedUser);

        //Task #1.4
        List<User> userList = HttpUtil.sendGetAllUsers();
        for(User users : userList) {
            System.out.println("-------------");
            System.out.println(users);
        }

        //Task #1.5
        User userGet = HttpUtil.sendGet(5);
        System.out.println("userGet = " + userGet);

        //Task #1.6
          User userUsername = HttpUtil.sendGetByUsername("Bret");
          System.out.println(userUsername);

          //Task #2
        HttpUtil.commentsToFile(2);

          //Task #3
        List<UserTodos> userTodosList = HttpUtil.sendTodos(3);
        for(UserTodos userTodos : userTodosList) {
            System.out.println("-------------");
            System.out.println(userTodos);
        }

    }
}
