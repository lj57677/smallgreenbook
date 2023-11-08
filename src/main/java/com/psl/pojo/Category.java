package com.psl.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    //groups 添加该项为某个组的校验项
    @NotNull(groups = Update.class)
    private Integer id;
    @NotEmpty(message = "文章分类名不能为空")
    private String categoryName;
    private String categoryAlias;
    private Integer createUser;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss") //在对象被序列化成JSON时，createTime字段的值将按照指定的格式显示。
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime updateTime;

    //1、定义分组，每个接口代表一个分组；没有分组时，默认是Default组
    //2、通过group属性对校验项进行分组，若不指定分组默认属于Default分组
    //3、在Controller对请求参数校验时，给@Validated注解的value属性赋值指定分组

    //子组继承父组，子组有父组的所有校验项
    public interface Update extends Default {

    }

    public interface Add extends Default{

    }
}
