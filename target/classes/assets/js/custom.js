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


$("#uploadForm").validate({
	rules:{
		pictureName: "required",
		pictureName: {
			required: true,
		}
	},
	messages: {
		pictureName: {
			required: "Please select picture"
		}
	}
});

function send(){
	$("#uploadForm").ajaxForm(function(){
		beforeSubmit: uploadingMessage();
		success: showResponse
		clearForm: true;
	});
}
function uploadingMessage(){
	document.getElementById("uploading").style.display='block';
}
function showResponse(responseText, statusText, xhr, $form)  { 
// for normal html responses, the first argument to the success callback 
// is the XMLHttpRequest object's responseText property 

// if the ajaxForm method was passed an Options Object with the dataType 
// property set to 'xml' then the first argument to the success callback 
// is the XMLHttpRequest object's responseXML property 

// if the ajaxForm method was passed an Options Object with the dataType 
// property set to 'json' then the first argument to the success callback 
// is the json data object returned by the server 

alert('status: ' + statusText + '\n\nresponseText: \n' + responseText + 
    '\n\nThe output div should have already been updated with the responseText.'); 
} 