package com.psl.service;

import com.psl.pojo.Article;
import com.psl.pojo.PageBean;
import com.psl.pojo.Result;


public interface ArticleService {
    Result addArticle(Article article);

    PageBean list(Integer pageNum, Integer pageSize, Integer categoryId, Integer state);

    Result detail(Integer id);

    Result update(Article article);

    Result delete(Integer id);
}
