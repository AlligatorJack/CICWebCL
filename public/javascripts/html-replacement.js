
function sleep(ms)
	{
		var dt = new Date();
		dt.setTime(dt.getTime() + ms);
		while (new Date().getTime() < dt.getTime());
	}


function formatText(el) {
		    var savedSel = rangy.saveSelection();

		    var text = $(el).text();
		    // text = unescape(text);
		    
		    
			if (text.length == 0) {
				newText = "leer";
			}
			else {
			    jsRoutes.controllers.Application.colorizeString(encodeURIComponent(text)).ajax({
			    		success: function(data) { text = text.replace(data) }//document.getElementById('cli').innerHTML = data; }
				});
			};
			
			


			// alert("hi");

		    // text = text.replace(/Daria/gm, "Florian");
		    // text = text.replace(/-from/gm, "-<span contenteditable=\"false\" style=\"color:red\">from</span>");
		    // text = text.replace(/this/gm, "<i>this</i>");



		    // text = text.replace("hi", newText)
		    

		    el.innerHTML = text;

		    // Restore the original selection 
		    rangy.restoreSelection(savedSel);
};

var keyTimer = null, keyDelay = 5;

function cliOnKeyUp(){
	document.getElementById('cli').onkeyup = function() {
		if (keyTimer) {
			window.clearTimeout(keyTimer);
		}
		keyTimer = window.setTimeout(function() {
			keyTimer = null;
			formatText('#cli');
		}, keyDelay);
	};
};

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

