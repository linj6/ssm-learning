//存放主要交互逻辑js代码
//javascript 模块化

//获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
var curWwwPath=window.document.location.href;
//获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
var pathName=window.document.location.pathname;
var pos=curWwwPath.indexOf(pathName);
//获取主机地址，如： http://localhost:8083
var localhostPaht=curWwwPath.substring(0,pos);
//获取带"/"的项目名，如：/uimcardprj
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);

var seckill = {
	//封装秒杀相关 ajax 的url
	url : {
		now : function(){
			
			return projectName + '/seckill/time/now';
		},
		exposer : function(seckillId){
			return projectName + '/seckill/' + seckillId + '/exposer'; 
		},
		execution : function(seckillId,md5){
			return projectName + '/seckill/' + seckillId + '/' + md5 + '/execution'; 
		}
	},
	handleSeckillKill : function(seckillId,node){
		//处理秒杀逻辑
		node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
		$.post(seckill.url.exposer(seckillId),{},function(result){
			//在回调函数中，执行交互流程
			if(result && result['success']){
				var exposer = result['data'];
				
				if(exposer['exposed']){
					//开启秒杀
					//获取秒杀地址
					var md5 = result['data']['md5'];
//					var killUrl = '/seckill/' + seckillId + '/' + md5 + 'execution';
					var killUrl = seckill.url.execution(seckillId,md5);
					console.log("killUrl:" + killUrl);
					
					//绑定一次点击事件
					$('#killBtn').one('click',function(){
						//执行秒杀请求
						//1、先禁用按钮
						$(this).addClass('disabled');
						//2、发送秒杀请求执行秒杀
						$.post(killUrl,{},function(result){
							if(result && result['success']){
								var killResult = result['data'];
								var state = killResult['state'];
								var stateInfo = killResult['stateInfo'];
								//3、显示秒杀结果
								node.html('<span class="label label-success">' + stateInfo + '</span>');
							}
						})
					});
					node.show();
				}else{
					//未开启秒杀
					var now = exposer['now'];
					var start = exposer['start'];
					var end = exposer['end'];
					//重新计算计时逻辑
					seckill.countdown(seckillId,now,start,end);
				}
			}else{
				console.log("result:" + result);
			}
		});
		
	},
	//验证手机号
	validatePhone : function(phone){
		if(phone && phone.length == 11 && !isNaN(phone)){
			return true;
		}else{
			return false;
		}
	},
	countdown : function(seckillId,nowTime,startTime,endTime){
		var seckillBox = $('#seckill-box');
		//时间判断
		if(nowTime > endTime){
			//秒杀已经结束
			seckillBox.html("秒杀已经结束");
		}else if(nowTime < startTime){
			//秒杀还没开始,开始倒计时操作
			var killTime = new Date(startTime + 1000);
			
			seckillBox.countdown(killTime,function(event){
				//时间格式
				var format = event.strftime('秒杀倒计时： %D天  %H时  %M分  %S秒');
				seckillBox.html(format);
				/*时间完成后回调事件*/
			}).on('finish.countdown',function(){
				//获取秒杀地址，控制显示逻辑，执行秒杀
				seckill.handleSeckillKill(seckillId,seckillBox);
			});
			//seckillBox.html('秒杀还未开始');
		}else{
			seckill.handleSeckillKill(seckillId,seckillBox);
//			seckillBox.html(nowTime);
		}
	},
	//详情页秒杀逻辑
	detail : {
		//详情页初始化
		init : function(params){
			//手机验证和交互 ，计时交互
			//规划交互流程
			//在cookie中查找手机号
			var killPhone = $.cookie('killPhone');
			var startTime = params['startTime'];
			var endTime = params['endTime'];
			var seckillId = params['seckillId']
			
			//验证手机号
			if(!seckill.validatePhone(killPhone)){
				//绑定手机号
				//控制输出
				var killPhoneModal = $("#killPhoneModal");
				//显示弹出层
				killPhoneModal.modal({
					//显示弹出层
					show : true,
					//禁止位置关闭
					backdrop : 'static',
					//关闭键盘事件
					keyboard : false
				});
				
				$("#killPhoneBtn").click(function(){
					var inputPhone = $("#killPhoneKey").val();
					if(seckill.validatePhone(inputPhone)){
						//手机号写入cookie
						$.cookie('killPhone',inputPhone,{expires : 7,path : '/ssm-learning/seckill'})
						//刷新页面
						window.location.reload();
						
					}else{
						$("#killPhoneMessage").hide().html("<label class='label label-danger'>手机号错误！</label>").show(500);
					}
				})
			}
			
			//已经登录
			$.get(seckill.url.now(),{},function(result){
				if(result && result["success"]){
					var nowTime = result['data'];
					//时间判断,计时交互
					seckill.countdown(seckillId,nowTime,startTime,endTime);
				}else{
					console.log("result:" + result);
					
				}
			});
		}
	}
}
