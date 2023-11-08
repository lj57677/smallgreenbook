package com.psl.controller;

import com.psl.pojo.Article;
import com.psl.pojo.PageBean;
import com.psl.pojo.Result;
import com.psl.service.ArticleService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    //发布文章
    @PostMapping
    public Result addArticle(@RequestBody @Validated(value = Article.Add.class) Article article) {
        return articleService.addArticle(article);
    }

    //分页查询文章列表
    @GetMapping
    public Result list(Integer pageNum,
                       Integer pageSize,
                       @RequestParam(required = false) Integer categoryId,
                       @RequestParam(required = false) Integer state
    ) {
        PageBean pageBean = articleService.list(pageNum, pageSize, categoryId, state);
        return Result.success(pageBean);
    }

    //根据id获取文章详细信息
    @GetMapping("/detail")
    public Result detail(Integer id){
        return articleService.detail(id);
    }

    //更新文章信息
    @PutMapping
    public Result update(@RequestBody @Validated(value = Article.Update.class) Article article){
        return articleService.update(article);
    }

    //删除文章
    @DeleteMapping
    public Result delete(Integer id){
        return articleService.delete(id);
    }
}
