<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>员工主页</title>
<%@include file="/web/head.jsp"%>
</head>
<body style="padding-top: 30px; padding-left: 5%; padding-right: 5%">

	<form class="layui-form">
		<div class="layui-form-item">
			<label class="layui-form-label">顾客编号：</label>
			<div class="layui-input-inline">
				<input type="text" name="custCode" class="layui-input" />
			</div>

			<label class="layui-form-label">员工编号：</label>
			<div class="layui-input-inline">
				<input type="text" name="userCode" class="layui-input" />
			</div>
			<label class="layui-form-label">商品编号：</label>
			<div class="layui-input-inline">
				<input type="text" name="prodCode" class="layui-input" />
			</div>
		</div>

		<div class="layui-input-block">
			<span>
				<button
					class="layui-btn layui-btn-sm layui-btn-normal layui-icon layui-icon-search"
					type="button" id="sel">查询</button>
				<button
					class="layui-btn layui-btn-sm layui-btn-normal layui-icon layui-icon-add-1"
					type="button" id="add">添加</button>
			</span>
		</div>
	</form>

	<table class="layui-hide" id="demo" lay-filter="test"></table>

</body>
<script type="text/javascript">
	layui.use('form', function() {
		var form = layui.form;
	})

	var user = "${user.roleCode}";

	$('#sel').click(select);
	select();
	function select() {
		var vcustCode = $('input[name="custCode"]').val();
		var vuserCode = $('input[name="userCode"]').val();
		var vprodCode = $('input[name="prodCode"]').val();
		layui
				.use(
						[ 'table', 'layer', 'upload' ],
						function() {
							var table = layui.table;
							var layer = layui.layer;
							var upload = layui.upload;
							table
									.render({
										elem : '#demo',
										toolbar : '#toolbarDemo',
										defaultToolbar : [ 'filter' ],
										method : "post",
										url : '/situOA/order/selectModel',
										where : {
											custCode : vcustCode,
											userCode : vuserCode,
											prodCode : vprodCode
										},
										page : true,
										cols : [ [ //表头
												{
													type : "checkbox",
												},
												{
													type : "numbers",
													title : 'ID',
													sort : true,
												},
												{
													field : 'custName',
													title : '顾客',
													templet : function(d) {
														return d.customerInfoModel.name
													}
												},
												{
													field : 'userName',
													title : '员工',
													sort : true,
													templet : function(d) {
														return d.userModel.name
													}
												},
												{
													field : 'prodName',
													title : '商品',
													templet : function(d) {
														return d.productInfoModel.name
													}
												},
												{
													field : 'count',
													title : '数量',
												},
												{
													field : 'status',
													title : '状态',
													templet : function(d) {
														if (d.status == 0)
															return "待受理";
														if (d.status == 1)
															return "备货中";
														if (d.status == 2)
															return "已发货";
														if (d.status == 3)
															return "已收货";
														if (d.status == 4)
															return "完成交易";
													}
												},
												{
													title : '操作',
													templet : function(d) {
														var item = "";
														if (user != "empSell")
															item += '<button class="layui-btn layui-btn-xs layui-btn-radius layui-btn-danger layui-icon layui-icon-delete" onclick="del('
																	+ d.id
																	+ ')"></button>';
														if (d.status == 0
																&& user != "empSell")
															item += '<button class="layui-btn layui-btn-xs layui-btn-radius layui-btn-danger" onclick="upd('
																	+ d.id
																	+ ','
																	+ d.status
																	+ ',\''
																	+ d.count
																	+ '\',\''
																	+ d.prodCode
																	+ '\')">备货</button>';
														if (d.status == 1
																&& user != "empSell")
															item += '<button class="layui-btn layui-btn-xs layui-btn-radius layui-btn-danger" onclick="upd('
																	+ d.id
																	+ ','
																	+ d.status
																	+ ',\''
																	+ d.count
																	+ '\',\''
																	+ d.prodCode
																	+ '\')">发货</button>';
														if (d.status == 2
																&& user != "empSell")
															item += '<button class="layui-btn layui-btn-xs layui-btn-radius layui-btn-danger" onclick="upd('
																	+ d.id
																	+ ','
																	+ d.status
																	+ ',\''
																	+ d.count
																	+ '\',\''
																	+ d.prodCode
																	+ '\')">确认收货</button>';
														if (d.status == 3)
															item += '<button class="layui-btn layui-btn-xs layui-btn-radius layui-btn-danger" onclick="jump()">售后回访</button>';
														return item;
													}
												} ] ]
									});
							table.on('toolbar(test)', function(obj) {
								var checkStatus = table
										.checkStatus(obj.config.id);
								switch (obj.event) {
								case 'delete':
									alert("待实现");
									break;
								}
							});

						})
	}
	$("#add").click(add);
	function add() {
		layui.use('layer', function() {
			var layer = layui.layer;
			layer.open({
				type : 2,
				content : "/situOA/order/about",
				area : [ '500px', '550px' ]
			})
		})
	}
	function del(vid) {
		if (!confirm("确认删除该记录？"))
			return;
		ajax("post", "/situOA/order/deleteModel", {
			id : vid
		}, "text", function(data) {
			if (data == "1")
				layui.use('layer', function() {
					var layer = layui.layer;
					layer.msg('删除成功');
				})
			select();
		})
	}

	function upd(vid, vstatus, vcount, vprodCode) {
		ajax("post", "/situOA/order/update", {
			id : vid,
			status : vstatus,
			count : vcount,
			prodCode : vprodCode
		}, "text", function(data) {
			select();
		})
	}

	function jump() {
		location.href = "/situOA/page/mailSend";
	}

	init();
	function init() {
		if (user == "empSell")
			$('input[name="userCode"]').prop("readonly", true)
	}
</script>
<script type="text/html" id="toolbarDemo">
  <div class="layui-btn-container">
    <button class="layui-btn layui-btn-danger layui-btn-xs layui-icon layui-icon-delete" lay-event="delete">删除选中</button>
  </div>
</script>
</html>