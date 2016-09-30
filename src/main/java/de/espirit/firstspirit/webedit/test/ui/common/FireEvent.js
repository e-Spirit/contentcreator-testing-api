(function(type, element){
	var options = {
		bubbles: true,
		cancelable: true
	};
	var event;
	if (typeof window.Event == 'function') {
		event = new Event(type, options);
	} else {
		event = document.createEvent("HTMLEvents");
		event.initEvent(type, options.bubbles, options.cancelable);
	}
	if (element.dispatchEvent) {
		element.dispatchEvent(event);
	} else if (element.fireEvent) {
		element.fireEvent('on' + type, event);
	}
})(arguments[0], arguments[1]);