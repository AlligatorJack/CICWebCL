/*
function cliKeyUp(elem) { // worx!!! fuer input

		    var elText = elem.value;
			

			elText = encodeURIComponent(elText);
		    elText = elText.replace(/%EF%BB%BF/gm, "") // TODO evtl loeschen
		    elText = elText.replace(/%C2%A0/gm, "")		// TODO evtl loeschen

		    
			if (elText.length == 0) {
				$('#highlighted').text("");
			}
			else {
				console.log(elText + getSelectionStart(elem));
				jsRoutes.controllers.Application.request(elText+"$$$"+getSelectionStart(elem)).ajax({
					success : function(report) {
						
            console.log(report);
						$("#completions").text("")
		        		$.each(report.completions, function(i, c) { 
		        			$("#completions").append(c)
						
							console.log("coloredresult:" + report.colored);
						});

						$("#highlighted").html(report.colored);
		    		}
				});
			}
};
*/
/*
* @author Florian
*/
function cliKeyUp(elem, key) { // fuer div

		    // var elText = document.getElementById("cli").textContent;
		    var elText = document.getElementById("cli").value;
		    var keyCode = key?key.which:event.key.keyCode;

		    var oldCurserPos = getCurser();

			elText = encodeURIComponent(elText);
		    elText = elText.replace(/%EF%BB%BF/gm, "");
		    elText = elText.replace(/%C2%A0/gm, "");

		    
			if (elText.length == 0) {
				$('#cli').value = "";
				$('#highlighted').text("");
				$('#errors').text("");
				document.getElementById("completions").options.length = 0;

			}
			else {
				if((keyCode == 38) || (keyCode == 40)) {
					posInCLI = getCurser();
					document.getElementById("completions").focus();
				}

				else if(keyCode == 13) {
					jsRoutes.controllers.Application.execute(elText).ajax({
						success : function(result) {
							$('#result').text(result);
						}
					});

				}
				else {
					jsRoutes.controllers.Application.request(elText+"$$$"+getCurser()).ajax({
						success : function(report) {

							document.getElementById("completions").options.length = 0;
							$('#errors').text("");

							$.each(report.completions, function(i, c) { 
								document.getElementById("completions").options[document.getElementById("completions").options.length] = new Option(c, i);
							});

							$.each(report.errors, function(i, c) { 
								if(i % 2 == 0)
		          					$("#errors").append("<li>" + c + "</li>")
		          				else
		          					$("#errors").append("&nbsp;&nbsp;&nbsp;"+c)
							});

							$('#highlighted').html(report.colored);
						}
					});
				}
			}
};


function setCurser(id, pos) {		// works for div!!

		var content = document.getElementById(id);
    	content.focus();
  		var sel = window.getSelection();
  		sel.collapse(content.firstChild, pos);
};


function getCurser() {		// works for div!!

    	var userSelection;
    	if (window.getSelection) {
    		userSelection = window.getSelection();
    	}
    	var start = userSelection.anchorOffset;
    	var end = userSelection.focusOffset;

    	return start;
};

function getSelectionStart(o) {		// works!! for input
	if (o.createTextRange) {
		var r = document.selection.createRange().duplicate()
		r.moveEnd('character', o.value.length)
		if (r.text == '') return o.value.length
		return o.value.lastIndexOf(r.text)
	} else return o.selectionStart

}


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

/*function getCaretPosition(editableDiv) {	// fuer div
    var caretPos = 0, containerEl = null, sel, range;
    if (window.getSelection) {
        sel = window.getSelection();
        if (sel.rangeCount) {
            range = sel.getRangeAt(0);
            if (range.commonAncestorContainer.parentNode == editableDiv) {
                caretPos = range.endOffset;
            }
        }
    } else if (document.selection && document.selection.createRange) {
        range = document.selection.createRange();
        if (range.parentElement() == editableDiv) {
            var tempEl = document.createElement("span");
            editableDiv.insertBefore(tempEl, editableDiv.firstChild);
            var tempRange = range.duplicate();
            tempRange.moveToElementText(tempEl);
            tempRange.setEndPoint("EndToEnd", range);
            caretPos = tempRange.text.length;
        }
    }
    return caretPos;
}
function setCaretPosition(elemId, caretPos) {		// fuer div
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
*/