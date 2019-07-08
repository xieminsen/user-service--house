package com.xms.house.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xms.house.common.RestResponse;


@RestController
public class userController {

	private static Logger logger = LoggerFactory.getLogger(userController.class);
	
	@RequestMapping("getusername")
	public RestResponse<String> getusername(Long id){
		logger.info("Incoming Request");
		return RestResponse.success("test-username");
	}
	
}
