<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>员工主页</title>
<%@include file="/web/head.jsp"%>
<script type="text/javascript" src="/situOA/common/js/echarts.min.js"></script>
</head>
<body>
	<div id="main" style="width: 600px; height: 400px;"></div>
	<div id="main2" style="width: 600px; height: 400px;"></div>
</body>
<script type="text/javascript">
	var myChart = echarts.init(document.getElementById('main'));
	var myChart2 = echarts.init(document.getElementById('main2'));

	ajax("post", "/situOA/prod/selectAll", {
		a : "1"
	}, "json", function(data) {
		data = JSON.parse(data);
		var x = new Array();
		var y = new Array();
		var p = new Array();
		var i = 0;
		$.each(data, function(index, elem) {
			x[i] = elem.name;
			y[i] = elem.sum;
			p[i] = {
				value : elem.sum,
				name : elem.name
			}
			i++
		})

		// 指定图表的配置项和数据
		var option = {
			title : {
				text : '商品销售统计'
			},
			tooltip : {},
			legend : {
				data : [ '销量' ]
			},
			xAxis : {
				data : x
			},
			yAxis : {},
			series : [ {
				name : '销量',
				type : 'bar',
				data : y
			} ]
		};

		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option);

		myChart2.setOption({
			title : {
				text : '商品销售比例'
			},
			series : [ {
				name : '商品销售比例',
				type : 'pie',
				radius : '55%',
				data : p
			} ]
		})
	})
</script>
</html>