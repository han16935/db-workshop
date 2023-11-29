package test.articleTest;

import config.AppConfig;
import dto.ArticleDto;
import service.ArticleService;

import java.util.List;

public class articleTest {
    static ArticleService articleService = new AppConfig().articleService();
    public static void main(String[] args) {
        List<ArticleDto> articles = articleService.getAllArticles(9);
        System.out.println("test");
        for (ArticleDto article : articles) {
            System.out.println(article);
        }
    }
}
