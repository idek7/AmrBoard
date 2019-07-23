package me.amr.repository;

import me.amr.model.Article2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleJpaRepository extends JpaRepository<Article2, Long> {
}
