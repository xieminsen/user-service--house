package com.xms.house.common;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *  统一异常处理
 * @author xie
 *
 */
@ControllerAdvice
public class GlobalExceptionHanlder {
//  
//    @Autowired
//    private Tracer tracer;

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHanlder.class);
	
	/**
	 * 微服务下发生异常只能返回不同的状态码
	 * @param req
	 * @param throwable
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(value = Throwable.class)//定义为处理异常的注解
	@ResponseBody
	public RestResponse<Object> handler(HttpServletRequest req, Throwable throwable){
		LOGGER.error(throwable.getMessage(),throwable);
//	    tracer.addTag(Span.SPAN_ERROR_TAG_NAME, ExceptionUtils.getExceptionMessage(throwable));
//	    System.out.println(tracer.getCurrentSpan().getTraceId());
		RestCode restCode = Exception2CodeRepo.getCode(throwable);
		RestResponse<Object> response = new RestResponse<Object>(restCode.code,restCode.msg);
		return response;
	}
	
}
