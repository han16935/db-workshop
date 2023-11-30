package service;

import dto.ArticleDto;
import repository.ArticleRepository;
import repository.CommentRepository;
import repository.FollowRepository;

import java.util.List;

public class ArticleService {
    private ArticleRepository articleRepository;
    private CommentRepository commentRepository;
    private FollowRepository followRepository;

    public ArticleService(ArticleRepository articleRepository, CommentRepository commentRepository, FollowRepository followRepository) {
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
        this.followRepository = followRepository;
    }

    public List<ArticleDto> getAllArticles(int boardOwnerId){
        List<ArticleDto> allArticles;
        if(!followRepository.hasOwnerFollowRelationship(boardOwnerId))
            allArticles = articleRepository.getAllArticlesWithoutFollow(boardOwnerId);

        else allArticles = articleRepository.getAllArticles(boardOwnerId);

        return commentRepository.getAllComments(allArticles);
    }

    // article 추가, articleDto에는
    // ownerId, writerId, content, createdDate 담겨 있어야
    public void addArticle(ArticleDto articleDto){
       articleRepository.addArticle(articleDto);
    }
}
