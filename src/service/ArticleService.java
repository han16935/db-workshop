package service;

import dto.ArticleDto;
import repository.ArticleRepository;

import java.util.Arrays;
import java.util.List;

public class ArticleService {
    private ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<ArticleDto> getAllArticles(int boardOwnerId){
        return articleRepository.getAllArticles(boardOwnerId);
    }
}
