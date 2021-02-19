package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.service.LoginService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Reference
    private LoginService loginService;
    @RequestMapping("/showName")
    public Result showName(){
        loginService.showName();
        return new Result();
    }
}
