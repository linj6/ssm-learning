package org.seckill.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value={"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillService seckillService;
	
	@Test
	public void testGetSeckillList() {
		List<Seckill> seckills = seckillService.getSeckillList();
		logger.info("list={}",seckills);
		
	}

	@Test
	public void testGetById() {
		long id = 1000;
		Seckill seckill = seckillService.getById(id);
		logger.info("seckill={}",seckill);
	}

	//集成测试代码完整逻辑，注意可重复执行。
	@Test
	public void testSeckillLogic() {
		long id = 1000;
		Exposer exposer = seckillService.exportSeckillUrl(id);
		if(exposer.isExposed()){
			logger.info("exposer={}",exposer);
			long userPhone = 13263932543L;
			String md5 = "df6e5b0c95f0cac6eceb17ac50e64acf";
			try {
				SeckillExecution seckillExecution = seckillService.executeSeckill(id, userPhone, md5);
				logger.info("seckillExecution={}", seckillExecution);
			} catch (SeckillCloseException e1) {
				logger.error(e1.getMessage());
			}catch (RepeatKillException e2) {
				logger.error(e2.getMessage());
			}
		}else{
			//秒杀未开启
			logger.warn("exposer={}",exposer);
		}
		
		//Exposer [
//		exposed=true, 
//		md5=df6e5b0c95f0cac6eceb17ac50e64acf, 
//		seckillId=1000, 
//		now=0, start=0, end=0]
	}

	@Test
	public void testExecuteSeckill() {
		long id = 1000;
		long userPhone = 13263932543L;
		String md5 = "df6e5b0c95f0cac6eceb17ac50e64acf";
		try {
			SeckillExecution seckillExecution = seckillService.executeSeckill(id, userPhone, md5);
			logger.info("seckillExecution={}", seckillExecution);
			/**
			 * 13:33:36.027 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Creating a new SqlSession
			13:33:36.064 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Registering transaction synchronization for SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@15e426ac]
			13:33:36.101 [main] DEBUG o.m.s.t.SpringManagedTransaction - JDBC Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@61773723] will be managed by Spring
			13:33:36.145 [main] DEBUG o.s.dao.SeckillDao.reduceNumber - ==>  Preparing: update seckill set number = number - 1 where seckill_id = ? and start_time <= ? and end_time >= ? and number > 0 
			13:33:36.306 [main] DEBUG o.s.dao.SeckillDao.reduceNumber - ==> Parameters: 1000(Long), 2016-09-04 13:33:35.996(Timestamp), 2016-09-04 13:33:35.996(Timestamp)
			13:33:36.318 [main] DEBUG o.s.dao.SeckillDao.reduceNumber - <==    Updates: 1
			13:33:36.319 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@15e426ac]
			13:33:36.321 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@15e426ac] from current transaction
			13:33:36.321 [main] DEBUG o.s.d.S.insertSuccessKilled - ==>  Preparing: insert ignore into success_killed(seckill_id,user_phone,state) values( ?, ?, 0 ) 
			13:33:36.325 [main] DEBUG o.s.d.S.insertSuccessKilled - ==> Parameters: 1000(Long), 13263932543(Long)
			13:33:36.328 [main] DEBUG o.s.d.S.insertSuccessKilled - <==    Updates: 1
			13:33:36.357 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@15e426ac]
			13:33:36.361 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@15e426ac] from current transaction
			13:33:36.368 [main] DEBUG o.s.d.S.queryByIdWithSuccessKilled - ==>  Preparing: select sk.seckill_id, sk.user_phone, sk.state, sk.create_time, s.seckill_id as "seckill.seckill_id", s.number as "seckill.number", s.name as "seckill.name", s.start_time as "seckill.start_time", s.end_time as "seckill.end_time", s.create_time as "seckill.create_time" from success_killed sk inner join seckill s on sk.seckill_id = s.seckill_id where sk.seckill_id = ? and sk.user_phone = ? 
			13:33:36.370 [main] DEBUG o.s.d.S.queryByIdWithSuccessKilled - ==> Parameters: 1000(Long), 13263932543(Long)
			13:33:36.470 [main] DEBUG o.s.d.S.queryByIdWithSuccessKilled - <==      Total: 1
			13:33:36.495 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@15e426ac]
			13:33:36.498 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Transaction synchronization committing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@15e426ac]
			13:33:36.499 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Transaction synchronization deregistering SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@15e426ac]
			13:33:36.499 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Transaction synchronization closing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@15e426ac]
			13:33:36.509 [main] INFO  o.seckill.service.SeckillServiceTest - seckillExecution=SeckillExecution [seckillId=1000, state=1, stateInfo=秒杀成功, successKilled=SuccessKilled [seckillId=1000, userPhone=13263932543, state=0, createTime=Sun Sep 04 13:33:36 CST 2016, seckill=Seckill [seckillId=1000, name=1000元秒杀iphone6, startTime=Sat Sep 03 00:00:00 CST 2016, endTime=Mon Sep 05 00:00:00 CST 2016, createTime=Thu Sep 01 12:59:36 CST 2016]]]
			
			 */
		} catch (SeckillCloseException e1) {
			logger.error(e1.getMessage());
		}catch (RepeatKillException e2) {
			logger.error(e2.getMessage());
		}
	}
	
	@Test
	public void executeSeckillProcedure(){
		long seckillId = 1002;
		long userPhone = 13234325678L;
		Exposer exposer = seckillService.exportSeckillUrl(seckillId);
		if(exposer != null){
			String md5 = exposer.getMd5();
			SeckillExecution seckillExecution =seckillService.executeSeckillProcedure(seckillId, userPhone, md5);
			logger.info(seckillExecution.getStateInfo());
		}
		
	}

}
