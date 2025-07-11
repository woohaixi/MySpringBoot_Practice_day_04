package com.hwadee.springboot.controller;

import com.alibaba.fastjson2.JSONObject;
import com.hwadee.springboot.entity.MenuEntity;
import com.hwadee.springboot.entity.UserEntity;
import com.hwadee.springboot.service.MenuService;
import com.hwadee.springboot.service.UserService;
import com.hwadee.springboot.utils.MD5Utils;
import com.hwadee.springboot.utils.ResultMsg;
import com.hwadee.springboot.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    UserService userService;
    @Autowired
    MenuService menuService;

    /**
     * 登录接口
     */
    @ResponseBody
    @PostMapping("/login/accountLogin")
    public ResultMsg accountLogin(@RequestBody UserEntity user) {
        Map<String, Object> params = new HashMap<>();
        params.put("account", user.getAccount());
        params.put("password", MD5Utils.encryptMD5AndSalt(user.getPassword(), MD5Utils.DEFAULT_SALT));
        UserEntity u = userService.getUserByLoginInfo(params);
        ResultMsg resultMsg = new ResultMsg();
        JSONObject jsonObject = new JSONObject();
        if (u != null) {
            //登录成功，查询菜单信息
            List<MenuEntity> menus = menuService.listByUserId(u.getUserId());
            //生成token
            String token = TokenUtil.sign(u);
            jsonObject.put("token", token);
            jsonObject.put("user", u);
            jsonObject.put("menus", menus);

            resultMsg.setStatus(200);
            resultMsg.setMessage("登录成功");
            resultMsg.setData(jsonObject);
        } else {
            resultMsg.setStatus(500);
            resultMsg.setMessage("账号或密码错误");
        }
        return resultMsg;
    }

    @PostMapping("/login/register")
    @ResponseBody
    public ResultMsg add(@RequestBody UserEntity user) {
        if(StringUtils.isEmpty(user.getName()) || StringUtils.isEmpty(user.getPhone()) || StringUtils.isEmpty(user.getAccount()) || StringUtils.isEmpty(user.getPassword())){
            return ResultMsg.error("请求参数异常");
        }
        //验证账号名称是否存在
        UserEntity userDO = userService.getUserByAccountName(user.getAccount());
        if(userDO!=null){
            return ResultMsg.error("账号名已经存在，请修改账户名");
        }

        userDO = new UserEntity();
        userDO.setName(user.getName());
        userDO.setPhone(user.getPhone());
        userDO.setAccount(user.getAccount());
        userDO.setPassword(MD5Utils.encryptMD5AndSalt(user.getPassword(),MD5Utils.DEFAULT_SALT));
        userDO.setCreateDate(new Date());
        try {
            userService.save(userDO);
            return ResultMsg.success();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultMsg.error("操作异常");
    }

    /**
     * 退出接口
     */
    @ResponseBody
    @GetMapping("/login/logout")
    public ResultMsg logout(@RequestHeader(required = false) String token) {
        // 清除用户登录状态,比如在redis中清除用户登录状态
        //...

        //模拟退出
        if (!StringUtils.isEmpty(token)) {
            TokenUtil.tokenMap.remove(token);
        }
        return ResultMsg.success();
    }
}
