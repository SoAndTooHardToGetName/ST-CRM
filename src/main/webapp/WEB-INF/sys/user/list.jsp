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
			<label class="layui-form-label">姓名：</label>
			<div class="layui-input-inline">
				<input type="text" name="name" class="layui-input" />
			</div>

			<label class="layui-form-label">用户名：</label>
			<div class="layui-input-inline">
				<input type="text" name="userName" class="layui-input" />
			</div>

			<label class="layui-form-label">编号：</label>
			<div class="layui-input-inline">
				<input type="text" name="code" class="layui-input" />
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">职位：</label>
			<div class="layui-input-inline">
				<select name="roleCode" lay-search>
					<option value=""></option>
				</select>
			</div>
			<label class="layui-form-label">上级：</label>
			<div class="layui-input-inline">
				<select name="parentCode" lay-search>
					<option value=""></option>
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
		var vuserName = $('input[name="userName"]').val();
		var vcode = $('input[name="code"]').val();
		var vroleCode = $('select[name="roleCode"]').val();
		var vparentCode = $('select[name="parentCode"]').val();
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
										id : 'idTest',
										toolbar : '#toolbarDemo',
										defaultToolbar : [ 'filter' ],
										method : "post",
										url : '/situOA/user/selectModel',
										where : {
											name : vname,
											userName : vuserName,
											code : vcode,
											roleCode : vroleCode,
											parentCode : vparentCode
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
													title : '姓名',
													sort : true
												},
												{
													field : 'userName',
													title : '用户名',
												},
												{
													field : 'roleName',
													title : '角色',
												},
												{
													field : 'parentName',
													title : '上级',
													templet : function(d) {
														return d.parentName == null ? "无"
																: d.parentName
													}
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
							table
									.on(
											'toolbar(test)',
											function(obj) {
												var checkStatus = table
														.checkStatus(obj.config.id);
												switch (obj.event) {
												case 'delete':
													if (!confirm("确认删除？"))
														return;
													var checkStatus = table
															.checkStatus('idTest');
													var vids = "";
													$.each(checkStatus.data,
															function(index,
																	elem) {
																vids += elem.id
																		+ ",";
															})
													alert(vids);
													ajax(
															"post",
															"/situOA/user/delete",
															{
																ids : vids
															}, "text",
															function(data) {
																select();
															})
													break;
												case 'export':
													window.location.href = "/situOA/user/export?name="
															+ vname
															+ "&userName="
															+ vuserName
															+ "&code="
															+ vcode
															+ "&roleCode="
															+ vroleCode
															+ "&parentCode="
															+ vparentCode;
													break;
												case 'download':
													window.location.href = "/situOA/user/export?stop=1";
													break;
												}
											});
							upload.render({
								elem : '#upload' //绑定元素
								,
								url : '/situOA/user/upload' //上传接口
								,
								accept : 'file',
								done : function(res) {
									select();
									//上传完毕回调
								},
								error : function() {
									//请求异常回调
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
				content : "/situOA/user/about",
				area : [ '500px', '550px' ]
			})
		})
	}
	function del(vid) {
		if (!confirm("确认删除该员工？"))
			return;
		ajax("post", "/situOA/user/deleteModel", {
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

	function upd(vid) {
		layui.use('layer', function() {
			var layer = layui.layer;
			layer.open({
				type : 2,
				content : "/situOA/user/about?id=" + vid,
				area : [ '500px', '550px' ]
			})
		})
	}
</script>
<script type="text/html" id="toolbarDemo">
  <div class="layui-btn-container">
 <button class="layui-btn layui-btn-normal layui-btn-xs layui-icon layui-icon-download-circle" lay-event="download">下载模版</button>
    <button class="layui-btn layui-btn-normal layui-btn-xs layui-icon layui-icon-form" lay-event="add" id="upload">批量导入</button>
    <button class="layui-btn layui-btn-danger layui-btn-xs layui-icon layui-icon-delete" lay-event="delete">删除选中</button>
 <button class="layui-btn layui-btn-normal layui-btn-xs layui-icon layui-icon-download-circle" lay-event="export">导出信息</button>
  </div>
</script>
</html>