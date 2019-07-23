package me.amr.rest;

import io.swagger.annotations.Api;
import me.amr.model.Article;
import me.amr.model.Article2;
import me.amr.model.ArticlesObj;
import me.amr.service.ArticleJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/article2")
@Api(value = "ArticleController", description = "Article REST API", basePath = "/api")
public class ArticleJpaController {

    @Autowired
    private ArticleJpaService articleService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Article2> getArticleList() {
        return articleService.getArticleList();
    }

    @RequestMapping(value = "/list/{page}", method = RequestMethod.GET)
    public ResponseEntity getPageList(@PathVariable("page") int page) {

        return new ResponseEntity<ArticlesObj>(articleService.getPagedList(page), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Article2 getArticle(@PathVariable("id") Long id) {
        return articleService.getArticle(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity addArticle(@Valid @RequestBody Article2 article) {
        articleService.addArticle(article);

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity updateArticle(@Valid @RequestBody Article2 article) {
        articleService.updateArticle(article);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteArticle(@PathVariable("id") Long id) {
        articleService.deleteArticle(id);

        return new ResponseEntity(HttpStatus.OK);
    }
}
