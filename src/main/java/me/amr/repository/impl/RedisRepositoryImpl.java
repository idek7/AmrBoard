package me.amr.repository.impl;

import me.amr.model.Article;
import me.amr.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.data.redis.core.query.SortQueryBuilder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RedisRepositoryImpl implements RedisRepository {

    private static final String SORT_KEY = "sortKey";
    private static final int COUNT_PER_PAGE = 10;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Article> getSortedPageableList(int page) {

        List<Article> result = null;

        int offset = (page - 1) * COUNT_PER_PAGE;

        /*try {
            SortQuery<String> sortQuery = SortQueryBuilder.sort(SORT_KEY).by("hash*->id")
                    .get("hash*->id")
                    .get("hash*->title")
                    .get("hash*->author")
                    .order(SortParameters.Order.DESC)
                    .limit(offset, COUNT_PER_PAGE)
                    .build();
            List<String> sortRslt = redisTemplate.sort(sortQuery);
            result = redisTemplate.sort(sortQuery);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return result;*/

        List<String> list = sort("ids:Article","Article:id:*->createDate",offset, COUNT_PER_PAGE);

        return new ArrayList<Article>();
    }

    private <T> List<T> sortPageList(String key, boolean isDesc, int off,int num) throws Exception{
        SortQueryBuilder<String> builder = SortQueryBuilder.sort(key);
        builder.get("#");
        if(isDesc)
            builder.order(SortParameters.Order.DESC);
        builder.limit(off, num);
        List<String> cks = redisTemplate.sort(builder.build());
        List<T> result = new ArrayList<T>();
        for (String ck : cks) {
            //得到项目对象 by(subKey+ck);
        }

        return result;
    }

    /**
     * Paging query
     * @param key is generally a list of ids
     * @param by_pattern is sorted according to the value of this pattern. In addition to the regular pattern, it can also receive the pattern of the hash.
     * @param offset offset
     * @param count number of queries per query
     * @return returns the list of ids after paging
     */
    public List<String> sort(String key, String by_pattern, int offset, int count){
        return redisTemplate.sort(
                SortQueryBuilder
                        .sort(key)
                        .by(by_pattern)
                        .alphabetical(true)
                        .order(SortParameters.Order.DESC)
                        .limit(offset,count)
                        .build()
        );
    }

}
