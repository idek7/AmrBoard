package me.amr.service;

import me.amr.model.Article;

import java.util.List;

public interface ArticleService {

    List<Article> getArticleList();

    Article getArticle(String id);

    void addArticle(Article article);

    void updateArticle(Article article);

    void deleteArticle(String id);
}
