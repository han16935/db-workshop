package service;

import dto.CommentDto;
import repository.CommentRepository;

import java.util.List;

public class CommentService {
    private CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void addComment(CommentDto commentDto){
        commentRepository.addComment(commentDto);
    }

    // new added
    public List<CommentDto> getAllComments() {
        return commentRepository.getAllComments();
    }
}
