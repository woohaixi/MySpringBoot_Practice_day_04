package com.hwadee.springboot.controller;



import com.hwadee.springboot.entity.MenuEntity;
import com.hwadee.springboot.service.MenuService;
import com.hwadee.springboot.utils.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequestMapping("/menu")
@Controller
public class MenuController {
    @Autowired
    MenuService menuService;

    @PostMapping("/list")
    @ResponseBody
    public ResultMsg list(@RequestBody MenuEntity menu) {
        Map<String, Object> params = new HashMap<>();
        params.put("cname", menu.getCname());
        params.put("ename", menu.getEname());
        List<MenuEntity> list = menuService.list(params);
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setStatus(200);
        resultMsg.setMessage("成功");
        resultMsg.setData(list);
        return resultMsg;
    }

    @PostMapping("/edit")
    @ResponseBody
    public ResultMsg edit(@RequestBody MenuEntity menu) {
        if(menu.getMenuId()==0 || StringUtils.isEmpty(menu.getCname()) || StringUtils.isEmpty(menu.getEname())){
            return ResultMsg.error("请求参数异常");
        }
        MenuEntity menuDO = menuService.get(menu.getMenuId());
        if (menuDO != null) {
            menuDO.setCname(menu.getCname());
            menuDO.setEname(menu.getEname());
            menuDO.setPath(menu.getPath());
            menuDO.setComponent(menu.getComponent());
            menuDO.setIcon(menu.getIcon());
            menuDO.setPid(menu.getPid());
            menuService.update(menuDO);
            return ResultMsg.success();
        }
        return ResultMsg.error("操作异常");
    }

    @PostMapping("/add")
    @ResponseBody
    public ResultMsg add(@RequestBody MenuEntity menu) {
        if(StringUtils.isEmpty(menu.getCname()) || StringUtils.isEmpty(menu.getEname())){
            return ResultMsg.error("请求参数异常");
        }

        MenuEntity menuDO = new MenuEntity();
        menuDO.setCname(menu.getCname());
        menuDO.setEname(menu.getEname());
        menuDO.setPath(menu.getPath());
        menuDO.setComponent(menu.getComponent());
        menuDO.setIcon(menu.getIcon());
        menuDO.setPid(menu.getPid());
        try {
            menuService.save(menuDO);
            return ResultMsg.success();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultMsg.error("操作异常");
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public ResultMsg delete(int id) {
        if (menuService.remove(id) > 0) {
            return ResultMsg.success();
        }
        return ResultMsg.error("删除失败");
    }
}
