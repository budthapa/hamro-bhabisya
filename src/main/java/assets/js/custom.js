$("#picture").bind('change', function(){
		var fileSize=this.files[0].size;
		if((fileSize/1024)>200){
			fileSizeAlert();		
		}
});
	
function fileSizeAlert(){
    $.alert({
        title: 'Error!',
        icon: 'glyphicon glyphicon-warning-sign',
        content: 'This file is too big to upload. Maximum file size 200Kb',
        confirmButtonClass: 'btn-info',
        confirm: function(){}
    });
}