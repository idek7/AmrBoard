package me.amr.service;

import me.amr.model.Article;

import java.util.List;

public interface ArticleService {

    List<Article> getArticleList();

    List<Article> getPagedList(int page);

    Article getArticle(Long id);

    void addArticle(Article article);

    void updateArticle(Article article);

    void deleteArticle(Long id);
}
