package service;

import dto.CommentDto;
import repository.CommentRepository;

public class CommentService {
    private CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void addComment(CommentDto commentDto){
        commentRepository.addComment(commentDto);
    }
}
