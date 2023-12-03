package service;

import dto.ArticleDto;
import repository.ArticleRepository;
import repository.CommentRepository;
import repository.FollowRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

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
        if(articleDto.getFilePath() != null){
            String uuid = UUID.randomUUID().toString();
            String newPath = "C:\\Users\\han16\\Desktop\\Photo\\" + uuid + articleDto.getFilePath().substring(articleDto.getFilePath().lastIndexOf('.'));
            File photo = new File(articleDto.getFilePath());
            try {
                Files.move(Paths.get(articleDto.getFilePath()), Paths.get(newPath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            articleRepository.addArticleWithImage(new ArticleDto(articleDto.getOwnerId(), articleDto.getWriterId(), articleDto.getContent(),
                    articleDto.getCreatedDate(), newPath));
        }
        else articleRepository.addArticle(articleDto);
    }
}
