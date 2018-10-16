function submit() {
	$.ajax({
		url:'ware/ware_add',
		type:'POST',
		dataType:'json',
		data:{
			"warehouse":$("#warehouse").value(),
			"warehouseno":Number($("#warehouseno").value()),
			"warehousetext":$("#warehousetext").value()
		},
		timeout:2000,
		async:false,
		success:function(data){
			alert(data);
		}
	});
}