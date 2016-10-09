package org.seckill.dao;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 配置spring和junit整合，junit启动时加载springIOC容器
 * spring-test,junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件位置
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SeckillDaoTest {

	//注入DAO实现类依赖
	@Resource
	private SeckillDao seckillDao;
	
	@Test
	public void testQueryById() {
		long id = 1000;
		Seckill seckill = seckillDao.queryById(id);
		System.out.println(seckill.getName());
		System.out.println(seckill);
	}


	@Test
	public void testQueryAll() {
		/**
		 * 错误：
		 * org.mybatis.spring.MyBatisSystemException: nested exception is org.apache.ibatis.binding.BindingException: 
		 * Parameter 'offset' not found. Available parameters are [1, 0, param1, param2]
		 * 
		 * 原因：
		 * List<Seckill> queryAll(int offset,int limit)
		 * 
		 * java没有保存形参的记录：queryAll(int offset,int limit) ---> queryAll(arg0,arg1)
		 * 
		 * 解决方法：使用@param注解指定形参参数名称
		 */
		
		/**
		 * 运行结果：
		 * Seckill [seckillId=1000, name=1000元秒杀iphone6, startTime=Sun Nov 01 00:00:00 CST 2015, endTime=Mon Nov 02 00:00:00 CST 2015, createTime=Thu Sep 01 12:59:36 CST 2016]
		   Seckill [seckillId=1001, name=500元秒杀ipad2, startTime=Sun Nov 01 00:00:00 CST 2015, endTime=Mon Nov 02 00:00:00 CST 2015, createTime=Thu Sep 01 12:59:36 CST 2016]
		   Seckill [seckillId=1002, name=300元秒杀小米4, startTime=Sun Nov 01 00:00:00 CST 2015, endTime=Mon Nov 02 00:00:00 CST 2015, createTime=Thu Sep 01 12:59:36 CST 2016]
		   Seckill [seckillId=1003, name=200元秒杀红米note, startTime=Sun Nov 01 00:00:00 CST 2015, endTime=Mon Nov 02 00:00:00 CST 2015, createTime=Thu Sep 01 12:59:36 CST 2016]
		 * 
		 */
		
		List<Seckill> seckills = seckillDao.queryAll(0, 100);
		for(Seckill seckill : seckills){
			System.out.println(seckill);
		}
	}
	
	@Test
	public void testReduceNumber() {
		Date killTime = new Date();
		int updateCount = seckillDao.reduceNumber(1000L, killTime);
		System.out.println("updateCount=" + updateCount);
	}

	

}
