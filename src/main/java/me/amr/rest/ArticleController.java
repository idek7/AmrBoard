package me.amr.rest;

import io.swagger.annotations.Api;
import me.amr.model.Article;
import me.amr.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/article")
@Api(value = "ArticleController", description = "Article REST API", basePath = "/api")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Article> getArticleList() {
        return articleService.getArticleList();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Article getArticle(@PathVariable("id") String id) {
        return articleService.getArticle(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity addArticle(@Valid @RequestBody Article article) {
        articleService.addArticle(article);

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity updateArticle(@Valid @RequestBody Article article) {
        articleService.updateArticle(article);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteArticle(@PathVariable("id") String id) {
        articleService.deleteArticle(id);

        return new ResponseEntity(HttpStatus.OK);
    }
}
