(function ( $ ) {
 
    $.AWcollapse = function(id) {
        /*$(root).addClass('AWcollapse')
        $(root).each(function() {
        	$(this)[0].outerHTML = '<div><div class="arrow-right"></div>' + $(this)[0].outerHTML + '</div>';
        });
        $('.arrow-right').click(function() {
        	switchClass(this)
        })*/
    	setChildren($('#' + id))
    	$('#' + id).children().children().find('*').hide()
    	$('#' + id).children().children().find('.AWarrow').show()
    	$('#' + id).children().children('p').hide()
    };
    
    function expand(me) {
    	$(me).children(':header').find('img').attr('src', '/images/down.jpg')
		$(me).children(':header').addClass('AWcollapseOpen');
		$(me).children(':header').removeClass('AWcollapseClosed');
    	$(me).children('p').show();
    	$(me).children('ul').show();
    	$(me).children('ul').children('li').show();
    	$(me).children('ul').children('li').children(':header').show();
    }
    
    function close(me) {
    	$(me).children(':header').find('img').attr('src', '/images/right.jpg')
		$(me).children(':header').addClass('AWcollapseClosed');
		$(me).children(':header').removeClass('AWcollapseOpen');
    	$(me).children('ul').children('li').children(':header').hide();
    	$(me).children('ul').children('li').hide();
    	$(me).children('ul').hide();
    	$(me).children('p').hide();
    }
    
    function setChildren(me) {
		$(me).children('li').children(':header').each(function() {
        	$(this).html('<img class="AWarrow" src="/images/right.jpg" style="height: ' + $(this).height() * .75 + 'px;" />' + $(this).html());
			$(this).addClass('AWcollapseClosed');
    		$(this).click(function() {
    			if ($(this).hasClass('AWcollapseClosed')) {
	    			expand($(this).parent());
    			} else {
    				close($(this).parent())
    			}
    		});
			setChildren($(this).parent().children('ul'))
    	})
    }
    
    function switchClass(me) {
    	if ($(me).hasClass('arrow-right')) {
        	$(me).removeClass('arrow-right');
        	$(me).addClass('arrow-down');
    	} else {
        	$(me).removeClass('arrow-down');
        	$(me).addClass('arrow-right');
    	}
    }

}( jQuery ));