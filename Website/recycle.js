
function someFunc() {
	var XHR = new XMLHttpRequest();
	var form = document.getElementById("some").value;
    var resultDiv = document.getElementById("result");	
	XHR.open('GET', "http://34.227.56.233/text_to_material?text="+ form, true);
	
	XHR.send();
	
	XHR.addEventListener("readystatechange", processRequest, false)
	function processRequest(e) {
		console.log('blah')
		if (XHR.readyState == 4 && XHR.status == 200) {
			
			var response = JSON.parse(XHR.responseText);
			if (response.compostable){
                resultDiv.innerHTML = "Compostable!"
			}
			else if (response.recyclable){
				resultDiv.innerHTML = "Recyclable!"
			}
			else{
				resultDiv.innerHTML = "Garbage"
			}
            resultDiv.style.visibility='visible';
		}
	}

}	
