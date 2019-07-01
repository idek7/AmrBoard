package me.amr;


import com.google.common.collect.Lists;
import me.amr.model.Article;
import me.amr.repository.ArticleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleUnitTest {

    private Logger logger = LoggerFactory.getLogger(ArticleUnitTest.class);

    private static final String TEST_USER_NAME = "test-user";

    private AtomicInteger sequenceId = new AtomicInteger();

    @Autowired
    private ArticleRepository articleRepository;

    private Article createArticle() {
        int id = sequenceId.incrementAndGet();

        return Article.builder()
                .id(String.valueOf(id))
                .title("test" + id)
                .content("테스트" + id + " 입니다.")
                .author(TEST_USER_NAME)
                .build();
    }

    private Article[] createArticleList(int count) {
        Article[] data = new Article[count];
        for (int i = 0; i < count; i++) {
            data[i] = createArticle();
        }

        return data;
    }

    /**
     * 사용자는 텍스트로 된 공지를 추가할 수 있다.
     */
    @Test
    public void 공지를_추가할_수_있다() {
        //given
        Article article = createArticle();

        //when
        articleRepository.save(article);

        //then
        Article saveArticle = articleRepository.findById(article.getId()).get();
        assertEquals(saveArticle.getTitle(), article.getTitle());
        assertEquals(saveArticle.getContent(), article.getContent());
        assertEquals(saveArticle.getAuthor(), article.getAuthor());
    }

    /**
     * 사용자는 공지목록을 조회할 수 있다.
     */
    @Test
    public void 공지목록을_조회할_수_있다() {
        //given
        Article[] list = createArticleList(3);

        //when
        for (Article article : list) {
            articleRepository.save(article);
        }

        //then
        List<Article> articleList = Lists.newArrayList(articleRepository.findAll());
        long total = articleRepository.count();
        assertTrue(articleList.size() == total);

        //debug
        logger.debug("total article count = {}", total);
        for (Article item : articleList) {
            logger.debug(item.toString());
        }
    }

    /**
     * 사용자는 공지를 삭제할 수 있다.
     */
    @Test
    public void 공지를_수정할_수_있다() {
        //given
        Article article = createArticle();
        String id = article.getId();
        String updateTitle = "test 수정";
        String updateContent = "수정된 테스트 입니다.";

        //when
        Article saveArticle = articleRepository.findById(id).get();
        saveArticle.update(updateTitle, updateContent);
        articleRepository.save(saveArticle);

        //then
        Article modifyArticle = articleRepository.findById(id).get();
        assertEquals(modifyArticle.getTitle(), saveArticle.getTitle());
        assertEquals(modifyArticle.getContent(), saveArticle.getContent());
        assertNotEquals(modifyArticle.getTitle(), article.getTitle());
        assertNotEquals(modifyArticle.getContent(), article.getContent());
    }

    /**
     * 사용자는 공지를 삭제할 수 있다.
     */
    @Test
    public void 공지를_삭제할_수_있다() {
        //given
        Article article = createArticle();
        String id = article.getId();

        //when
        articleRepository.deleteById(id);

        //then
        Optional<Article> deleteArticle = articleRepository.findById(id);
        assertTrue(!deleteArticle.isPresent());
    }
}
