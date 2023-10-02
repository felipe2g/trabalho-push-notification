package logpool.example.logpool.models.dto;

import java.util.List;

public class PostDTO {
    private String title;
    private String authorName;
    private String body;
    private List<TagDTO> tags;

    public PostDTO() {
    }

    public PostDTO(String title, String authorName, String body, List<TagDTO> tags) {
        this.title = title;
        this.authorName = authorName;
        this.body = body;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagDTO> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "PostDTO{" +
                "title='" + title + '\'' +
                ", authorName='" + authorName + '\'' +
                ", body='" + body + '\'' +
                ", tags=" + tags +
                '}';
    }
}
