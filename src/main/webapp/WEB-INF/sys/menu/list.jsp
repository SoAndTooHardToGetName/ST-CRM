<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>菜单主页</title>
<%@include file="/web/head.jsp"%>
</head>
<body style="padding-top: 30px; padding-left: 5%; padding-right: 5%">

	<form class="layui-form">
		<div class="layui-form-item">
			<label class="layui-form-label">菜单功能：</label>
			<div class="layui-input-inline">
				<input type="text" name="name" class="layui-input" />
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

	<table class="layui-hide" id="demo" lay-data="{id: 'idTest'}"></table>


</body>
<script type="text/javascript">
	layui.use('form', function() {
		var form = layui.form;
	})

	$('#sel').click(select);
	select();
	function select() {
		var vname = $('input[name="name"]').val();
		layui
				.use(
						'table',
						function() {
							var table = layui.table;
							table
									.render({
										elem : '#demo',
										id : 'idTest',
										toolbar : '#toolbarDemo',
										url : '/situOA/menu/selectModel',
										method : 'post',
										where : {
											name : vname,
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
													field : 'code',
													title : '编号',
												},
												{
													field : 'name',
													title : '名称',
													sort : true
												},
												{
													field : 'menuUrl',
													title : '功能地址',
													sort : true
												},
												{
													title : '操作',
													templet : function(d) {
														return '<button class="layui-btn layui-btn-xs layui-btn-radius layui-btn-danger layui-icon layui-icon-delete" onclick="del('
																+ d.id
																+ ')"></button><button class="layui-btn layui-btn-xs layui-btn-radius layui-btn-normal layui-icon layui-icon-about" onclick="upd('
																+ d.id
																+ ')">修改</button>';
													}
												} ] ]
									});
							table.on('toolbar(test)', function(obj) {
								var checkStatus = table
										.checkStatus(obj.config.id);
								switch (obj.event) {
								case 'delete':
									if (!confirm("确认删除？"))
										return;
									var checkStatus = table
											.checkStatus('idTest');
									var vids = "";
									$.each(checkStatus.data, function(index,
											elem) {
										vids += elem.id + ",";
									})
									alert(vids);
									ajax("post", "/situOA/menu/delete", {
										vids : vids
									}, "text", function(data) {
										select();
									})
									break;
								}
							});
						});
	}

	$("#add").click(add);
	function add() {
		layui.use('layer', function() {
			var layer = layui.layer;
			layer.open({
				type : 2,
				content : "/situOA/menu/about",
				area : [ '500px', '500px' ]
			})
		})
	}

	function del(vid) {
		if (!confirm("确认删除该菜单功能？"))
			return;
		ajax("post", "/situOA/menu/deleteModel", {
			id : vid
		}, "text", function(data) {
			if (data == "1")
				layui.use('layer', function() {
					var layer = layui.layer;
					layer.msg('删除成功');
					select();
				})
		})
	}

	function upd(vid) {
		layui.use('layer', function() {
			var layer = layui.layer;
			layer.open({
				type : 2,
				content : "/situOA/menu/about?id=" + vid,
				area : [ '500px', '500px' ]
			})
		})
	}
</script>
<script type="text/html" id="toolbarDemo">
<div class="layui-btn-container">
<button class="layui-btn layui-btn-danger layui-btn-xs layui-icon layui-icon-delete" lay-event="delete">删除选中</button>
</div>
</script>
</html>