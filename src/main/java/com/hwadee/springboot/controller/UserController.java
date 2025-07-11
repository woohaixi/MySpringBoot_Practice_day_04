package com.hwadee.springboot.controller;


import com.hwadee.springboot.entity.UserEntity;
import com.hwadee.springboot.service.UserService;
import com.hwadee.springboot.utils.MD5Utils;
import com.hwadee.springboot.utils.PageUtils;
import com.hwadee.springboot.utils.Query;
import com.hwadee.springboot.utils.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequestMapping("/user")
@Controller
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("")
    public String index() {
        return "user/user-list";
    }

    @GetMapping("/list")
    @ResponseBody
    public ResultMsg list(String name, int offset, int limit) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("offset", offset);
        params.put("limit", limit);
        Query query = new Query(params);
        List<UserEntity> sysUserList = userService.list(query);
        int total = userService.count(query);
        PageUtils pageUtil = new PageUtils(sysUserList, total);
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setStatus(200);
        resultMsg.setMessage("成功");
        resultMsg.setData(pageUtil);
        return resultMsg;
    }

    @PostMapping("/edit")
    @ResponseBody
    public ResultMsg edit(@RequestBody UserEntity user) {
        if(user.getUserId()==0 || StringUtils.isEmpty(user.getName()) || StringUtils.isEmpty(user.getPhone())){
            return ResultMsg.error("请求参数异常");
        }
        UserEntity userDO = userService.get(user.getUserId());
        if (userDO != null) {
            userDO.setName(user.getName());
            userDO.setPhone(user.getPhone());
            userService.update(userDO);
            return ResultMsg.success();
        }
        return ResultMsg.error("操作异常");
    }

    @PostMapping("/add")
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

    @DeleteMapping("/delete")
    @ResponseBody
    public ResultMsg delete(int id) {
        if (userService.remove(id) > 0) {
            return ResultMsg.success();
        }
        return ResultMsg.error("删除失败");
    }
}
