jQuery.fn.extend({

})

function ajax(type, url, tempdata, datatype, fun) {
	$.ajax({
		type : type,
		url : url,
		data : tempdata,
		datatype : datatype,
		success : fun
	})
}