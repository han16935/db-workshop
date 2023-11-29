package dto;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleDto {
    private int id;
    private int ownerId;
    private int writerId;
    private String content;
    private LocalDateTime createdDate;
    private List<CommentDto> comments;

    public ArticleDto(int id, int ownerId, int writerId, String content, LocalDateTime createdDate) {
        this.id = id;
        this.ownerId = ownerId;
        this.writerId = writerId;
        this.content = content;
        this.createdDate = createdDate;
    }
    public int getId() {
        return id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public int getWriterId() {
        return writerId;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void addComments(List<CommentDto> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "ArticleDto{" +
                "id=" + id +
                ", ownerId=" + ownerId +
                ", writerId=" + writerId +
                ", content='" + content + '\'' +
                ", createdDate=" + createdDate +
                ", comments=" + comments +
                '}';
    }
}
