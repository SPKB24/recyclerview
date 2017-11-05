
function someFunc() {
	var XHR = new XMLHttpRequest();
	var form = document.getElementById("some").value;
	
	XHR.open('GET', "http://34.227.56.233/text_to_material?text="+ form, true);
	
	XHR.send();
	
	XHR.addEventListener("readystatechange", processRequest, false)
	function processRequest(e) {
		console.log('blah')
		if (XHR.readyState == 4 && XHR.status == 200) {
			
			var response = JSON.parse(XHR.responseText);
			if (response.compostable){
				window.alert("Compostable");
			}
			else if (response.recyclable){
				window.alert("Recyclable");
			}
			else{
				window.alert("Trash");
			}

		}
	}

}	
