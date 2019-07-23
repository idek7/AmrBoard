package me.amr.repository;

import me.amr.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleRepository extends CrudRepository<Article, Long> {

    List<Article> findAll();

    Page<Article> findAll(Pageable pageable);

}
