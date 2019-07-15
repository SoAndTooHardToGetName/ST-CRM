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
				<label class="layui-form-label">客户：</label>
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
				<label class="layui-form-label">商品：</label>
				<div class="layui-input-inline">
					<select name="prodCode">
						<option value=""></option>
						<c:forEach items="${prods}" var="s">
							<option value="${s.code}">${s.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>


			<div class="layui-form-item">
				<label class="layui-form-label">数量：</label>
				<div class="layui-input-inline">
					<input type="text" name="count" class="layui-input">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<div class="layui-input-inline">
					<input type="button" class="layui-btn" id="sub" value="确定" /> <input
						type="button" class="layui-btn" value="取消" id="cancel" />
				</div>
			</div>
		</form>
	</fieldset>

</body>
<script type="text/javascript">
	layui.use('form', function() {
		var form = layui.form;
	});

	$('#sub').click(sub);
	function sub() {
		var temp = $('form').serialize();
		ajax('post', '/situOA/order/submit', temp, 'text', function(data) {
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
</script>
</html>