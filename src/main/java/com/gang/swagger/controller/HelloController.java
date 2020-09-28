package com.gang.swagger.controller;

import com.gang.swagger.pojo.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

/**
 * @ProjectName : swagger-demo
 * @作者 : 侯小刚
 * @描述 :
 * @创建日期 : 2020-09-28 11:08
 */
@RestController
public class HelloController {

    @GetMapping(value="/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping(value="/user")
    @ApiOperation("获取用户")
    public User getUser(@ApiParam("用户id") @RequestParam(value = "id") int id){
        return new User();
    }

}
