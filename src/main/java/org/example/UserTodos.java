package org.example;

public class UserTodos {
    private Integer userId;
    private Integer id;
    private String title;
    private Boolean completed;

    public Boolean getCompleted() {
        return completed;
    }

    @Override
    public String toString() {
        return "UserTodos{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", completed=" + completed +
                '}';
    }
}
