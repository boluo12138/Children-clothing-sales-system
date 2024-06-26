package com.wzu.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzu.common.vo.Result;
import com.wzu.sys.entity.User;
import com.wzu.sys.service.IUserService;
import com.wzu.sys.service.MailService;
import com.wzu.utils.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 功能： 用户前端控制器
 * 作者：菠萝
 * 日期：2024/3/17
 */
@Data
@Slf4j
@Api(tags = {"用户接口列表"})
@RestController
@RequestMapping("/user")
// @CrossOrigin   跨域处理
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/all")
    public Result<List<User>> getAllUser() {
        List<User> list = userService.list();
        return Result.success(list, "查询成功");
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody User user) {
        System.out.println("用户登陆————————"+user.toString());
        Map<String, Object> data = userService.login(user);
        if (data != null) {
            return Result.success(data);
        }
        return Result.fail(20002, "用户名或密码错误");
    }

    @GetMapping("/info")
    public Result<Map<String, Object>> getUserInfo(@RequestParam("token") String token) {
        // 根据token获取用户信息，redis
        Map<String, Object> data = userService.getUserInfo(token);
        if (data != null) {
            return Result.success(data);
        }
        return Result.fail(20003, "登录信息无效，请重新登录");
    }

    @PostMapping("/logout")
    public Result<?> logout(@RequestHeader("X-Token") String token) {
        userService.logout(token);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<Map<String, Object>> getUserList(@RequestParam(value = "username", required = false) String username,
                                                   @RequestParam(value = "phone", required = false) String phone,
                                                   @RequestParam(value = "pageNo") Long pageNo,
                                                   @RequestParam(value = "pageSize") Long pageSize) {

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasLength(username), User::getUsername, username);
        wrapper.like(StringUtils.hasLength(phone), User::getPhone, phone);
        wrapper.orderByDesc(User::getId);
        Page<User> page = new Page<>(pageNo, pageSize);
        userService.page(page, wrapper);
        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());
        return Result.success(data);

    }

    @PostMapping
    public Result<?> addUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setName(user.getUsername());
        userService.addUser(user);
        return Result.success("新增用户成功");
    }

    @PutMapping
    public Result<?> updateUser(@RequestBody User user) {
        log.info(user.toString());
        user.setPassword(null);
        userService.updateUser(user);
        return Result.success(userService.getUserById(user.getId()),"修改用户成功");
    }

    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable("id") Integer id) {
        User user = userService.getUserById(id);
        return Result.success(user);
    }

    @DeleteMapping("/{id}")
    public Result<User> deleteUserById(@PathVariable("id") Integer id) {
        userService.deleteUserById(id);
        return Result.success("删除用户成功");
    }


    @Autowired
    private MailService mailService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //发送邮箱验证码
    @PostMapping("/sendEmail")
    public Result sendEmail(@RequestBody User users) {
        String email = users.getEmail();
        //key 邮箱号  value 验证码
        String code = redisTemplate.opsForValue().get(email);
//        System.out.println("1.code\n"+code);
        //从redis获取验证码，如果获取获取到，返回ok
        if (!StringUtils.isEmpty(code)) {
            return Result.success(code);
        }
        //如果从redis获取不到，生成新的6位验证码
        code = RandomUtil.getSixBitRandom();
        //调用service方法，通过邮箱服务进行发送
        boolean isSend = mailService.sendMail(email, code);
        //生成验证码放到redis里面，设置有效时间为5分钟
        if (isSend) {
            redisTemplate.opsForValue().set(email, code, 5, TimeUnit.MINUTES);
            return Result.success(code);
        } else {
            return Result.fail("发送失败");
        }
    }


//    @Autowired
//    UserMapper mapper;
    //注册
    @PostMapping("/register")
    public Result<User> register(@RequestBody User user) {
        return userService.register(user);
    }


    @PostMapping("/password")
    public Result<Map<String, Object>> updatePassword(@RequestParam("token") String token,
                                                      @RequestParam("username") String username,
                                                      @RequestParam("oldPassword") String oldPassword,
                                                      @RequestParam("newPassword") String newPassword) {
        System.out.println("update password: " + token + " " + username + " " + oldPassword + " " + newPassword);
        Map<String, Object> data = userService.updatePassword(token, username, oldPassword, newPassword);
        if(data != null){
            userService.updateById((User) data.get("loginUser"));
            return Result.success(data);
        }
        return Result.fail(20004,"更改密码失败");
    }
    @PostMapping("/resetPassword")
    public Result resetPassword(@RequestBody User user) {
        Result result=null;
        if (user.getUsername()!=null&& user.getEmail()!=null){
            //第一步，传来的是用户名和邮件，其他为空，则生成验证码并发送邮件
            User user1 = userService.checkUserAndEmail(user);
            if (user1!=null){
                result = this.sendEmail(user1);
            }
        }
        if (user.getCode()!=null && user.getEmail()!=null) {
            //第二步，传来code和username，需要验证数据库中的code是否正确
            result = userService.checkCode(user.getCode(), user.getEmail());
        }
        if (user.getUsername()!=null && user.getPassword()!=null) {
            //最后，用户名和密码同时传过来，开始重置密码。
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("username", user.getUsername());
            userService.update(user,wrapper);
            result = Result.success("密码重置成功");
        }
        return result;
    }
}
