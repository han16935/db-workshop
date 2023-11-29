package dto;

import java.time.LocalDateTime;

public class ArticleDto {
    private int id;
    private int boardId;
    private int writerId;
    private String title;
    private String content;

    public int getId() {
        return id;
    }

    public int getBoardId() {
        return boardId;
    }

    public int getWriterId() {
        return writerId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    private LocalDateTime createdDate;

    public ArticleDto(int id, int boardId, int writerId, String title, String content, LocalDateTime createdDate) {
        this.id = id;
        this.boardId = boardId;
        this.writerId = writerId;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
    }

    public ArticleDto(int boardId, int writerId, String title, String content, LocalDateTime createdDate) {
        this.boardId = boardId;
        this.writerId = writerId;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
    }
}
