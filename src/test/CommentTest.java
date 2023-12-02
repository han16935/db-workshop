package test;

import config.AppConfig;
import dto.CommentDto;
import service.CommentService;

import java.time.LocalDateTime;

public class CommentTest {
    private static CommentService commentService = new AppConfig().commentService();


    public static void main(String[] args) {
        CommentDto commentDto = new CommentDto(1, 6, LocalDateTime.now(), "comment test");

        System.out.println("addCommentTest");

        commentService.addComment(commentDto);

        System.out.println("addCommentTest complete!");
    }
}
