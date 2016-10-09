package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

public interface SuccessKilledDao {
	
	/**
	 * 插入购买明细,可过滤重复
	 * @param seckillId 商品 id
	 * @param userPhone 用户手机号码
	 * @return 插入的行数
	 */
	int insertSuccessKilled(@Param("seckillId")long seckillId ,@Param("userPhone")long userPhone);
	
	/**
	 * 根据id查询SuccessKilled并携带秒杀商品对象
	 * @param seckillId
	 * @return 
	 */
	SuccessKilled queryByIdWithSuccessKilled(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);
}
