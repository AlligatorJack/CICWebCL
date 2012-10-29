/*
function sleep(ms)
	{
		var dt = new Date();
		dt.setTime(dt.getTime() + ms);
		while (new Date().getTime() < dt.getTime());
	}
*/
var caretPos = 0;
function cliKeyUp(elem) {
		    // var savedSel = rangy.saveSelection();
		    caretPos = caretPos + 1;
		    console.log(caretPos);

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
						
						// $("#completions").empty()
		        		// $.each(report.completions, function(i, c) { 
		        			// $("#completions").append("<li>" + c + "</li>")
						
							$("#highlighted").html(report.colored);



							/*
							var elem = document.getElementById("editable");
							var range = document.createRange();
							var sel = window.getSelection();
							range.setStart(elem.childNodes[2], 5);
							range.collapse(true);
							sel.removeAllRanges();
							sel.addRange(range);
							*/



							setCaretPosition("cli", 2);
							// el.focus();
							// el.setPosition(3);
				        	// rangy.restoreSelection(savedSel);
		       		 	// });
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
