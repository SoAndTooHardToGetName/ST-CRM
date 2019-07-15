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
			<label class="layui-form-label">员工编号：</label>
			<div class="layui-input-inline">
				<input type="text" name="userCode" class="layui-input" />
			</div>

			<label class="layui-form-label">客户编号：</label>
			<div class="layui-input-inline">
				<input type="text" name="custCode" class="layui-input" />
			</div>
		</div>

		<div class="layui-input-block">
			<span>
				<button
					class="layui-btn layui-btn-sm layui-btn-normal layui-icon layui-icon-search"
					type="button" id="sel">查询</button>
			</span>
		</div>
	</form>

	<table class="layui-hide" id="demo" lay-filter="test"></table>

</body>
<script type="text/javascript">
	var userRole = "${user.roleCode}";
	layui.use('form', function() {
		var form = layui.form;
	})

	$('#sel').click(select);
	select();
	function select() {
		var vuserCode = $('input[name="userCode"]').val();
		var vcustCode = $('input[name="custCode"]').val();
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
										method : "post",
										url : '/situOA/mail/selectModel',
										where : {
											userCode : vuserCode,
											custCode : vcustCode,
										},
										page : true,
										cols : [ [ //表头
												{
													type : "numbers",
													title : 'ID',
													sort : true,
												},
												{
													field : 'userName',
													title : '员工姓名',
												},
												{
													field : 'custName',
													title : '客户姓名',
													sort : true,
												},
												{
													field : 'status',
													title : '主题',
												},
												{
													field : 'content',
													title : '内容',
												},
												{
													field : 'createTime',
													title : '时间',
												},
												{
													title : '操作',
													templet : function(d) {
														return '<button class="layui-btn layui-btn-xs layui-btn-radius layui-btn-danger layui-icon layui-icon-delete" onclick="del('
																+ d.id
																+ ')"></button>';
													}
												} ] ]
									});
						})
	}

	function del(vid) {
		if (!confirm("确认删除该记录？"))
			return;
		ajax("post", "/situOA/mail/deleteModel", {
			id : vid
		}, "text", function(data) {
			layui.use('layer', function() {
				var layer = layui.layer;
				if (data == "1") {
					layer.msg('删除成功');
					select();
				} else if (data == "2")
					layer.msg('无权限操作')
			})
		})
	}

	init()
	function init() {
		if (userRole == "empSell")
			$('input[name="userCode"]').prop("readonly", true)
	}
</script>
</html>