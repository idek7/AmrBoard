package me.amr.service.impl;

import com.google.common.collect.ImmutableList;
import me.amr.model.Article;
import me.amr.repository.ArticleRepository;
import me.amr.repository.RedisRepository;
import me.amr.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.data.redis.core.query.SortQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ArticleServiceImpl implements ArticleService {

    private static final int SIZE_PER_PAGE = 10;

    private AtomicLong sequenceId = new AtomicLong();

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private RedisRepository redisRepository;

    @Override
    public List<Article> getArticleList() {

        //Iterable<Article> list = articleRepository.findAll(new Sort(Sort.Direction.DESC, "id"));

        List<Article> list = articleRepository.findAll();
        list.sort((a, b) -> (int)(b.getId() - a.getId()));

        return ImmutableList.copyOf(list);
    }

    @Override
    public List<Article> getPagedList(int page) {

        List<Article> list = redisRepository.getSortedPageableList(page);

        /*Page<Article> list = articleRepository.findAll(PageRequest.of(page - 1, 10,  new Sort(
                new Sort.Order(Sort.Direction.DESC, "id"))));*/

        return ImmutableList.copyOf(list);
    }

    public Article getArticle(Long id) {
        return articleRepository.findById(id).get();
    }

    @Override
    public void addArticle(Article article) {

        long seqId = sequenceId.incrementAndGet();
        Article newArticle = Article.builder()
                .id(seqId)
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
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }
}