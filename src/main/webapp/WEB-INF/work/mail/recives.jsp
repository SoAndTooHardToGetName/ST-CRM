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

	<table class="layui-hide" id="demo" lay-filter="test"></table>

</body>
<script type="text/javascript">
	$('#sel').click(select);
	select();
	function select() {
		layui.use('table', function() {
			var table = layui.table;
			table.render({
				elem : '#demo',
				method : "post",
				url : '/situOA/mail/recives',
				page : true,
				cols : [ [ //表头
				{
					type : "numbers",
					title : 'ID',
					sort : true,
				}, {
					field : 'titile',
					title : '主题',
				}, {
					field : 'from',
					title : '发件人',
					sort : true,
				}, {
					field : 'content',
					title : '内容',
				} ] ]
			});
		})
	}
</script>
</html>