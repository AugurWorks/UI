(function ( $ ) {
	
	var root = '';
 
    $.DocCollapse = function(id) {
        root = id;
    	setChildren($('#' + id));
    	$('#' + id).find('li').css('margin-left', '20px');
    	$('#' + id).find(':header').css('margin-left', '-20px');
    	$('#' + id).find('li').children(':header').parent().css('list-style-type', 'none');
    	$('#' + id).find(':header').css('margin-bottom', '15px');
    	$('#' + id).find(':header').css('margin-top', '15px');
    	$('#' + id).find(':header').hover(function() { $(this).css('cursor', 'pointer'); });
    	closeAll();
    	var hash = $(location).attr('hash');
    	if (hash) {
    		goToHash(hash)
    	}
        
        $(window).on('hashchange', function() {
        	var hash = $(location).attr('hash');
        	closeAll();
        	goToHash(hash);
        });
    };
    
    function closeAll() {
    	$('#' + root).children().children().find('ul').hide();
    	$('#' + root).children().children().find('li').hide();
    	$('#' + root).children().children().find('li').children(':header').parent().children().hide();
    	$('#' + root).children().children().find('.DCarrow').show();
    	$('#' + root).children().children('p').hide();
    	$('#' + root).find(':header').removeClass(root + 'Open');
    	$('#' + root).find(':header').addClass(root + 'Closed');
    	$('#' + root).find(':header').find('img').attr('src', '../images/doccollapse_right.jpg');
    }
    
    function expand(me) {
    	$(me).children(':header').find('img').attr('src', '../images/doccollapse_down.jpg');
		$(me).children(':header').addClass(root + 'Open');
		$(me).children(':header').removeClass(root + 'Closed');
    	$(me).children().show();
    	$(me).children('ul').children('li').show();
    	$(me).children('ul').children('li').children(':header').show();
    }
    
    function close(me) {
    	$(me).children(':header').find('img').attr('src', '../images/doccollapse_right.jpg');
		$(me).children(':header').addClass(root + 'Closed');
		$(me).children(':header').removeClass(root + 'Open');
    	$(me).children('ul').children('li').children(':header').hide();
    	$(me).children('ul').children('li').hide();
    	$(me).children().hide();
    	$(me).children(':header').show();
    }
    
    function setChildren(me) {
		$(me).children('li').children(':header').each(function() {
        	$(this).html('<img class="DCarrow" src="../images/doccollapse_right.jpg" style="height: ' + $(this).height() * .75 + 'px; padding-right: 5px; display: inline;" />' + $(this).html());
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
    
    function goToHash(hash) {
		expandUp($(hash))
		window.location.hash = hash;
    }
    
    function expandUp(me) {
    	if ($(me).hasClass(root + 'Closed')) {
	    	expand($(me).parent())
    		expandUp($(me).parent().parent().parent().children(':header'))
    	}
    }

}( jQuery ));