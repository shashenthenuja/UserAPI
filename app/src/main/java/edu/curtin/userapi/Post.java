package edu.curtin.userapi;

public class Post {
    private int userId;
    private int id;
    private String title;
    private String body;

    public Post(int userId, int id) {
        this.userId = userId;
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
