package org.seckill.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="classpath:spring/spring-dao.xml")
public class SuccessKilledDaoTest {

	@Resource
	private SuccessKilledDao successKilledDao;
	
	@Test
	public void testInsertSuccessKilled() {
		long seckillId = 1001L;
		long userPhone = 18970970453L;
		int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
		System.out.println("insertCount=" + insertCount);
	}

	@Test
	public void testQueryByIdWithSuccessKilled() {
		long seckillId = 1001L;
		long userPhone = 18970970453L;
		SuccessKilled successKilled = successKilledDao.queryByIdWithSuccessKilled(seckillId, userPhone);
		System.out.println(successKilled);
	}

}
