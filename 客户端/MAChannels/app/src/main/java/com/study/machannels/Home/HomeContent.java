package com.study.machannels.Home;

public class HomeContent {
    private int image;
    private String title,time,content;

    public HomeContent(int image, String title, String time, String content) {
        this.image = image;
        this.title = title;
        this.time = time;
        this.content = content;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

}
