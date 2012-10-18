function formatText(el) {
		    var savedSel = rangy.saveSelection();

		    var text = el.innerHTML.replace(/this/g, "<i>this</i>");
		    text = text.replace("Florian", "Daria");
		    text = text.replace(/-from/gm, "-<span contenteditable=\"false\" style=\"color:red\">from</span>");
		    // text = text.replace(/this/gm, "<i>this</i>");

		    el.innerHTML = text;

		    // Restore the original selection 
		    rangy.restoreSelection(savedSel);
		};

		var keyTimer = null, keyDelay = 10;
		function StartFunction() {
			document.getElementById('cli').onkeyup = function() {
				if (keyTimer) {
					window.clearTimeout(keyTimer);
				}
				keyTimer = window.setTimeout(function() {
					keyTimer = null;
					formatText(document.getElementById('cli'));
				}, keyDelay);
			};
		};
window.onload = StartFunction;