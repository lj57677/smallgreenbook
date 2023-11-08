package com.psl.mapper;

import com.psl.pojo.Category;
import com.psl.pojo.Result;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CategoryMapper {

    @Insert("insert into tb_category(category_name, category_alias, create_user, create_time, update_time) " +
            "values(#{categoryName}, #{categoryAlias}, #{createUser}, now(), now())")
    Integer addCategory(Category category);

    @Select("select * from tb_category")
    List<Category> listCategory();

    @Select("select * from tb_category where id = #{id}")
    Category detailById(Integer id);

    Integer updateCategory(Category category);

    @Delete("delete from  tb_category where id = #{id}")
    Integer deleteById(Integer id);
}
