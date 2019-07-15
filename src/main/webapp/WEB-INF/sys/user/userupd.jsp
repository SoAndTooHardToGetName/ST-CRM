<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>修改</title>
<%@include file="/web/head.jsp"%>
</head>
<body>
	<fieldset class="layui-elem-field" style="margin: 20px; padding: 15px;">
		<legend>${title}</legend>
		<form class="layui-form">
			<div class="layui-form-item">
				<label class="layui-form-label">编号：</label>
				<div class="layui-input-inline">
					<input type="text" name="code" required lay-verify="required"
						value="${temp.code}" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">用户名：</label>
				<div class="layui-input-inline">
					<input type="text" name="userName" required lay-verify="required"
						value="${temp.userName}" placeholder="请输入账号" autocomplete="off"
						class="layui-input">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">登录密码：</label>
				<div class="layui-input-inline">
					<input type="password" name="userPass" required
						lay-verify="required" value="" autocomplete="off"
						class="layui-input">
				</div>
			</div>


			<div class="layui-form-item">
				<label class="layui-form-label">姓名：</label>
				<div class="layui-input-inline">
					<input type="text" name="name" required lay-verify="required"
						value="${temp.name}" placeholder="请输入姓名" autocomplete="off"
						class="layui-input">
				</div>
			</div>


			<div class="layui-form-item">
				<label class="layui-form-label">职位：</label>
				<div class="layui-input-inline">
					<select name="roleCode" lay-filter="parent">
						<option value=""></option>
						<c:forEach items="${roles}" var="s">
							<option value="${s.code}">${s.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">上级：</label>
				<div class="layui-input-inline">
					<select name="parentCode">
					</select>
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<div class="layui-input-inline">
					<input type="button" class="layui-btn" id="sub" value="确定" /> <input
						type="button" class="layui-btn" value="取消" id="cancel" />
				</div>
			</div>
			<input type="hidden" name="id" value="${temp.id}">
		</form>
	</fieldset>

</body>
<script type="text/javascript">
	layui.use('form', function() {
		var form = layui.form;
		form.on('select(parent)', function(temp) {
			ajax('post', '/situOA/user/selectAll', {
				roleCode : temp.value
			}, 'json', function(data) {
				var item = '<option value=""></option>';
				$.each(data, function(index, elem) {
					item += '<option value="'+elem.code+'">' + elem.name
							+ '</option>';
				})
				$('select[name="parentCode"]').html(item);
				form.render();
			})
		})
	});

	$('#sub').click(sub);
	function sub() {
		var temp = $('form').serialize();
		ajax('post', '/situOA/user/submit', temp, 'text', function(data) {
			if (data == "1")
				layui.use('layer', function() {
					var layer = layui.layer;
					layer.msg('操作成功');
					parent.select();
					window.setTimeout(close, 1500);
				})
		})
	}

	$('#cancel').click(close);
	function close() {
		parent.layer.closeAll();
	}

	init();
	function init() {
		$('select[name="roleCode"]').val('${temp.roleCode}')
		var elem = $('input[name="code"]');
		if (elem.val() != '')
			elem.prop('readonly', true);
	}
</script>
</html>