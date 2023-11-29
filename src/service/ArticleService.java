package service;

import dto.ArticleDto;
import repository.ArticleRepository;

import java.util.Arrays;

public class ArticleService {
    private ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public ArticleDto[] getAllArticles(int boardOwnerId){
        return (ArticleDto[]) articleRepository.getAllArticles(boardOwnerId).toArray();
    }
}
