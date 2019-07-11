package com.xms.house.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xms.house.common.RestResponse;
import com.xms.house.exception.IllegalParamsException;
import com.xms.house.exception.IllegalParamsException.Type;
import com.xms.house.model.User;
import com.xms.house.service.UserService;

@RequestMapping("user")
@RestController
public class userController {

	private static Logger logger = LoggerFactory.getLogger(userController.class);
	
	@Value("${server.port}")
	private Integer port;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	  @Autowired
	  private UserService userService;
	  //-------------------查询---------------------
	  
	  @RequestMapping("getById")
	  public RestResponse<User> getUserById(Long id){
	    User user = userService.getUserById(id);
	    return RestResponse.success(user);
	  }
	  
	  @RequestMapping("getList")
	  public RestResponse<List<User>> getUserList(@RequestBody User user){
	    List<User> users = userService.getUserByQuery(user);
	    return RestResponse.success(users);
	  }
	  
	  //----------------------注册----------------------------------
	  @RequestMapping("add")
	  public RestResponse<User> add(@RequestBody User user){
	    userService.addAccount(user,user.getEnableUrl());
	    return RestResponse.success();
	  }
	  
	  /**
	   * 主要激活key的验证
	   */
	  @RequestMapping("enable")
	  public RestResponse<Object> enable(String key){
	    userService.enable(key);
	    return RestResponse.success();
	  }
	
	  
	  //------------------------登录/鉴权--------------------------
	  
	  /**
	   * 登录
	   * @param user
	   * @return
	   */
	  @RequestMapping("auth")
	  public RestResponse<User> auth(@RequestBody User user){
	    User finalUser = userService.auth(user.getEmail(),user.getPasswd());
	    return RestResponse.success(finalUser);
	  }
	  
	  /**
	   * 鉴权接口
	   * @param token
	   * @return
	   */
	  @RequestMapping("get")
	  public RestResponse<User> getUser(String token){
	    User finalUser = userService.getLoginedUserByToken(token);
	    return RestResponse.success(finalUser);
	  }
	  
	  /**
	   * 退出登录操作
	   * @param token
	   * @return
	   */
	  @RequestMapping("logout")
	  public RestResponse<Object> logout(String token){
	    userService.invalidate(token);
	    return RestResponse.success();
	  }
	  
	  @RequestMapping("update")
	  public RestResponse<User> update(@RequestBody User user){
	    User updateUser = userService.updateUser(user);
	    return RestResponse.success(updateUser);
	  }
	  
	  @RequestMapping("reset")
	  public RestResponse<User> reset(String key ,String password){
	    User updateUser = userService.reset(key,password);
	    return RestResponse.success(updateUser);
	  }
	  
	  @RequestMapping("getKeyEmail")
	  public RestResponse<String> getKeyEmail(String key){
	    String  email = userService.getResetKeyEmail(key);
	    return RestResponse.success(email);
	  }
	  
	  @RequestMapping("resetNotify")
	  public RestResponse<User> resetNotify(String email,String url){
	    userService.resetNotify(email,url);
	    return RestResponse.success();
	  }
	
	
	
	
	
	/**
	 * 测试redis客户端接口
	 * @param id
	 * @return
	 */
	@RequestMapping("getusername")
	public RestResponse<String> getusername(Long id){
		
		/*if(id.equals(11)) {
			throw new IllegalParamsException(Type.WRONG_PAGE_NUM, "分页错误");
		}*/
		logger.info("Incoming Request, and port =" + port);
		redisTemplate.opsForValue().set("key1", "value1");
		logger.info("redis is get key1=" + redisTemplate.opsForValue().get("key1"));
		return RestResponse.success("test-username"+ port);
	}
	
}
