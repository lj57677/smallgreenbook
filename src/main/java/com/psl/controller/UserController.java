package com.psl.controller;

import com.psl.pojo.Result;
import com.psl.pojo.User;
import com.psl.service.UserService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    //用户注册
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username,
                           @Pattern(regexp = "^\\S{5,16}$") String password) {
        return userService.register(username, password);
    }

    //用户登录
    @PostMapping("/login")
    public Result login(@Pattern(regexp = "^\\S{5,16}$") String username,
                        @Pattern(regexp = "^\\S{5,16}$") String password) {
        return userService.login(username, password);
    }

    //获取当前已登录用户的详细信息
    @GetMapping("/userInfo")
    public Result getUserInfo() {
        return userService.getUserInfo();
    }

    //更新登录用户信息
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        return userService.update(user);
    }

    //更新登录用户的头像
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        return userService.updateAvatar(avatarUrl);
    }

    //更新登录用户的密码
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String token) {
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");
        //验证是否缺少参数
        if (!(StringUtils.hasLength(oldPwd)) || !(StringUtils.hasLength(newPwd)) || !(StringUtils.hasLength(rePwd))) {
            return Result.error("缺少参数，请检查~");
        }
        return userService.updatePwd(oldPwd, newPwd, rePwd, token);
    }
}
