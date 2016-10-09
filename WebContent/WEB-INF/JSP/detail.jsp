<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 引入jstl -->
<%@include file="common/tag.jsp" %>
<%
	request.getSession().setAttribute("ctx",request.getContextPath());
	
%>

<!DOCTYPE html>
<html>
   <head>
      <title>秒杀详情页</title>
      <!-- 静态包含 -->
      <%@include file="common/head.jsp" %>
   </head>
   <body>
		<!-- 页面显示部分 -->
		<div class="container">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h2>${seckill.name }</h2>
				</div>
				<div class="panel-body">
					<h2 class="text-danger" style="text-align: center;">
						<!-- 显示time图标 -->
						<span class="glyphicon glyphicon-time"></span>
						<!-- 展示倒计时 -->
						<span class="glyphicon" id="seckill-box"></span>
					</h2>
				</div>
			</div>
		</div>      

     	<!-- 登录弹出层，输入电话 -->
     	<div id="killPhoneModal" class="modal fade">
     		<div class="modal-dialog">
     			<div class="modal-content">
     				<div class="modal-header">
     					<h3 class="modal-title text-center">
							<span class="glyphicon glyphicon-time"></span>秒杀电话：
     					</h3>
     				</div>
     				<div class="modal-body">
     					<div class="row">
     						<div class="col-xs-8 col-xs-offset-2">
     							<input type="text" name="killPhone" id="killPhoneKey"
     								placeholder="填写手机号" class="form-control"/>
     						</div>
     					</div>
     				</div>
     				
     				<div class="modal-footer">
     					<!-- 验证信息 -->
     					<span id="killPhoneMessage" class="glyphicon"></span>
     					<button type="button" id="killPhoneBtn" class="btn btn-success">
     						<span class="glyphicon glyphicon-phone"></span>
     						Submit
     					</button>
     				</div>
     			</div>
     		</div>
     	</div>
     
	</body>
	 <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
	<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
	<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<!-- jQuery cookie操作插件 -->
	<script src="<%=request.getContextPath() %>/resources/js/jquery.cookie/jquery.cookie.js"></script>
	<!-- jQuery countDown倒计时的插件 -->
	<script src="http://cdn.bootcss.com/jquery.countdown/2.2.0/jquery.countdown.js"></script>
	<!--<script src="<%=request.getContextPath() %>/resources/js/jquery.countdown/jquery.plugin.js"></script>-->
	<!--<script src="<%=request.getContextPath() %>/resources/js/jquery.countdown/jquery.countdown.js"></script>-->
	<!-- 编写交互操作 -->
	<script src="<%=request.getContextPath() %>/resources/script/seckill.js" type="text/javascript"></script>
	
	<script type="text/javascript">
		$(function(){
			//使用 EL表达式传入参数
			seckill.detail.init({
				seckillId : ${seckill.seckillId},
				startTime : ${seckill.startTime.time},
				endTime : ${seckill.endTime.time}
			});
			
		});
	
	</script>
</html>