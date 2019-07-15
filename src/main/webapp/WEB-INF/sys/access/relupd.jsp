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
		<legend>用户维护--修改信息</legend>
		<form class="layui-form layui-form-pane" lay-filter="updrel"
			method="post">
			<div class="layui-form-item" pane>


				<label class="layui-form-label">职位名称：</label>
				<div class="layui-input-inline">
					<select name="roleCode">
						<option value=""></option>
						<c:forEach items="${roles}" var="s">
							<option value="${s.code}">${s.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="layui-form-item" pane>
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
				<input type="button" class="layui-btn" id="sub" value="确定" /> <input
					type="button" class="layui-btn" value="取消" id="cancel" />
			</div>

		</form>
	</fieldset>



</body>
<script type="text/javascript">
	layui.use('form', function() {
		var form = layui.form;
	})

	$('#sub').click(sub);
	function sub() {
		var temp = $('form').serialize();
		ajax('post', '/situOA/access/add', temp, 'text', function(data) {
			layui.use('layer', function() {
				var layer = layui.layer;
				if (data == "1") {
					layer.msg('操作成功');
					parent.select();
					window.setTimeout(close, 1500);
				} else
					layer.msg('该权限设置已存在')
			})
		})
	}

	$('#cancel').click(close);
	function close() {
		parent.layer.closeAll();
	}
</script>
</html>