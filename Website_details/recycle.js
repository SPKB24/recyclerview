/*window.addEventListener("load", function () {
  function sendData() {
    var XHR = new XMLHttpRequest();

    // Bind the FormData object and the form element
    var FD = new FormData(form);

    // Define what happens on successful data submission
    XHR.addEventListener("load", function(event) {
      alert(event.target.responseText);
    });

    // Define what happens in case of error
    XHR.addEventListener("error", function(event) {
      alert('Oops! Something goes wrong.');
    });
	var form = document.getElementById("some").innerHTML;
    // Set up our request
    XHR.open('GET', "http://34.227.56.233/text_to_material?text="+ form , true);

    // The data sent is what the user provided in the form
    XHR.send();
  }
 
  // Access the form element...
  // var form = document.getElementById("some");

  // ...and take over its submit event.
  form.addEventListener("submit", function (event) {
    event.preventDefault();

    sendData();
  });
});*/
console.log('fdfddfd');

function someFunc() {
	var XHR = new XMLHttpRequest();


	 // var FD = new FormData(form);


	var form = document.getElementById("some").innerHTML;
	console.log(form)
	XHR.open('GET', "http://34.227.56.233/text_to_material?text="+ form , true);
	console.log("http://34.227.56.233/text_to_material?text="+ form)
	XHR.send();
	XHR.onreadystatechange = processRequest;

	function processRequest(e) {
		if (XHR.readyState == 4 && XHR.status == 200) {
			var response = JSON.parse(XHR.responseText);
			alert(response.ip);
		}
	}
	return false;
}	
// The data sent is what the user provided in the form
//XHR.send();