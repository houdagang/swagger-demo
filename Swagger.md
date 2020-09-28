# Swagger

学习目标：

- 了解Swagger的作用和概念
- 了解前后端分离
- 在SpringBoot中集成Swagger

## Swagger简介

**前后端分离**

Vue + SpringBoot

后端时代：前端只用管理静态页面；html ==>后端。模板引擎 JSP =>后端是主力



前后端分离时代：

- 后端：后端控制层，服务层，数据访问层
- 前端：前端控制层，视图层
  - 伪造后端数据，json。已经存在了，不需要后端，前端工程依旧能够跑起来
- 前后端如何交互？===>API
- 前后端相对独立，松耦合
- 前后端甚至可以部署在不同的服务器上；



产生一个问题：

- 前后端集成联调，前端人员和后端人员无法做到，“及时协商，尽早解决”，最终导致问题集中爆发；

解决方案：

- 首先制定schema【计划】，实时更新最新API，降低集成的风险；
- 早些年：制定word计划文档；
- 前后端分离：
  - 前端测试后端接口：早期，postman
  - 后端提供接口，需要实时更新最新的消息及改动！



## Swagger

- 号称世界上最流行的Api框架
- RestFul Api 文档在线自动生成工具 =>Api文档与API定义同步更新
- 直接运行，可以在线测试API接口；
- 支持多种语言：java、php



官网：https://swagger.io/



在项目中使用Swagger需要springbox；

- swagger2
- ui



## SpringBoot继承Swagger

1.新建一个SpringBoot = web项目

2.导入相关依赖

```
<!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger2 -->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.9.2</version>
</dependency>

<!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger-ui -->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.9.2</version>
</dependency>
```

3.写一个基础的工程

```
@RestController
public class HelloController {
    @RequestMapping(value="/hello")
    public String hello() {
        return "hello";
    }
}
```

4.配置Swagger ==> Configuration

```
@Configuration
@EnableSwagger2 //开启Swagger2
public class SwaggerConfig {
}
```

5.测试运行

浏览器地址：localhost:8080/swagger-ui.html

![image-20200928133126842](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200928133126842.png)

**注意： 上面的配置是基于2.x的版本**



## 3.x版本的Swagger配置

3.x版本基于上面，还是改动挺大的。

1.首先，引入的jar包的变动

```
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
```

2.配置类的注解也有所变化

```
@Configuration
@EnableOpenApi
public class SwaggerConfig {
	//配置了Swagger的Dockey的bean实例
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo());
    }

    //配置Swagger信息 = apiInfo
    private ApiInfo apiInfo() {
        //作者信息
        Contact contact = new Contact("小刚","https://www.baidu.com","952551626@qq.com");
        return new ApiInfo(
                "小刚的SwaggerAPI文档",
                "村里来的",
                "v1.0",
                "https://www.baidu.com",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList()
        );
    }
}
```

3.访问的地址的变化

```
http://localhost:8080/swagger-ui/index.html
```



## Swagger配置扫描接口

Docket.select()配置，默认扫描全部

- 扫描单个包下

```
@Bean
public Docket docket() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.gang.swagger.controller"))
        .build();
}
```

- 指定所有的controller都实现一个接口

```
@Bean
public Docket createRestApi() {
    return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
            .paths(PathSelectors.any())
            .build();
}
```

| 解释             | 方法                                           |
| ---------------- | ---------------------------------------------- |
| 扫描指定的包     | RequestHandlerSelectors.basePackage()          |
| 扫描全部         | RequestHandlerSelectors.any()                  |
| 扫描类上的注解   | RequestHandlerSelectors.withClassAnnotation()  |
| 扫描方法上的注解 | RequestHandlerSelectors.withMethodAnnotation() |

- 过滤路径 .path(PathSelectors.any())

| 解释         | 方法                  |
| ------------ | --------------------- |
| 根据路径过滤 | PathSelectors.ant()   |
| 不过滤       | PathSelectors.any()   |
| 全过滤       | PathSelectors.none()  |
| 正则过滤     | PathSelectors.regex() |

- 如何动态的启动或者关闭Swagger（生产环境，正式环境）

  ```
  @Bean
  public Docket docket(Environment environment) {
  	//获取对应的环境，判断，传入一个布尔值
      Profiles profiles = Profiles.of("dev","test");
      boolean flag = environment.acceptsProfiles(profiles);
      return new Docket(DocumentationType.SWAGGER_2)
          .apiInfo(apiInfo())
          //是否启用Swagger,false则浏览器中不能访问
          .enable(flag)
          .select()
          //RequestHandlerSelectors,配置要扫描的接口的方式
          //.apis(RequestHandlerSelectors.basePackage("com.gang.swagger.controller"))
          .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
          .build();
  }
  ```

- 配置分组

  .groupName("分组名字")

  >  配置多个分组，即返回多个Docket实例

  ```
  @Bean
  public Docket docket1() {
      return new Docket(DocumentationType.SWAGGER_2).groupName("分组1");
  }
  
  @Bean
  public Docket docket2() {
  	return new Docket(DocumentationType.SWAGGER_2).groupName("分组2");
  }
  
  @Bean
  public Docket docket3() {
  	return new Docket(DocumentationType.SWAGGER_2).groupName("分组3");
  }
  ```



## Swagger注释

swagger中modul扫描实体类：只要我们的接口中，返回值中存在实体类，他就会被扫描到Swagger中

- 加在类上面的注释   @ApiModel()
- 加在属性上面的注释     @ApiModelProperty()

```
@ApiModel("用户实体类 User")
public class User {
    @ApiModelProperty("id")
    public int id;
    @ApiModelProperty("用户名")
    public String name;
    @ApiModelProperty("密码")
    public String pwd;
}
```

![image-20200928155355851](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200928155355851.png)

- 方法注释 @ApiOperation(）

- 参数注释@ApiParam(）

  ```
  @PostMapping(value="/user")
  @ApiOperation("获取用户")
  public User getUser(@ApiParam("用户id") @RequestParam(value = "id") int id){
      return new User();
  }
  ```

  ![image-20200928161649344](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200928161649344.png)



