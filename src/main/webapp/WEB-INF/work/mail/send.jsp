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
		<legend>写邮件</legend>
		<form class="layui-form" action="/situOA/mail/send" method="post">
			<div class="layui-form-item">
				<label class="layui-form-label">收件人：</label>
				<div class="layui-input-inline">
					<select name="custCode">
						<option value=""></option>
						<c:forEach items="${custs}" var="s">
							<option value="${s.code}">${s.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">主题：</label>
				<div class="layui-input-inline">
					<input type="text" name="status" class="layui-input" value="">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">定时发送：</label>
				<div class="layui-input-inline">
					<input type="text" name="time" class="layui-input" value=""
						id="time">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">内容：</label>
				<div class="layui-input-block">
					<textarea id="demo" style="display: none;" name="content"></textarea>
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<div class="layui-input-inline">
					<input type="submit" class="layui-btn layui-btn-normal" value="确定" />
					<input type="reset" class="layui-btn layui-btn-normal" value="重置" />
				</div>
			</div>
		</form>
	</fieldset>

</body>
<script type="text/javascript">
	layui.use([ 'form', 'laydate' ], function() {
		var form = layui.form;
		var laydate = layui.laydate;
		laydate.render({
			elem : '#time',
			type : 'datetime'
		})
	});

	layui.use('layedit', function() {
		var layedit = layui.layedit;
		layedit.build('demo'); //建立编辑器
	});
</script>
</html>