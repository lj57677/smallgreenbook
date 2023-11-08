package com.psl.controller;

import com.psl.pojo.Category;
import com.psl.pojo.Result;
import com.psl.service.CategoryService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    //新增文章分类
    @PostMapping
    public Result addCategory(@RequestBody @Validated(Category.Add.class) Category category) {
        return categoryService.addCategory(category);
    }

    //获取当前登录用户创建的所有文章分类
    @GetMapping
    public Result list() {
        return categoryService.list();
    }

    //根据ID获取文章分类详情
    @GetMapping("/detail")
    public Result detailByID(Integer id) {
        return categoryService.detailById(id);
    }

    //更新文章分类
    @PutMapping
    public Result updateCategory(@RequestBody @Validated(Category.Update.class) Category category) {
        return categoryService.updateCategory(category);
    }

    //根据ID删除文章分类
    @DeleteMapping
    public Result delete(Integer id) {
        return categoryService.deleteById(id);
    }
}
