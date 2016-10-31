(function() {
	if (!window.WebSocket) {
		console.error("WebSocket not supported by this browser");
	} else {

		var server = {
			connect : function() {
				var location = 'ws://' + window.location.host
						+ '/${web.context}/main/webSocket';
				console.log(location);
				this.ws = new WebSocket(location);
				this.ws.onopen = function() {
					this.send('websockets are open for communications!');
				};
				this.ws.onmessage = function(m) {
					if (m.data) {
						console.log(m.data);
						$$('main_menu').clearAll();
						$$('main_menu').load('main/menu');

					}
				};
				this.ws.onclose = function(m) {
					console.log('ws closed');
					server.ws=null;
				};
			},

			send : function(text) {
				if (text != null && text.length > 0)
					if (this.ws)
						this.ws.send(message);
			}
		};
		server.connect();
	}
})();