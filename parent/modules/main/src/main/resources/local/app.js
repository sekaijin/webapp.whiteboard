(function() {
	webix.ui({
		id : "layout",
		rows : [ {
			view : "toolbar",
			id : 'main_toolbar',
			margin : 50,
			height : 36,
			cols : [ {
				view : "menu",
				id : 'main_menu',
				url : "main/menu",
				datatype : "json",
				on:{
				    onMenuItemClick:function(id, e, n){
				        webix.ajax(this.getMenuItem(id).href, function(text){
				            webix.message(text); //show server side response
				        });
				        webix.html.preventEvent(e);
				        return webix.html.stopEvent(e);
				    }
				}
			}/*, {
				view : "button",
				id : 'bouton_test',
				type : "htmlbutton",
				css : "icon_btn",
				label : 'test',
				click : function() {
					$$('main_menu').clearAll();
					$$('main_menu').load('main/menu');
				}
			}*/ ]
		} ]
	});
})();