package com.psl.service;

import com.psl.pojo.Category;
import com.psl.pojo.Result;

public interface CategoryService {
    Result addCategory(Category category);

    Result list();

    Result detailById(Integer id);

    Result updateCategory(Category category);

    Result deleteById(Integer id);
}
