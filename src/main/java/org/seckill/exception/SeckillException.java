package org.seckill.exception;

/**
 * 秒杀相关业务异常
 * 
 * <p>Title:SeckillException</p>
 * <p>Description: </p>
 * <p>Company:</p> 
 * @author	LNJ
 * @date	2016年9月3日 下午7:26:36
 * @version 1.0
 */
public class SeckillException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SeckillException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public SeckillException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
