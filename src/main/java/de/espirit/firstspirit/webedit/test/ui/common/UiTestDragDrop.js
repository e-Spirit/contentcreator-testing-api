(function(action, target){

	var win = top.WE_API.Preview.getWindow();

	function fakeEvent(elem, type) {
		var rect = elem.getBoundingClientRect();
		var options = {
			bubbles: true,
			cancelable: true,
			view: win,
			clientX: rect.left + 1,
			// Feature #173480 CC: bodyuebergreifender Drag & Drop Support
			//  - Determination of the target section index uses mouse position since the posting mentioned above
			//  - The given target element is expected to be a section after the contextual drag element
			//  => The test case simulates an operation in which the section will be moved behind the given drop target
			clientY: rect.top + 1 + elem.offsetHeight / 2
		};
		var event;
		if (typeof window.MouseEvent == 'function') {
			event = new MouseEvent(type, options);
		} else {
			event = win.document.createEvent('MouseEvents');
			event.initMouseEvent(type, options.bubbles, options.cancelable, options.view, 0, 0, 0, options.clientX, options.clientY, false, false, false, false, 0, null);
		}
		if (elem.dispatchEvent) {
			elem.dispatchEvent(event);
		} else if (elem.fireEvent) {
			elem.fireEvent('on' + type, event);
		}
	}

	fakeEvent(action, 'dragstart');
	fakeEvent(win.document.body, 'dragenter');
	fakeEvent(target, 'dragover');
	fakeEvent(target, 'drop');
	fakeEvent(win.document.body, 'dragleave');

})(arguments[0], arguments[1]);