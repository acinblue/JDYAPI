function submit() {
	var json = {
			"warehouse":$("#warehouse").val(),
			"warehouseno":Number($("#warehouseno").val()),
			"warehousetext":$("#warehousetext").val()
		};
	$.ajax({
		url:'ware/ware_add',
		type:'POST',
		contentType : "application/json;charset=utf-8",
	    dataType : "json",
	    data: JSON.stringify(json),
		timeout:2000,
		async:false,
		success:function(data){
			alert(data);
		}
	});
}

function lose() {
	alert($("#warehouse").val());
}