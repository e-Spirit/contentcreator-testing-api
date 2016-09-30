(function(element){

	var win = top.WE_API.Preview.getWindow();

	function fakeEvent(elem, type) {
		var rect = elem.getBoundingClientRect();
		var options = {
			bubbles: true,
			cancelable: true,
			view: win,
			clientX: rect.left + 4,
			clientY: rect.top + 4,
			ctrlKey: true // inline edit short cut
		};
		var event;
		if (typeof window.MouseEvent == 'function') {
			event = new MouseEvent(type, options);
		} else {
			event = win.document.createEvent('MouseEvents');
			event.initMouseEvent(type, options.bubbles, options.cancelable, options.view, 0, 0, 0, options.clientX, options.clientY, options.ctrlKey, false, false, false, 0, null);
		}
		if (elem.dispatchEvent) {
			elem.dispatchEvent(event);
		} else if (elem.fireEvent) {
			elem.fireEvent('on' + type, event);
		}
	}

	fakeEvent(element, 'mousedown');

})(arguments[0]);