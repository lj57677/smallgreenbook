package com.psl.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.psl.anno.State;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @NotNull(groups = Update.class)
    private Integer id;
    @NotEmpty(groups = Add.class)
    @Pattern(groups = Add.class, regexp = "^\\S{1,20}$")
    private String title;
    @NotEmpty(groups = Add.class)
    private String content; //文章正文
    @NotEmpty(groups = Add.class)
    @URL
    private String coverImg; //文章封面
    @State(groups = Add.class)
    private Integer state; //文章发布状态 1：已发布 0：草稿(只能为1或0)
    @NotNull(groups = Add.class)
    private Integer categoryId; //文章分类id
    private Integer createUser;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss") //在对象被序列化成JSON时，createTime字段的值将按照指定的格式显示。
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime updateTime;

    public interface Add extends Default {

    }

    public interface Update extends Default {

    }
}
