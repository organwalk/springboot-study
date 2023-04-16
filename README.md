# SpringBoot学习笔记

基于SpringBoot 3.X 版本学习，但仍然有2.x版本的操作

## Web入门

- Spring Boot将传统Web开发的mvc、json、tomcat等框架整合,提供了spring-boot-starter-web组件,简化了Web应用配置。
- 创建SpringBoot项目勾选Spring Web选项后,会自动将spring-boot-starter-web组件加入到项目中。
- spring-boot-starter-web启动器主要包括web、webmvc、json、tomcat等基础依赖组件，作用是提供Web开发场景所需的所有底层依赖。
- webmvc为Web开发的基础框架,json为JSON数据解析组件,tomcat为自带的容器依赖。

```xml
<dependency>
<groupId>org. springframework. boot</groupId>
<artifactId>spring-boot-starter-web</artifactId></dependency>
</dependency>
```

### 控制器

- SpringBoot提供了`@Conroller`和`@RestController`两种注解来标识此类负责接收和处理HTTP请求。
- （为遵循前后端分离，不推荐）如果请求的是页面和数据，使用`@Conroller`注解即可；如果只是请求数据，则可以使用`@RestController`注解。

#### ＠Controller的用法

示例中返回了hello页面和name的数据，在前端页面中可以通过＄｛name｝参数获取后台返回的数据并显示。
＠Controller通常与Thymeleaf模板引擎结合使用。

```java
 @Controller
public class HelloController{ 
    @RequestMapping(©v"/helLo") 
    public String index(ModelMap map){
		map.addAttribute( attributeName: "name", attributeValue: "zhangsan"); 
        return "hello";
	} 
} 
```

#### ＠RestController的用法

- 默认情况下，＠RestController注解会将返回的对象数据转换为JSON格式。

```java
@RestController
pubLic class HelloController { 
    @RequestMapping(⑥v"/user") 
    public User getUser(){ 
        User user=new User(); 
        user.setUsername("zhangsan"); 
        user.setPassword("123"); 
        return user;
	} 
} 
```

### 路由映射

- ＠RequestMapping注解主要负责URL的路由映射。它可以添加在Controller 类或者具体的方法上。

- 如果添加在Controller类上，则这个Controller中的所有路由映射都将会加上此映射规则，如果添加在方法上，则只对当前方法生效。

- ＠RequestMapping注解包含很多属性参数来定义HTTP的请求映射规则。常用的属性参数如下：

  - value：请求URL的路径，支持URL模板、正则表达式

  - method：HTTP请求方法

  - consumes：请求的媒体类型（Content-Type）， 如application／json produces：响应的媒体类型

  - params，headers：请求的参数及请求头的值 

- ＠RequestMapping的value属性用于匹配URL映射，value支持简单表达式 @RequestMapping("/user")
- ＠RequestMapping支持使用通配符匹配URL，用于统一映射某些URL规则类似的请求：＠RequestMapping（＂／getJson／＊.json＂），当在浏览器中请求 ／getJson／a.json或者／getJson／b.json时都会匹配到后台的Json方法
-  ＠RequestMapping的通配符匹配非常简单实用，支持 “＊”   “？”   "**"等通配符
- 符号 "*"匹配任意字符，符号“＊＊”匹配任意路径，符号“？”匹配单个字符。
- 有通配符的优先级低于没有通配符的，比如／user／add.json比／user／＊.json优先匹配。
- 有“＊＊”通配符的优先级低于有“＊”通配符的。

### Method

- HTTP请求Method有GET、POST、PUT、DELETE等方式。HTTP支持的全部 Method
- ＠RequestMapping注解提供了method参数指定请求的Method类型，包括 RequestMethod.GET、RequestMethod.POST、RequestMethod.DELETE、 ReqquestMethod.PUT等值，分别对应HTTP请求的Method

```java
@RequestMapping(value = ⑧v"/getData",method = RequestMethod.GET) 
public String getData(){
	return "hello";
}
```

- Method匹配也可以使用＠GetMapping、＠PostMapping等注解代替。 

### 实例代码

```java
import com.example.springbootstudy.entity.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class ParamsController {
    
    //发送GET请求
    @RequestMapping(value = "/getTest1",method = RequestMethod.GET)
    public String getTest1(){
        return "GET请求";
    }
    //发送含参的GET请求
    @RequestMapping(value = "/getTest2",method = RequestMethod.GET)
    public String getTest1(String nickname,String phone){
        System.out.println("nickname"+nickname);
        System.out.println("phone"+phone);
        return "GET请求"+nickname+phone;
    }

    //适用于前后端参数名称不统一
    @RequestMapping(value = "/getTest3",method = RequestMethod.GET)
    public String getTest1(@RequestParam(value = "nickname",required = false) String name){
        //required意味着该参数传不传递都可以
        System.out.println("nickname"+name);
        return "GET请求"+name;
    }

    //使用apipost发送请求
    @RequestMapping(value = "/postTest1",method = RequestMethod.POST)
    public String postTest1(){
        return "POST请求";
    }
    //使用apipost发送含参请求
    @RequestMapping(value = "/postTest2",method = RequestMethod.POST)
    public String postTest2(String username,String password){
        System.out.println("username:"+username);
        System.out.println("password:"+password);
        return "POST";
    }
    //以实体类存储请求参数
    @RequestMapping(value = "/postTest3",method = RequestMethod.POST)
    public String postTest3(User user){
        System.out.println(user);
        return "POST";
    }
    //使用apipost发送json格式数据
    @RequestMapping(value = "/postTest4",method = RequestMethod.POST)
    public String postTest4(@RequestBody User user){
        System.out.println(user);
        return "POST";
    }
    //通配符请求
    @GetMapping("/test/**")
    public String test(){
        return "通配符请求";
    }
}
```

<div align="center">ParamsController.java</div>

```java
public class User {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
```

<div align="center">User.java</div>

## Web开发进阶

### 静态资源访问

- ​    使用IDEA创建SpringBoot项目，会默认创建出classpath:/static/目录，静态资源一般放在这个目录下即可（前后端分离不推荐）
- ​    如果默认的静态资源过滤策略不能满足开发需求， 也可以自定义静态资源过滤策略。
- ​    在application.properties中直接定义过滤规则和静态资源位置：

```properties
springmvc.static-path-pattern=/static/**
springweb.resources.static-Locations=classpath:/static/
```

- ​     过滤规则为/static/**，静态资源位置为classpath:/static/

### 文件上传原理

- 表单的enctype属性规定在发送到服务器之前应该如何对表单数据进行编码
- 当表单的enctype="aapplication/x-www-form-urlencoded （默认） 时，form表单中的数据格式为： key=value&key=value
- 当表单的enctype="multipart/form-data"时，其传输数据形式如下：

![传输数据形式](C:\Users\haruki\AppData\Roaming\Typora\typora-user-images\image-20230320192746123.png)

### SpringBoot实现文件上传功能

- SpringBoot工程嵌入的tomcat限制了请求的文件大小，每个文件的配置最大为1Mb，单次请求的文件的总数不能大于10Mb。
- 要更改这个默认值需要在配置文件 （女application.properties） 中加入两个配置：

```properties
spring.servlet.mutipart.max-fie-size=1OMB
spring.servlet.mutipart.max-request-size=1OMB
```

当表单的enctype="multipart/form-data"时可以使用MultipartFile获取上传的文件数据，再通过transferTo方法将其写入到磁盘中

```java
@RestContro11e
public class FileController{
	private static final String UPLOADED_FOLDER= System.getProperty("user.dir")+"/upload/”;
    @PostMapping("/up")
    pubic String upload(string nickname,MutipartFile f) throws IOException {
    	System.out.printin("文件大小："+f.getsize)；
        System.outprintin(f.getcontentType())；
        System.out.printin(f.getoriginalFilename())；
        System.out.printin(system.getProperty("user.dir"));
        saveFile(f);
        return上传成功”;
    }
    public void saveFile(MultipartFile f) throvs IOEXception{}
    	File upDir =new File(UPLOADED_FOLDER)
        if(!upDir.exists)){
        	upDir.mkdir;
        }
        File file = new File(UPLOADED_FOLDER+f.getoriginalFilename());
        f.transferTo(file);
    }
}
```

以下为一个可运行示例代码：

```java
@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public String up(String nickname, MultipartFile photo, HttpServletRequest request) throws IOException {
        System.out.println(nickname);
        //获取图片的原始名称
        System.out.println(photo.getOriginalFilename());
        //取文件类型
        System.out.println(photo.getContentType());

        String path = request.getServletContext().getRealPath("/upload/");
        System.out.println(path);
        saveFile(photo,path);
        return "上传成功";
    }

    public void saveFile(MultipartFile photo,String path) throws IOException {
        //判断存储的目录是否存在，如果不存在则创建
        File dir = new File(path);
        if (!dir.exists()){
            //创建目录
            dir.mkdir();
        }

        File file = new File(path+photo.getOriginalFilename());
        photo.transferTo(file);
    }
}
```

<div align="center">FileUploadController.java</div>

`@RestController`是Spring MVC中的一个注解，它用于标识一个类是RESTful Web服务的控制器。当一个类被标记为`@RestController`时，Spring会自动将其所有公共方法的返回值序列化为JSON或XML，并将其发送给客户端。

在上述代码中，`@RestController`注解表明这是一个RESTful Web服务的控制器。`@PostMapping("/upload")`注解表示这个方法处理POST类型的请求，并且请求的URL是"/upload"。

该方法接收三个参数：`nickname`、`photo`和`request`。其中，`nickname`是一个普通的字符串参数，`photo`是一个`MultipartFile`类型的文件上传参数，`request`是一个`HttpServletRequest`类型的对象，用于获取当前请求的上下文信息。

该方法首先打印出`nickname`、`photo`的原始文件名和文件类型，然后将上传的文件保存到服务器的指定目录中。具体地，它使用`request.getServletContext().getRealPath("/upload/")`方法获取服务器上的上传目录，然后调用`saveFile`方法将文件保存到该目录中。

`saveFile`方法首先判断上传目录是否存在，如果不存在则创建该目录。然后创建一个新的`File`对象，将上传的文件保存到该文件中。

```properties
spring.web.resources.static-locations=/upload/
```

这是Spring Boot中的一个配置参数，它指定了Web应用程序中静态资源文件的访问路径。具体地说，该参数指定了静态资源文件在服务器上的存储路径。在本例中，静态资源文件存储在`/upload/`目录下。当客户端请求这些静态资源文件时，Spring Boot会自动从该目录下查找相应的文件并返回给客户端。需要注意的是，如果该参数指定了多个路径，Spring Boot会按照指定的顺序查找静态资源文件。例如，如果将该参数设置为`spring.web.resources.static-locations=/static/,/public/`，那么当客户端请求静态资源文件时，Spring Boot会先在`/static/`目录下查找文件，如果找不到再在`/public/`目录下查找文件。

### 拦截器                                               

拦截器在Web系统中非常常见，对于某些全局统一的操作，我们可以把它提取到拦截器中实现。总结起来，拦截器大致有以下几种使用场景：

- 权限检查：如登录检测，进入处理程序检测是否登录，如果没有，则直接返回登录页面。
- 性能监控：有时系统在某段时间莫名其妙很慢  可以通过拦截器在进入处理程序之前记录开始时间，在处理完后记录结束时间，从而得到该请求的处理时间
- 通用行为：读取cookie得到用户信息并将用户对象放入请求，从而方便后续流程使用，还有提取Locale、Theme信息等，只要是多个处理程序都需要的，即可使用拦截器实现

- SpringBoot定义了Handlerlnterceptor接口来实现自定义拦截器的功能
- Handlerlnterceptor接口定义了preHandle、 postHandle、 afterCompletior三种方法，通过重与这三种方法实现请求前、 请求后等操作

- addPathPatterns方法定义拦截的地址
- excludePathPatterns定义排除某些地址不被拦截
- 添加的一个拦截器没有addPathPattern任何一个url则默认拦截所有请求
- 如果没有excludePathPatterns任何一个请求，则默认不放过任何一个请求

以下是一个可运行的示例代码：

```java
public class Logininterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handle){
        System.out.println("LoginInterceptor");
        return true;
    }
}
```

<div align="center">Logininterceptor.java</div>

上述代码定义了一个名为`Logininterceptor`的拦截器类，它实现了`HandlerInterceptor`接口。其中，`preHandle`方法是该接口的一个方法，用于在请求处理之前执行一些操作，例如权限验证、日志记录等。在本例中，`preHandle`方法仅仅是打印一条日志，并返回`true`表示请求可以继续进行。如果返回`false`，则表示请求被拦截，后续的操作将不会执行。

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new Logininterceptor()).addPathPatterns("/user/**");
    }
}
```

<div align="center">WebConfig.java</div>

这一段代码定义了一个名为`WebConfig`的配置类，它实现了`WebMvcConfigurer`接口。其中，`addInterceptors`方法是该接口的一个方法，用于向Spring MVC框架注册拦截器。在本例中，`addInterceptors`方法调用`registry.addInterceptor`方法，将`Logininterceptor`拦截器注册到Spring MVC框架中，并指定拦截的请求路径为`/user/**`，表示只有以`/user`开头的请求会被该拦截器拦截。

## 构建RESTful服务

### RESTful介绍

- RESTful是目前流行的互联网软件服务架构设计风格。
- REST（Representational State Transfer，表述性状态转移） 一词是由Roy ThomasFielding在2000年的博士论文中提出的，它定义了互联网软件服务的架构原则，如果一个架构符合REST原则，则称之为RESTful架构。
- REST并不是一个标准，它更像一组客户端和服务端交互时的架构理念和设计原则，基于这种架构理念和设计原则的WebAPI更加简洁，更有层次

### RESTful的特点

- 每一个URI代表一种资源
- 客户端使用GET、POST、FPUT、DELETE四种表示操作方式的动词对服务端资源进行操作：GET用于获取资源，POST用于新建资源 （也可以用于更新资源）PUT用于更新资源，DELETE用于删除资源。
- 通过操作资源的表现形式来实现服务端请求操作
- 资源的表现形式是JSON或者HTML
- 客户端与服务端之间的交互在请求之间是无状态的，从客户端到服务端的每个请求都包含必需的信息。

### RESTfulAPI

符合RESTful规范的WebAPI需要具备如下两个关键特性：

- 安全性：安全的方法被期望不会产生任何副作用，当我们使用GET操作获取资源时，不会引起资源本身的改变，也不会引起服务器状态的改变。
- 幕等性：等的方法保证了重复进行一个请求和一次请求的效果相同 （并不是指响应总是相同的，而是指服务器上资源的状态从第一次请求后就不再改变了），在数学上幕等性是指N次变换和一次变换相同。

### HTTP状态码

- HTTP状态码就是服务向用户返回的状态码和提示信息，客户端的每一次请求服务都必须给出回应，回应包括HTTP状态码和数据两部分
- HTTP定义了40个标准状态码，可用于传达客户端请求的结果。 状态码分为以下5个类别：
- 1XX:信息，通信传输协议级信息
- 2x：成功，表示客户端的请求已成功接受
- 3XX: 重定向，表示客户端必须执行一些其他操作才能完成其请求
- 4x： 客户端错误，此类错误状态码指向客户端
- 5XX: 服务器错误， 服务器负责这写错误状态码

### SpringBoot实现RESTfulAPI

- SpringBoot提供的spring-boot-starter-web组件完全支持开发RESTfulAPI提供了与REST操作方式 （GET、 POST、 PUT、 DELETE）对应的注解

- @GetMapping 处理GET请求， 获取资源

- @PostMapping: 处理POST请求， 新增资源

- @PutMapping: 处理PUT请求，更新资源。

- @DeleteMapping: 处理DELETE请求，册删除资源。

- @PatchMapping: 处理PATCH请求， 用于部分更新资源

- 在RESTful架构中，每个网址代表一种资源，所以URI中建议不要包含动词，只包含名词即可，而且所用的名词往往与数据库的表格名对应

- 用户管理模块API示例：

  ![image-20230320213915614](C:\Users\haruki\AppData\Roaming\Typora\typora-user-images\image-20230320213915614.png)

下面是一个完整的示例代码：

```java
@RestController
public class UserController {
    @GetMapping("/user/{id}")
    public String getUserById(@PathVariable int id){
        System.out.println(id);
        return "根据ID获取用户信息";
    }
    @PostMapping("/user")
    public String save(User user){
        return "添加用户";
    }
    @PutMapping("/user")
    public String update(User user){
        return "更新用户";
    }
    @DeleteMapping("/user/{id}")
    public String deleteById(@PathVariable int id){
        System.out.println(id);
        return "根据ID删除用户";
    }
}
```

<div align="center">UserController.java</div>

## 什么是Swagger

- Swagger是一个规范和完整的框架，用于生成、描述、调用和可视化RESTful风格的Web服务，是非常流行的API表达工具
- Swagger能够自动生成完善的RESTfulAPI文档，同时并根据后台代码的修改同步更新，同时提供完整的测试页面来调试API

### 使用Swagger生成WebAPI文档

 <span style="color:red">（Springboot2.X版本）：</span>在SpringBoot项目中集成Swagger同样非常简单，只需在项目中引入springfox-swagger2和springfox-swagger-ui依赖即可。 

```xml
<dependency>
	<groupId>io.springfox</groupId>
	<artifactId>springfox-swagger2</artifactId>
	<version>2.9.2</version>
</dependency>
<dependency>
	<groupId>io.springfox</groupId>
	<artifactId>springfox-swagger-ui</artifactId>
	<version>2.9.2</version>
</dependency>
```

以及配置config：

```java
@Configuration  //启用配置类
@EnableSwagger2 //启用Swagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com"))//com下包交给Swagger2管理
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("演示项目API")
                .description("演示项目")
                .version("1.0")
                .build();
    }
}
```

<div align="center">SwaggerConfig.java</div>

SpringBoot2.6.X，3.X版本前与Swagger有版本冲突问题，需要在applicationproperties中加入以下配置：

```properties
spring.mvc.pathmatchmatching-strategy=ant-path_matcher
```

启动项目访问http://127.0.0.1:8080/swagger-ui.html，即可打开自动生成的可视化测试页面

至于使用**SpringBoot3.X版本**的用户，根据Stackoverflow一篇贴文的高赞回答：

[404 error on Swagger UI with Spring (springdoc-openapi configuration)](https://stackoverflow.com/questions/74776863/404-error-on-swagger-ui-with-spring-springdoc-openapi-configuration)

可以参考SpringBoot官方文档引入Springdoc-openapi模块并按文档指示配置：[Springdoc-openapi](https://springdoc.org/v2/)

```
http://localhost:8080/swagger-ui/index.html
```

也可添加该配置简化网址：

```properties
springdoc.swagger-ui.path=/swagger-ui.html
```

自定义api文档路径：

```properties
springdoc.api-docs.path=/api-docs
```

springdoc-openapi是一个Java库，可以帮助使用Spring Boot项目自动生成API文档。它的工作原理是在运行时检查应用程序，根据Spring配置、类结构和各种注释推断API语义。可以使用swagger-api注释对生成的文档进行补充。

您可以使用以下注释来为请求添加API注释：

```java
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Not Found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")})
```

其中注释可按需选填。

## MybatisPlus快速上手

### ORM介绍

- ORM （Object RelationalMapping，对象关系映射） 是为了解决面向对象与关系数据库存在的互不匹配现象的一种技术。
- ORM通过使用描述对象和数据库之间映射的元数据将程序中的对象自动持久化到关系数据库中。
- ORM框架的本质是简化编程中操作数据库的编码。

### MyBatis-Plus介绍

- MyBatis是一款优秀的数据持久层ORM框架，被广泛地应用于应用系统
- MyBatis能够非常灵活地实现动态SQL，可以使用XML或注解采配置和映射原生信息，能够轻松地将Java的POJO（PlainOrdinaryJavaObject， 普通的Java对象）与数据库中的表和字段进行映射关联
- MyBatis-Plus是一个MyBatis的增强工具，在MyBatis的基础上做了增强，简化了开发

### 添加依赖

**基于MySQL8.x版本,Spring 2.x版本**，参考mybatis官方文档：[mybatis-spring-boot-autoconfigure](https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)

```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>2.0.3</version>
</dependency>
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.2.6</version>
</dependency>
```

**SpringBoot3.x**版本会发现官方发布的3.0版本mybatisPlus需要与mybatis同时使用才可在springboot上使用，但是这二者本身不能共存。因此，在Github的官方issue列表中，官方提供了一种快照测试版本:[MyBatisPlus still not supporting SpringBoot3.0](https://github.com/baomidou/mybatis-plus/issues/4997)

在pom.xml中引入二者：

```xml
<repositories>
        <!-- 下载mybatis-plus SNAPSHOT 版本所需仓库 -->
        <repository>
            <id>ossrh</id>
            <name>OSS Snapshot repository</name>
            <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
            <releases>
                <enabled>false</enabled>
            </releases>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
        </repository>
</repositories>
```

```xml
<dependency>
	<groupId>com.baomidou</groupId>
	<artifactId>mybatis-plus-boot-starter</artifactId>
	<version>3.5.2.7-SNAPSHOT</version>
</dependency>
```

即可在SpringBoot3.x版本下使用mybatisPlus，其它依赖项可照填。

### 全局配置

```properties
spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/springbootdb?useSSL=false
spring.datasource.username=root
spring.datasource.password=123456
mybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
```

同时，需要在启动类处添加注解（写上mapper包路径）：

```java
@MapperScan("com.example.springbootstudy.mapper")
```

### 示例代码

```java
@Mapper
public interface UserMapper extends BaseMapper<User> {
    
}
```

```java
@RestController
public class MUserController {

    @Autowired//自动注入mapper
    private UserMapper userMapper;
    @GetMapping("/muser")
    public List query(){
        List<User> list =  userMapper.selectList(null);
        System.out.println(list);
        return list;
    }

    @PostMapping("/muser")
    public String save(User user){
        int i = userMapper.insert(user);
        if (i>0){
            return "插入成功";
        }else{
            return "插入失败";
        }
    }
}
```

更多请参考mybatisPlus官方文档：[注解 | MyBatis-Plus (baomidou.com)](https://baomidou.com/pages/223848/#tablename)

### 多表查询

对于多表查询，我们无法利用mybatisPlus的特性。我们只能使用注解形式来手写SQL语句，以下为示例代码：

```java
@TableId(type = IdType.AUTO)
    private int id;
    private String username;
    private String password;
    private String birthday;
    @TableField(exist = false)//该属性并不是表中真实存在的字段
    private List<Order> orders;
//...getter,setter,toString
```

<div align="center">User.java</div>

```java
@TableName("t_order")
public class Order {
    @TableId(type = IdType.AUTO)
    private int id;
    private String order_time;
    private double total;
    @TableField(exist = false)
    private User user;
    //...getter,setter,toString
}
```

<div align="center">Order.java</div>

```java
@Mapper
public interface UserMapper extends BaseMapper<User> {

    //查询用户，根据用户id查询信息   select * from user where id=

    @Select("select * from user where id = #{id}")
    User selectById(int id);

    @Select("select * from user")
    @Results(
            {
                    @Result(column = "id",property = "id"),
                    @Result(column = "username",property = "username"),
                    @Result(column = "password",property = "password"),
                    @Result(column = "birthday",property = "birthday"),
                    @Result(column = "id",property = "orders" ,javaType = List.class,
                        many = @Many(select = "com.example.springbootstudy.mapper.OrderMapper.selectByUid"))
            }
    )
    List<User> selectAllUserAndOrders();
}
```

<div align="center">UserMapper.java</div>

```java
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    @Select("select * from t_order where uid = #{uid}")
    @Results(
            {
                    @Result(column = "id", property = "id"),
                    @Result(column = "order_time", property = "order_time", jdbcType = JdbcType.TIMESTAMP),
                    @Result(column = "total", property = "total")
            }
    )//不写这一步会导致该方法查询出的order_time为null
    List<Order> selectByUid(int uid);

    @Select("select * from t_order")
    @Results(
            {
                    @Result(column = "id",property = "id"),
                    @Result(column = "order_time",property = "order_time"),
                    @Result(column = "total",property = "total"),
                    @Result(column = "uid",property = "user" ,javaType = User.class,
                            one = @One(select = "com.example.springbootstudy.mapper.UserMapper.selectById"))
            }
    )
    List<Order> selectAllOrdersAndUser();
}
```

<div align="center">OrderMapper.java</div>

```java
//两个控制器

@RestController
public class DUserController {
    @Autowired//自动注入mapper
    private UserMapper userMapper;
    @GetMapping("/duser")
    public List findAll(){
        List<User> list =  userMapper.selectAllUserAndOrders();
        System.out.println(list);
        return list;
    }
}

@RestController
public class DOrderController {
    @Autowired//自动注入mapper
    private OrderMapper orderMapper;
    @GetMapping("/dorder")
    public List findAll(){
        List<Order> list = orderMapper.selectAllOrdersAndUser();
        System.out.println(list);
        return list;
    }
}
```

### 条件查询

参考官方文档：[条件构造器 | MyBatis-Plus (baomidou.com)](https://baomidou.com/pages/10c804/#abstractwrapper)

以下为示例代码：

```java
@GetMapping("/duser/find")
    public List<User> findByCond(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username","lucy");
        return userMapper.selectList(queryWrapper);
    }
```

### 分页查询

首先定义一个固定标准的配置项

```java
@Configuration
public class MyBatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor paginationInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }
}
```

然后可在控制器进行分页查询：

```java
 @GetMapping("/duser/findbypage")
    public IPage findByPage(){
        //设置起始值及每页条数
        Page<User> page = new Page<>(0,2);
        IPage iPage = userMapper.selectPage(page,null);
        return iPage;
    }
```

## 解决跨域问题

### 为什么会出现跨域问题

- 为了保证浏览器的安全，不同源的客户端脚本在没有明确授权的情况下，不能读写对方资源，称为同源策略，同源策略是浏览器安全的基石
- 同源策略 （Sameoriginpolicy） 是一种约定，它是浏览器最核心也最基本的安全功能
- 所谓同源 （即指在同一个域） 就是两个页面具有相同的协议 （protocol），主机（host） 和端口号 （port）
- 当一个请求url的协议、域名、端口三者之间任意一个与当前页面u不同即为跨域，此时无法读取非同源网页的Cookie，无法向非同源地址发送AJAX请求

### 跨域问题解决

- CORS （Cross-Origin Resource Sharing） 是由W3C制定的一种跨域资源共享技术标准，其目的就是为了解决前端的跨域请求
- CORS可以在不破坏即有规则的情况下，通过后端服务器实现CORS接口，从而实现跨域通信
- CORS将请求分为两类：简单请求和非简单请求， 分别对跨域通信提供了支持

### 简单请求

满足以下条件的请求即为简单请求：

- 请求方法：GET、POSTJ HEAD

- 除了以下的请求头字段之外， 没有自定义的请求头：

  Accept、 Accept-Language、 Content-Language、 Last-Event-D、Content-Type、

- Content-ype的值只有以下三种：
  text/plain、 multipart/form-data、 application/x-www-form-urlencoded

### 简单请求的服务器处理

- 对于简单请求， CORS的策略是请求时在请求头中增加一个Origin字段，

```http
Host: localhost:8080
Oriqin: http://localhost:8081
Referer: http://localhost:8081/index.html
```

- 服务器收到请求后，根据该字段判断是否允许该请求访问，如果允许，则在HTTP头信息中添加Access-Control-Allow-Origin字段。

```http
Access-Control-Alow-Oriqin： http://localhost:8081
Content-Length:20
Content-Type:text/plain;charset=UTF-8
Date: Thu,12 Jul 2018 12:51:14 GMT
```

### 非简单请求

- 对于非简单请求的跨源请求，浏览器会在真实请求发出前增加一次OPTION请求，称为预检请求 preflighttrequest）
- 预检请求将真实请求的信息，包括请求方法、 自定义头字段、 源信息添加到HTTP头信息字段中，询问服务器是否允许这样的操作。
- 例如一个GET请求：

```java
OPTIONS  /test HTTP/1.1
Origin： http://www.test.com
Access-Control-Request-Method: GET
Access-Control-Request-Headers: X-Custom-Header
Host:www.test.com
```

- Access-Control-Request-Method表示请求使用的HTTP方法，Access-Control-Request-Headers包含请求的自定义头字段

服务器收到请求时，需要分别对Origin、 Access-Control-Request-Method、Access-Control-Request-Headers进行验证，验证通过后，会在返回HTTP头信息中添加：

```http
Access-Contro1-Al1ow-Origin: http://www.test.com
Access-Control-Allow-Methods: GET,POST,PUT,DELETE
Access-Control-Allow-Headers: X-Custom-Header
Access-Control-Allow-Credentials:  true
Access-Control-Max-Age:  1728000
```

Access-Control-Allow-Methods、Access-Control-Allow-Headers:真实请求允许的方法 、允许使用的字段
Access-Control-Allow-Credentials：是否允许用户发送、 处理cookie
Access-Control-Max-Age:预检请求的有效期，单位为秒，有效期内不会重新发送预检请求

### 在SpringBoot中配置CORS

使用`@CrossOrigin`注解即可。

## JWT跨域认证

- JSONWeb Token （简称JWT） 是一个token的具体实现方式，是目前最流行的跨域认证解决方案。
- JWT的原理是，服务器认证以后，生成一个JSON对象，发回给用户。
- 用户与服务端通信的时候，都要发回这个JSON对象。服务器完全只靠这个对象认定用户身份。
- 为了防止用户改数据，服务器在生成这个对象的时候， 会加上签名。

 JWT的由三个部分组成，依次如下：

- Header （头部）

- Payload （负载）

- Signature （签名）

- 三部分最终组合为完整的字符串，中间使用分隔，如下：

  Header.Payload.Signature          

```
eyJhbGcioiJIUzI1NiIsInR5cC6IkpXVCJ9，
eyJzdwiOixMjMONTY3ODkwiwibmFtzs6kpvaG4
gRG91iwiaXNTb2NpYWwiOnRydwv9.
4pcPyMD9oPSyXnrXCjTwXyr4Bsezd1AVTmud2fU4
```

### Header

​     Header部分是一个JSON对象，描述JWT的元数据

```json
{
	"alg":"HS256",
	"typ":"JWT"
}
```

- alg属性表示签名的算法（algorithm），默认是HMAC SHA256 （写成HS256）
- typ属性表示这个令牌 （token） 的类型 （type），JWT令牌统一写为JWT
- 最后，将上面的JSON对象使用 Base64URL算法转成字符串

### Payload

- Payload部分也是一个JSON对象，用来存放实际需要传递的数据。JWT规定了7个官方字段，供选用。

  - iss(issuer):签发人

  - exp(expirationtime):过期时间

  - Sub(subject):主题

  - aud(audience):受众

  - nbf(Not Before):生效时间

  - iat(Issued At):签发时间

  - jti(JWT ID):编号

- 注意，JWT默认是不加密的，任何人都可以读到，所以不要把秘密信息放在个部分。
- 这个JSON对象也要使用 Base64URL 算法法转成字符串。

### Signature

- Signature部分是对前两部分的签名，防止数据算改
- 首先， 需要指定一个密钥 （secret）。这个密钥只有服务器才知道，不能泄露给用户
- 然后，使用Header里面指定的签名算法 （默认是HMACSHA256），按照下面的公式产生签名

```java
  HMACSHA256(
         base64urlEncode(header)+"."+
         base64urlEncode(payload)，
         secret)
```

### JWT的特点

- 客户端收到服务器返回的JWT，可以储存在Cookie里面，也可以储存在localStorage
- 客户端每次与服务器通信，都要带上这个JWT， 可以把它放在Cookie里面自动发送，但是这样不能跨域
- 更好的做法是放在HTTP请求的头信息`Authorization`字段里面，单独发送

### 加入依赖

```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.9.1</version>
</dependency>
```

这里仅提供Java SE 9及更高版本写法：

```java
public interface ResultCode {
    public static Integer SUCCESS = 20000;//成功
    public static Integer ERROR = 20001;//失败
}
```

```java
//统一返回结果的类
public class Result {
    private Boolean success;
    private Integer code;
    private String message;
    private Map<String,Object>data= new HashMap<>();

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Result() {
    }

    //成功静态方法
    public static Result ok(){
        Result r = new Result();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }

    //失败静态方法
    public static Result error(){
        Result r = new Result();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }

    public Result success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public Result message(String message){
        this.setMessage(message);
        return this;
    }

    public Result code(Integer code){
        this.setCode(code);
        return this;
    }

    public Result data(String key,Object value){
        this.data.put(key, value);
        return this;
    }

    public Result data(Map<String,Object>map){
        this.setData(map);
        return this;
    }
}
```

```java
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

public class JwtUtils {
     //7天过期
     private static long expire = 604800;
    private static String secret = "abcdfghiabcdfghiabcdfghiabcdfghi";

    public static String generateToken(String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 1000 * expire);

        String header = "{\"alg\":\"HS512\",\"typ\":\"JWT\"}";
        String encodedHeader = Base64.getEncoder().encodeToString(header.getBytes());

        String payload = "{\"sub\":\"" + username + "\",\"iat\":" + now.getTime() / 1000 + ",\"exp\":" + expiration.getTime() / 1000 + "}";
        String encodedPayload = Base64.getEncoder().encodeToString(payload.getBytes());

        String signature = encodedHeader + "." + encodedPayload;
        String encodedSignature = Base64.getEncoder().encodeToString(signature.getBytes());

        return encodedHeader + "." + encodedPayload + "." + encodedSignature;
    }

    public static Claims getClaimsByToken(String token) {
        String[] parts = token.split("\\.");
        String encodedHeader = parts[0];
        String encodedPayload = parts[1];
        String encodedSignature = parts[2];

        String header = new String(Base64.getDecoder().decode(encodedHeader));
        String payload = new String(Base64.getDecoder().decode(encodedPayload));

        if (!verifySignature(encodedHeader + "." + encodedPayload, encodedSignature)) {
            throw new RuntimeException("Invalid signature");
        }

        return Jwts.parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    private static boolean verifySignature(String content, String encodedSignature) {
        byte[] signature = Base64.getDecoder().decode(encodedSignature);
        try {
            Mac mac = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA512");
            mac.init(secretKeySpec);
            byte[] contentBytes = content.getBytes();
            byte[] signatureBytes = mac.doFinal(contentBytes);
            return Arrays.equals(signature, signatureBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            return false;
        }
    }
}
```

这段代码实现了一个简单的 JWT 工具类，可以用于生成和解析 JWT。

JWT 是一种用于身份验证和授权的令牌，由三部分组成：header、payload 和 signature。其中 header 和 payload 都是 JSON 格式的字符串，它们都经过 Base64 编码后放在 JWT 中。signature 是由 header、payload 和密钥计算得出的签名，用于验证 JWT 的完整性和真实性。

在这段代码中，`generateToken()` 方法用于生成 JWT，它接受一个用户名作为参数，然后生成一个包含用户名信息的 JWT。`getClaimsByToken()` 方法用于解析 JWT，它接受一个 JWT 字符串作为参数，然后解析出其中的用户名信息。

具体实现如下：

- `expire`：设置 JWT 的过期时间，单位为秒。
- `secret`：设置 JWT 的密钥，用于计算签名。
- `generateToken()` 方法：

- 获取当前时间和过期时间，并计算它们之间的时间差。
- 构造 header 和 payload，分别包含算法、类型、用户名、签发时间和过期时间等信息。然后对它们进行 Base64 编码。
- 计算 signature，将 header 和 payload 拼接起来，然后使用 HMAC-SHA512 算法和密钥计算出 signature。最后对 signature 进行 Base64 编码，得到最终的 JWT。

- `getClaimsByToken()` 方法：

- 将 JWT 字符串按照 . 分割成三部分：header、payload 和 signature。
- 对 header 和 payload 进行 Base64 解码，得到原始的 JSON 字符串。
- 验证 signature 的有效性，如果无效则抛出异常。
- 使用 JWT 库解析 JWT，并返回其中的 payload 部分，即包含用户名信息的 JSON 对象。

```java
@RestController
@RequestMapping("/tuser")
@CrossOrigin//设置跨域资源共享（CORS）的配置
public class TUserController {
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        String token = JwtUtils.generateToken(user.getUsername());
        return Result.ok().data("token",token);
    }
}
```

## 云端部署

需注意不同的开发环境，本文以Ubuntu20.04提供文档链接：

### MySQL 8.0

[Ubuntu20.04下安装并使用MySQL8.0 - 腾讯云开发者社区-腾讯云 (tencent.com)](https://cloud.tencent.com/developer/article/1873316)

### Nginx

[如何在 Ubuntu 20.04 上安装 Nginx - 腾讯云开发者社区-腾讯云 (tencent.com)](https://cloud.tencent.com/developer/article/1623233)
