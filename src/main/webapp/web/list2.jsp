<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@include file="/web/head.jsp"%>
<title>Insert title here</title>
</head>
<body>
	<div id="test1"></div>

</body>
<script type="text/javascript">
	var roleCode = "${roleCode}";
	layui.use('tree', function() {
		var tree = layui.tree;
		var temp = new Array();
		var i = 0;
		ajax("post", "/situOA/menu/selectAll", {
			a : 1
		}, "json", function(data) {
			data = JSON.parse(data)
			alert(data)
			$.each(data, function(index, elem) {
				temp[i] = {
					title : elem.name,
					id : elem.code
				}
				i++;
			})
			tree.render({
				elem : '#test1',
				data : temp,
				showCheckbox : true //是否显示复选框
				,
				id : 'demoId',
				isJump : true //是否允许点击节点时弹出新窗口跳转
				,
				oncheck : function(obj) {
					console.log(obj.data); //得到当前点击的节点数据
					console.log(obj.checked); //得到当前节点的展开状态：open、close、normal
					console.log(obj.elem); //得到当前节点元素

				}
			});
			ajax("post", "/situOA/access/select", {
				roleCode : vroleCode
			}, "json", function(data) {
				$.each(data, function(index, elem) {
					tree.setChecked("demoId", elem.menuCode)
				})
			})
		})
		//渲染
	});
</script>
</html>