package service;

import dto.ArticleDto;
import repository.ArticleRepository;
import repository.CommentRepository;

import javax.xml.stream.events.Comment;
import java.util.Arrays;
import java.util.List;

public class ArticleService {
    private ArticleRepository articleRepository;
    private CommentRepository commentRepository;

    public ArticleService(ArticleRepository articleRepository, CommentRepository commentRepository) {
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
    }

    public List<ArticleDto> getAllArticles(int boardOwnerId){
        List<ArticleDto> allArticles;
        if(!commentRepository.getAllFollowRelationShip(boardOwnerId))
          allArticles = articleRepository.getAllArticlesWithoutFollow(boardOwnerId);

        else
          allArticles = articleRepository.getAllArticles(boardOwnerId);
        return commentRepository.getAllComments(allArticles);
    }
}
