package dto;

import java.time.LocalDateTime;

public class CommentDto {
    private int id;
    private int articleId;
    private int writerId;
    private LocalDateTime createdDate;
    private String content;

    public CommentDto(int id, int articleId, int writerId, LocalDateTime createdDate, String content) {
        this.id = id;
        this.articleId = articleId;
        this.writerId = writerId;
        this.createdDate = createdDate;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public int getArticleId() {
        return articleId;
    }

    public int getWriterId() {
        return writerId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", writerId=" + writerId +
                ", createdDate=" + createdDate +
                ", content='" + content + '\'' +
                '}';
    }
}
