package me.amr.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ArticlesObj {
    private long count;
    private List<Article2> list;

    public ArticlesObj() {}

    @Builder
    public ArticlesObj(long count, List<Article2> list) {
        this.count = count;
        this.list = list;
    }
}
