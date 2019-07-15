<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>权限维护</title>
<%@include file="/web/head.jsp"%>
</head>
<body style="padding-top: 30px; padding-left: 5%; padding-right: 5%">
	<form class="layui-form">
		<div class="layui-form-item">

			<label class="layui-form-label">职位名称：</label>
			<div class="layui-input-inline">
				<select name="roleCode">
					<option value=""></option>
					<c:forEach items="${roles}" var="s">
						<option value="${s.code}">${s.name}</option>
					</c:forEach>
				</select>
			</div>

			<label class="layui-form-label">菜单名称：</label>
			<div class="layui-input-inline">
				<select name="menuCode">
					<option value=""></option>
					<c:forEach items="${menus}" var="s">
						<option value="${s.code}">${s.name}</option>
					</c:forEach>
				</select>
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

	<table class="layui-hide" id="demo"  lay-data="{id: 'idTest'}"></table>
</body>
<script type="text/javascript">
	layui.use('form', function() {
		var form = layui.form;
	})

	$('#sel').click(select);
	select();
	function select() {
		var vroleCode = $('select[name="roleCode"]').val();
		var vmenuCode = $('select[name="menuCode"]').val();
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
										url : '/situOA/access/selectModel',
										method : 'post',
										where : {
											roleCode : vroleCode,
											menuCode : vmenuCode
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
													field : 'roleName',
													title : '职位',
													sort : true
												},
												{
													field : 'menuName',
													title : '菜单功能',
													sort : true
												},
												{
													title : '操作',
													templet : function(d) {
														var item = '<button class="layui-btn layui-btn-xs layui-btn-radius layui-btn-danger layui-icon layui-icon-delete" onclick="del('
																+ d.id
																+ ')"></button><button class="layui-btn layui-btn-xs layui-btn-radius layui-btn-normal layui-icon layui-icon-about" onclick="upd('
																+ d.id
																+ ','
																+ d.state
																+ ')">';
														if (d.state == 1)
															item += '禁用</button>';
														else
															item += '启用</button>';
														return item;
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
									ajax("post", "/situOA/access/delete", {
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
				content : "/situOA/access/about",
				area : [ '500px', '500px' ]
			})
		})
	}

	function del(vid) {
		if (!confirm("确认删除该职位菜单权限？"))
			return;
		ajax("post", "/situOA/access/deleteModel", {
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

	function upd(vid, vstate) {
		ajax("post", "/situOA/access/upd", {
			id : vid,
			state : vstate
		}, "text", function(data) {
			if (data == "1")
				select();
		})
	}
</script>
<script type="text/html" id="toolbarDemo">
<div class="layui-btn-container">
<button class="layui-btn layui-btn-danger layui-btn-xs layui-icon layui-icon-delete" lay-event="delete">删除选中</button>
</div>
</script>
</html>