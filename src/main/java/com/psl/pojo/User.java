package com.psl.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @NotNull //不为空
    private Integer id;
    @Pattern(regexp = "^\\S{5,16}$")
    private String username;
    @JsonIgnore //另该属性在转为json字符串时忽略
    private String password;
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname;
    @Email(message = "邮箱格式不正确") //需符合email格式
    private String email;
    private String userPic;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
