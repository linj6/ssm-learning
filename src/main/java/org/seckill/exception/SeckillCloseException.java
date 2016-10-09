package org.seckill.exception;

/**
 * 秒杀关闭异常
 * 例如：秒杀已经结束，还访问该秒杀接口时，应该抛出秒杀关闭异常。
 * 
 * <p>Title:SeckillCloseException</p>
 * <p>Description: </p>
 * <p>Company:</p> 
 * @author	LNJ
 * @date	2016年9月3日 下午7:22:09
 * @version 1.0
 */
public class SeckillCloseException extends SeckillException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SeckillCloseException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public SeckillCloseException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
