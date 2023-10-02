package com.newsletterclient.newsletterclient.models.dtos;

import com.newsletterclient.newsletterclient.models.News;
import com.newsletterclient.newsletterclient.models.Post;
import com.newsletterclient.newsletterclient.models.Tag;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.List;

public class NewsDTO implements Serializable {
    private String id;
    private String title;
    private String date;
    private String editorName;
    private List<Post> posts;

    public NewsDTO() {}

    public NewsDTO(String id, String title, String date, String editorName, List<Post> posts) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.editorName = editorName;
        this.posts = posts;
    }

    public NewsDTO(News news) {
        if(news.getId() != null) {
            this.id = news.getId().toString();
        }
        this.title = news.getTitle();
        this.date = news.getDate();
        this.editorName = news.getEditorName();
        this.posts = news.getPosts();
    }

    public News toNews() {
        ObjectId id = null;
        if(this.id != null) {
            id = new ObjectId(this.id);
        }

        return new News(id, this.title, this.date, this.editorName, this.posts);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEditorName() {
        return editorName;
    }

    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
