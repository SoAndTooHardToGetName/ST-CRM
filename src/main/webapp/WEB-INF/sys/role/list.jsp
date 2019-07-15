<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>职业维护</title>
<%@include file="/web/head.jsp"%>
</head>
<body style="padding-top: 30px; padding-left: 5%; padding-right: 5%">
	<form class="layui-form">
		<div class="layui-form-item">
			<label class="layui-form-label">编号：</label>
			<div class="layui-input-inline">
				<input type="text" name="code" class="layui-input" value="" />
			</div>
			<label class="layui-form-label">职位名：</label>
			<div class="layui-input-inline">
				<input type="text" name="name" class="layui-input" value="" />
			</div>
		</div>
		<div class="layui-input-block">
			<button
				class="layui-btn layui-btn-sm layui-btn-normal layui-icon layui-icon-search"
				type="button" id="sel">查询</button>
			<button
				class="layui-btn layui-btn-sm layui-btn-normal layui-icon layui-icon-add-1"
				type="button" id="add">添加</button>
		</div>
	</form>

	<table class="layui-hide" id="demo" lay-filter="test"
		lay-data="{id: 'idTest'}"></table>

</body>
<script type="text/javascript">
	layui.use('form', function() {
		var form = layui.form;
	})

	$('#sel').click(select);
	select();
	function select() {
		var vname = $('input[name="name"]').val();
		var vcode = $('input[name="code"]').val();
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
										url : '/situOA/role/selectModel',
										method : 'post',
										where : {
											name : vname,
											code : vcode
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
													title : '操作',
													templet : function(d) {
														return '<button class="layui-btn layui-btn-xs layui-btn-radius layui-btn-danger layui-icon layui-icon-delete" onclick="del('
																+ d.id
																+ ')"></button><button class="layui-btn layui-btn-xs layui-btn-radius layui-btn-normal layui-icon layui-icon-about" onclick="upd('
																+ d.id
																+ ')">修改</button><button class="layui-btn layui-btn-xs layui-btn-radius layui-btn-normal layui-icon layui-icon-senior" onclick="access(\''
																+ d.code
																+ '\')">设置权限</button>';
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
									ajax("post", "/situOA/role/delete", {
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
				content : "/situOA/role/about",
				area : [ '500px', '500px' ]
			})
		})
	}

	function del(vid) {
		if (!confirm("确认删除该职位？此操作将影响相应的员工信息"))
			return;
		ajax("post", "/situOA/role/deleteModel", {
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
				content : "/situOA/role/about?id=" + vid,
				area : [ '500px', '500px' ]
			})
		})
	}

	function access(vcode) {
		layui.use('layer', function() {
			var layer = layui.layer;
			layer.open({
				type : 2,
				content : "/situOA/access/set?roleCode=" + vcode,
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