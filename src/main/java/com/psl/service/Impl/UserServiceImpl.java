package com.psl.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.psl.mapper.UserMapper;
import com.psl.pojo.Result;
import com.psl.pojo.User;
import com.psl.service.UserService;
import com.psl.utils.JwtUtil;
import com.psl.utils.Md5Util;
import com.psl.utils.ThreadLocalUtil;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result register(String username, String password) {
        //1、通过用户名查询是否注册
        User user = userMapper.queryByUsername(username);
        if (BeanUtil.isNotEmpty(user)) {
            //查到了该用户名的用户
            return Result.error("该用户名已被注册！");
        }

        //2、添加用户数据到数据库
        //2.1、不能将用户输入的密码通过明文传输，需要加密
        String md5Password = Md5Util.getMD5String(password);
        //2.2、添加到数据库
        LocalDateTime now = LocalDateTime.now();
        userMapper.userRegister(username, md5Password, now);

        //3、返回响应信息
        return Result.success();
    }

    @Override
    public Result login(String username, String password) {
        //1、判断输入的用户名是否存在
        User loginUser = userMapper.queryByUsername(username);
        if (BeanUtil.isEmpty(loginUser)) {
            //不存在该用户名
            return Result.error("用户名不存在！");
        }
        //2、验证用户名与密码是否对应
        if (Md5Util.getMD5String(password).equals(loginUser.getPassword())) {
            //3、将用户信息封装到Map集合claims
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", username);
            //4、生成jwt令牌
            String token = JwtUtil.genToken(claims);
            //5、将token存入redis
            stringRedisTemplate.opsForValue().set(token, token, 3, TimeUnit.HOURS);
            //6、返回响应信息与jwt令牌
            return Result.success(token);
        }
        return Result.error("您的密码有误！");
    }

    @Override
    public Result getUserInfo() {
        Map<String, Object> loginUserMap = ThreadLocalUtil.get();
        //3、取出token中的username
        String username = (String) loginUserMap.get("username");
        //4、通过用户名查用户
        User user = userMapper.queryByUsername(username);
        return Result.success(user);
    }

    @Override
    public Result update(User user) {
        //设置更新时间
        user.setUpdateTime(LocalDateTime.now());
        //更新
        Integer affectedRows = userMapper.update(user);
        if (affectedRows != null && affectedRows > 0) {
            return Result.success();
        }
        return Result.error("更新失败！");

    }

    @Override
    public Result updateAvatar(String avatarUrl) {
        //获取ThreadLocal中的userMap
        Map<String, Object> loginUserMap = ThreadLocalUtil.get();
        //取出id
        Integer id = (Integer) loginUserMap.get("id");
        Integer affectedRows = userMapper.updateAvatar(avatarUrl, id);
        if (affectedRows != null && affectedRows > 0) {
            return Result.success();
        }
        return Result.error("更新失败！");
    }

    @Override
    public Result updatePwd(String oldPwd, String newPwd, String rePwd ,String token) {
        //1、判断旧密码是否正确
        //1.1、获取当前登录用户的token
        Map<String, Object> loginUserMap = ThreadLocalUtil.get();
        String username = (String) loginUserMap.get("username");
        //1.2、通过用户名查用户
        User user = userMapper.queryByUsername(username);
        //1.3判断密码是否正确
        if (!(Md5Util.getMD5String(oldPwd).equals(user.getPassword()))) {
            return Result.error("原密码有误！密码修改错误");
        }
        //2、判断新密码是否符合5~16位的非空字符
        if (!(newPwd.matches("^\\S{5,16}$"))) {
            return Result.error("新密码不符合要求！（5~16位的非空字符）");
        }
        //3、验证新密码的两次输入是否相同
        if (!(newPwd.equals(rePwd))){
            return Result.error("您输入的两次新密码不同，请检查~");
        }
        //4、修改用户密码
        //通过Md5加密新密码
        String md5NewPwd = Md5Util.getMD5String(newPwd);
        Integer affectedRows = userMapper.updatePwd(md5NewPwd, username);
        if (!(affectedRows != null && affectedRows > 0)) {
            return Result.error("更新失败！");
        }
        //5、修改密码后需要使旧token失效，删除token
        stringRedisTemplate.delete(token);
        return Result.success();

    }
}
