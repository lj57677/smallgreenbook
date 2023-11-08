package com.psl.service;

import com.psl.pojo.Result;
import com.psl.pojo.User;

public interface UserService {
    Result register(String username, String password);

    Result login(String username, String password);

    Result getUserInfo();

    Result update(User user);

    Result updateAvatar(String avatarUrl);

    Result updatePwd(String oldPwd, String newPwd, String rePwd, String token);
}
