(function(target, name){

	var content = '<html><body>DROPDATA</body></html>';
	var blob = new Blob([content], {type: 'text/html'});
	blob.name = name + '.html';

	function fakeEvent(elem, type) {
		var rect = elem.getBoundingClientRect();
		var options = {
			bubbles: true,
			cancelable: true,
			view: window,
			clientX: rect.left + elem.offsetHeight / 2,
			clientY: rect.top + elem.offsetHeight / 2
		};
		var event;
		if (typeof window.MouseEvent == 'function') {
			event = new MouseEvent(type, options);
		} else {
			event = document.createEvent('MouseEvents');
			event.initMouseEvent(type, options.bubbles, options.cancelable, options.view, 0, 0, 0, options.clientX, options.clientY, false, false, false, false, 0, null);
		}
		event.dataTransfer = {
			files: [blob]
		};
		if (elem.dispatchEvent) {
			elem.dispatchEvent(event);
		} else if (elem.fireEvent) {
			elem.fireEvent('on' + type, event);
		}
	}

	fakeEvent(document.body, 'dragenter');
	fakeEvent(target, 'dragover');
	fakeEvent(target, 'drop');
	fakeEvent(document.body, 'dragleave');

})(arguments[0], arguments[1]);