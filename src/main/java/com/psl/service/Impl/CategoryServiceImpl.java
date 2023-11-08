package com.psl.service.Impl;

import com.psl.mapper.CategoryMapper;
import com.psl.pojo.Category;
import com.psl.pojo.Result;
import com.psl.service.CategoryService;
import com.psl.utils.ThreadLocalUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public Result addCategory(Category category) {
        //获取ThreadLocal中的token
        Map<String, Object> loginUserMap = ThreadLocalUtil.get();
        //获取登录用户id
        Integer id = (Integer) loginUserMap.get("id");
        //封装Category
        category.setCreateUser(id);
        Integer affectedRows = categoryMapper.addCategory(category);
        if (affectedRows != null && affectedRows > 0) {
            return Result.success();
        }
        return Result.error("新增失败！");
    }

    @Override
    public Result list() {
        List<Category> list = categoryMapper.listCategory();
        return Result.success(list);
    }

    @Override
    public Result detailById(Integer id) {
        Category category = categoryMapper.detailById(id);
        if (category == null){
            return Result.error("查无此类");
        }
        return Result.success(category);
    }

    @Override
    public Result updateCategory(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        Integer affectedRows = categoryMapper.updateCategory(category);
        if (affectedRows != null && affectedRows > 0) {
            return Result.success();
        }
        return Result.error("更新失败！");
    }

    @Override
    public Result deleteById(Integer id) {
        Integer affectedRows = categoryMapper.deleteById(id);
        if (affectedRows != null && affectedRows > 0) {
            return Result.success();
        }
        return Result.error("删除失败！");
    }
}
