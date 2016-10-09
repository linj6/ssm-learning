package org.seckill.exception;

/**
 * 重复秒杀异常（运行时异常）
 * 
 * Java异常主要是分编译期异常和运行期异常，
 * 运行期异常不需要手动try-catch，
 * Spring的声明式事务只接受运行期异常回滚策略，
 * 当抛出非运行期异常时Spring不会帮我们做回滚的。
 * 
 * <p>Title:RepeatKillException</p>
 * <p>Description: </p>
 * <p>Company:</p> 
 * @author	LNJ
 * @date	2016年9月3日 下午7:17:07
 * @version 1.0
 */
public class RepeatKillException extends SeckillException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RepeatKillException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public RepeatKillException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
