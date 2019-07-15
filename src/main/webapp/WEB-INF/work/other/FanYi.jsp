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
		<legend>百科搜索</legend>
		<form class="layui-form">
			<div class="layui-form-item">
				<label class="layui-form-label">搜索内容：</label>
				<div class="layui-input-block">
					<input class="layui-input" name="item" />
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<div class="layui-input-inline">
					<button type="button" id="sel" class="layui-btn layui-btn-normal">确定</button>
					<input type="reset" class="layui-btn layui-btn-normal" value="重置" />
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">百科结果：</label>
				<div class="layui-input-block">
					<label id="demo"></label>
				</div>
			</div>
		</form>
	</fieldset>

</body>
<script type="text/javascript">
	layui.use('form', function() {
		var form = layui.form;
	});

	$("#sel").click(trans)
	function trans() {
		ajax("post", "/situOA/fanyi/test", {
			item : $('input[name="item"]').val()
		}, "text", function(data) {
			$('#demo').html(data);
		})
	}
</script>
</html>