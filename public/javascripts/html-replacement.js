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
				// $.ajax({
				//   url: "/ajax/",
 			// 	 context: document.body
				//  }).done(function() {  
 			// 	 $(this).addClass("done");
				// });
		
			 //    jsRoutes.controllers.Application.colorizeString(elText).ajax({
			 //    		contentType: "charset=utf-8",
			 //    		success: function(data) {
			    		 	
			 //    			// document.getElementById('cli').innerHTML = data;
			 //    			document.getElementById('suggestions').innerHTML = data;
			 //    			rangy.restoreSelection(savedSel);

			 //    		}//document.getElementById('cli').innerHTML = data; }
				// });
			    	
					var url = "/ajax/" + elText;
					
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
			};

			
			


			// alert("hi");

		    // text = text.replace(/Daria/gm, "Florian");
		    // text = text.replace(/-from/gm, "-<span contenteditable=\"false\" style=\"color:red\">from</span>");
		    // text = text.replace(/this/gm, "<i>this</i>");	    
		    
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
