package com.gang.swagger.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ProjectName : swagger-demo
 * @作者 : 侯小刚
 * @描述 :
 * @创建日期 : 2020-09-28 15:41
 */
@ApiModel("用户实体类 User")
public class User {
    @ApiModelProperty("id")
    public int id;
    @ApiModelProperty("用户名")
    public String name;
    @ApiModelProperty("密码")
    public String pwd;
}
