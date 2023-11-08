package com.psl.mapper;

import com.psl.pojo.Article;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ArticleMapper {

    Integer add(Article article);

    List<Article> list(Integer categoryId, Integer state);

    @Select("select * from tb_article where id = #{id}")
    Article detail(Integer id);

    Integer update(Article article);

    @Delete("delete from tb_article where id = #{id}")
    Integer delete(Integer id);
}
