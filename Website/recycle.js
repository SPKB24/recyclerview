
function someFunc() {
	var XHR = new XMLHttpRequest();
	var form = document.getElementById("some").value;
    var resultDiv = document.getElementById("result");	
    resultDiv.style.visibility='hidden';
	XHR.open('GET', "http://wecyclr.net/text_to_material?text="+ form, true);
	
	XHR.send();
	
	XHR.addEventListener("readystatechange", processRequest, false)
	function processRequest(e) {
		console.log('blah')
		if (XHR.readyState == 4 && XHR.status == 200) {
			
			var response = JSON.parse(XHR.responseText);
			if (response.compostable){
                resultDiv.innerHTML = "Compostable!";
                resultDiv.style.color = "#4EE08D";
			}
			else if (response.recyclable){
				resultDiv.innerHTML = "Recyclable!";
                resultDiv.style.color = "#4EE08D";
			}
			else{
				resultDiv.innerHTML = "Garbage";
                resultDiv.style.color = "#E63946";
			}
            resultDiv.style.visibility='visible';
		}
	}

}	
