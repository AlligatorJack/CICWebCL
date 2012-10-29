/*
function sleep(ms)
	{
		var dt = new Date();
		dt.setTime(dt.getTime() + ms);
		while (new Date().getTime() < dt.getTime());
	}
*/
function formatText(elem) {
		    var savedSel = rangy.saveSelection();

		    var el = document.getElementById("cli");

		    var elText = el.textContent;
		

			elText = encodeURIComponent(elText);
		    elText = elText.replace(/%EF%BB%BF/gm, "")
		    elText = elText.replace(/%C2%A0/gm, "")

		    
			if (elText.length == 0) {
				$('#highlighted').text("");
			}
			else {

				jsRoutes.controllers.Application.request(elText).ajax({
					success : function(report) {
						$("#colored").html(report.colored);
						$("#completions").empty()
						$.each(report.completions, function(i, c) {
							$("#completions").append("<li>" + c + "</li>")
						});
					}
				});
			}
			    	
					/*var url = "/" + elText;
					
					$.ajax({
				  		url: url,
					}).done(function( data ) {
				  		console.log(data);
				  		// console.log($('#suggestions').elText());
				  		// document.getElementById("suggestions").innerHTML = data;
				  		
				  		// elText = data;                 
				  		$('#highlighted').html(data);

				  		rangy.restoreSelection(savedSel);

					});
*/
};



/*var keyTimer = null, keyDelay = 5;

function cliOnKeyUp(){
	document.getElementById('cli').onkeyup = function() {
		if (keyTimer) {
			window.clearTimeout(keyTimer);
		}
		keyTimer = window.setTimeout(function() {
			keyTimer = null;
			formatText($('#cli'));
		}, keyDelay);
	};
};*/

function setCaretPosition(elemId, caretPos) {
			var elem = document.getElementById(elemId);

			if(elem != null) {
				if(elem.createTextRange) {
					var range = elem.createTextRange();
					range.move('character', caretPos);
					range.select();
				}
				else {
					if(elem.selectionStart) {
						elem.focus();
						elem.setSelectionRange(caretPos, caretPos);
					}
					else
						elem.focus();
				}
			}
};
