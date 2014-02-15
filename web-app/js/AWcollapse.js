(function ( $ ) {
	
	var root = '';
 
    $.AWcollapse = function(id) {
        root = id;
    	setChildren($('#' + id));
    	$('#' + id).find('li').css('list-style-type', 'none');
    	$('#' + id).find(':header').css('margin-bottom', '15px');
    	$('#' + id).find(':header').css('margin-top', '15px');
    	$('#' + id).children().children().find('*').hide();
    	$('#' + id).children().children().find('.AWarrow').show();
    	$('#' + id).children().children('p').hide();
    	var hash = $(location).attr('hash');
    	if (hash) {
    		expandUp($(hash))
    		window.location.hash = hash;
    	}
    };
    
    function expand(me) {
    	$(me).children(':header').find('img').attr('src', '/images/down.jpg');
		$(me).children(':header').addClass(root + 'Open');
		$(me).children(':header').removeClass(root + 'Closed');
    	$(me).children().show();
    	$(me).children('ul').children('li').show();
    	$(me).children('ul').children('li').children(':header').show();
    }
    
    function close(me) {
    	$(me).children(':header').find('img').attr('src', '/images/right.jpg');
		$(me).children(':header').addClass(root + 'Closed');
		$(me).children(':header').removeClass(root + 'Open');
    	$(me).children('ul').children('li').children(':header').hide();
    	$(me).children('ul').children('li').hide();
    	$(me).children().hide();
    	$(me).children(':header').show();
    }
    
    function setChildren(me) {
		$(me).children('li').children(':header').each(function() {
        	$(this).html('<img class="AWarrow" src="/images/right.jpg" style="height: ' + $(this).height() * .75 + 'px; padding-right: 5px;" />' + $(this).html());
			$(this).addClass(root + 'Closed');
    		$(this).click(function() {
    			if ($(this).hasClass(root + 'Closed')) {
	    			expand($(this).parent());
    			} else {
    				close($(this).parent())
    			}
    		});
			setChildren($(this).parent().children('ul'))
    	})
    }
    
    function expandUp(me) {
    	if ($(me).hasClass(root + 'Closed')) {
	    	expand($(me).parent())
    		expandUp($(me).parent().parent().parent().children(':header'))
    	}
    }

}( jQuery ));