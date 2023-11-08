package com.psl.Interceptor;

import com.psl.utils.JwtUtil;
import com.psl.utils.ThreadLocalUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1、尝试获取请求头的token
        String token = request.getHeader("Authorization");
        //2、token判空
        if (token.isEmpty()) {
            response.setStatus(401);
            return false;
        }
        try {
            //3、通过请求头携带的token与redis中的token验证JWT是否过期
            //用请求头的token作为key去redis查询 查得到：token未过期 查不到：token过期
            String redisToken = stringRedisTemplate.opsForValue().get(token);
            if (redisToken == null) {
                //jwt令牌过期
               throw new Exception();
            }
            //3、token不为空，验证是否有效
            //尝试解析
            Map<String, Object> tokenMap = JwtUtil.parseToken(token);
            //将token存至ThreadLocal
            ThreadLocalUtil.set(tokenMap);
        } catch (Exception e) {
            //4、报出异常说明jwt有误或被篡改
            response.setStatus(401);
            return false;
        }
        //5、放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清除ThreadLocal，内存泄露
        ThreadLocalUtil.remove();
    }
}
