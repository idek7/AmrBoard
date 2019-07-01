package me.amr.service.impl;

import com.google.common.collect.ImmutableList;
import me.amr.model.Article;
import me.amr.repository.ArticleRepository;
import me.amr.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ArticleServiceImpl implements ArticleService {

    private static final int SIZE_PER_PAGE = 10;

    private AtomicInteger sequenceId = new AtomicInteger();

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public List<Article> getArticleList() {

        return ImmutableList.copyOf(articleRepository.findAll()).reverse();
    }

    @Override
    public Article getArticle(String id) {
        return articleRepository.findById(id).get();
    }

    @Override
    public void addArticle(Article article) {

        int seqId = sequenceId.incrementAndGet();
        Article newArticle = Article.builder()
                .id(String.valueOf(seqId))
                .title(article.getTitle())
                .content(article.getContent())
                .author(article.getAuthor())
                .build();

        articleRepository.save(newArticle);
    }

    @Override
    public void updateArticle(Article article) {
        Article updateArticle = articleRepository.findById(article.getId()).get();
        updateArticle.update(article.getTitle(), article.getContent());

        articleRepository.save(updateArticle);
    }

    @Override
    public void deleteArticle(String id) {
        articleRepository.deleteById(id);
    }
}