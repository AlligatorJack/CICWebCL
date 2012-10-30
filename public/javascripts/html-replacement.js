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
						
						$("#completions").text("")
		        		$.each(report.completions, function(i, c) { 
		        			$("#completions").append(c)
						
							$("#highlighted").html(report.colored);
							console.log("coloredresult:" + report.colored);
						});


		    		}
				});
			}
};
*/


function cliKeyUp(elem, key) { // fuer div

		    var elText = document.getElementById("cli").textContent;
		    var keyCode = key?key.which:event.key.keyCode;

		    var oldCurserPos = getCurser();


			elText = encodeURIComponent(elText);
		    elText = elText.replace(/%EF%BB%BF/gm, "")
		    elText = elText.replace(/%C2%A0/gm, "")

		    
			if (elText.length == 0) {
				$('#cli').text("");
				$('#highlighted').text("");
				$('#errors').text("");
				document.getElementById("completions").options.length = 0;

			}
			else {
				if(keyCode != 13) {


					jsRoutes.controllers.Application.request(elText+"$$$"+getCurser()).ajax({
						success : function(report) {
							
							document.getElementById("completions").options.length = 0;

							$.each(report.completions, function(i, c) { 
								document.getElementById("completions").options[document.getElementById("completions").options.length] = new Option(c, i);
							});

							$.each(report.errors, function(i, c) { 
								$('#errors').html($('#errors').text + c);
							});

							$('#highlighted').html(report.colored);
						}
					});


				}
				else {
					jsRoutes.controllers.Application.apply(elText).ajax({
						success : function(report) {
							
							
							// $.each(report.errors, function(i, c) { 
			    //     			$('#errors').html($('#errors').text + c);
							// });

							// $('#result').html(report.result);
												}
					});
			}
		}

};


function setCurser(pos) {		// works for div!!

		var content = document.getElementById('cli');
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
};