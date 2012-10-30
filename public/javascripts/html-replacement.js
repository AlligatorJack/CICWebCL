function cliKeyUp(elem) { // fuer div

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

/*
function cliKeyUp(elem) { // fuer div

		    var el = document.getElementById("cli");

		    var elText = el.textContent;
		

			elText = encodeURIComponent(elText);
		    elText = elText.replace(/%EF%BB%BF/gm, "")
		    elText = elText.replace(/%C2%A0/gm, "")

		    
			if (elText.length == 0) {
				$('#highlighted').text("");
			}
			else {
				jsRoutes.controllers.Application.request(elText+"$$$"+getSelectionStart(elem)).ajax({
					success : function(report) {
						
						// $("#completions").empty()
		        		// $.each(report.completions, function(i, c) { 
		        			// $("#completions").append("<li>" + c + "</li>")
						
							$("#highlighted").html(report.colored);


		    		}
				});
			}
					// var url = "/" + elText;
					
					// $.ajax({
				 //  		url: url,
					// }).done(function( data ) {
				 //  		console.log(data);
				 //  		// console.log($('#suggestions').elText());
				 //  		// document.getElementById("suggestions").innerHTML = data;
				  		
				 //  		// elText = data;                 
				 //  		$('#highlighted').html(data);

				 //  		rangy.restoreSelection(savedSel);

					});

};
*/

function getSelectionStart(o) {
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
