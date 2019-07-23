package me.amr.service;

import me.amr.model.Article2;
import me.amr.model.ArticlesObj;

import java.util.List;

public interface ArticleJpaService {

    List<Article2> getArticleList();

    ArticlesObj getPagedList(int page);

    Article2 getArticle(Long id);

    void addArticle(Article2 article);

    void updateArticle(Article2 article);

    void deleteArticle(Long id);
}
