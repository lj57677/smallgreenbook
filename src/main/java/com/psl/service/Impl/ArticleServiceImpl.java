package com.psl.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.psl.mapper.ArticleMapper;
import com.psl.pojo.Article;
import com.psl.pojo.PageBean;
import com.psl.pojo.Result;
import com.psl.service.ArticleService;
import com.psl.utils.ThreadLocalUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public Result addArticle(Article article) {
        //1、补充并封装Article信息 createUser createTime updateTime
        Map<String, Object> loginUserMap = ThreadLocalUtil.get();
        Integer userId = (Integer) loginUserMap.get("id");
        article.setCreateUser(userId);
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        //2、添加入数据库
        Integer affectedRows = articleMapper.add(article);
        if (affectedRows != null && affectedRows > 0) {
            return Result.success();
        }
        return Result.error("发布失败！");
    }

    @Override
    public PageBean list(Integer pageNum, Integer pageSize, Integer categoryId, Integer state) {
        //1、开启PageHelper分页查询
        PageHelper.startPage(pageNum, pageSize);
        //2、查询文章列表
        List<Article> articleList = articleMapper.list(categoryId, state);
        //将list强转为Page
        Page<Article> articlePage = (Page<Article>) articleList;
        //3、封装pageBean返回
        return new PageBean(articlePage.getTotal(), articlePage.getResult());
    }

    @Override
    public Result detail(Integer id) {
        Article article = articleMapper.detail(id);
        if (article == null) {
            Result.error("此文章不存在！");
        }
        return Result.success(article);
    }

    @Override
    public Result update(Article article) {
        //封装更新时间
        article.setUpdateTime(LocalDateTime.now());
        Integer affectedRows = articleMapper.update(article);
        if (affectedRows != null && affectedRows > 0) {
            return Result.success();
        }
        return Result.error("更新失败！");
    }

    @Override
    public Result delete(Integer id) {
        Integer affectedRows = articleMapper.delete(id);
        if (affectedRows != null && affectedRows > 0) {
            return Result.success();
        }
        return Result.error("删除失败！");
    }
}
