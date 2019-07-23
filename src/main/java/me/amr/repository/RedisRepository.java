package me.amr.repository;

import me.amr.model.Article;

import java.util.List;

public interface RedisRepository {

    List<Article> getSortedPageableList(int page);
}
