package me.amr.service.impl;

import com.google.common.collect.ImmutableList;
import me.amr.model.Article;
import me.amr.model.Article2;
import me.amr.model.ArticlesObj;
import me.amr.repository.ArticleJpaRepository;
import me.amr.repository.RedisRepository;
import me.amr.service.ArticleJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ArticleJpaServiceImpl implements ArticleJpaService {

    private static final int SIZE_PER_PAGE = 10;

    private AtomicLong sequenceId = new AtomicLong();

    @Autowired
    private ArticleJpaRepository articleRepository;

    @Autowired
    private RedisRepository redisRepository;

    @Override
    public List<Article2> getArticleList() {

        //Iterable<Article> list = articleRepository.findAll(new Sort(Sort.Direction.DESC, "id"));

        List<Article2> list = articleRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        return ImmutableList.copyOf(list);
    }

    @Override
    public ArticlesObj getPagedList(int page) {
        Page<Article2> list = articleRepository.findAll(
                PageRequest.of(page - 1, 10,
                        Sort.by(new Sort.Order(Sort.Direction.DESC, "createDate"))
                )
        );

        return ArticlesObj.builder()
                .count(articleRepository.count())
                .list(ImmutableList.copyOf(list))
                .build();
    }

    public Article2 getArticle(Long id) {
        return articleRepository.findById(id).get();
    }

    @Override
    public void addArticle(Article2 article) {

        long seqId = sequenceId.incrementAndGet();
        Article2 newArticle = Article2.builder()
                .id(seqId)
                .title(article.getTitle())
                .content(article.getContent())
                .author(article.getAuthor())
                .build();

        articleRepository.save(newArticle);
    }

    @Override
    public void updateArticle(Article2 article) {
        Article2 updateArticle = articleRepository.findById(article.getId()).get();
        updateArticle.update(article.getTitle(), article.getContent());

        articleRepository.save(updateArticle);
    }

    @Override
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }
}