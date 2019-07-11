package com.xms.house.exception;

/**
 *   定义异常类型
 *   WRONG_PAGE_NUM  分页参数不合法
 *   WRONG_TYPE   类型
 * @author xie
 *
 */
public class IllegalParamsException extends RuntimeException implements WithTypeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Type type;
	
	public IllegalParamsException(){
		
	}
	
	public IllegalParamsException(Type type,String msg){
		super(msg);
		this.type = type;
	}
	
	public Type type(){
		return type;
	}
	
	public enum Type{
		WRONG_PAGE_NUM,WRONG_TYPE
	}
}
