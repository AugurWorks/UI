(function ($) {
	
	$.docCollapse = {
			
		defaults: {
			margin:		'20',
			color:		'black'
		},
	
		init: function(me, options) {
			opts = $.extend({}, $.docCollapse.defaults, options);
			svg = '<path d="m110,338c0,-48 4,-56 42,-95l42,-43l-42,-43c-38,-39 -42,-47 -42,-95c0,-29 3,-52 8,-52c4,0 50,43 102,95l95,95l-95,95c-52,52 -98,95 -102,95c-5,0 -8,-23 -8,-52z"/></g></svg></defs><g><use xlink:href="#svg_2" id="svg_3" transform="matrix(4,0,0,4,0,0) " x="-14.66667"/></g></svg>';
				
	    	$.docCollapse.setChildren(me);
	    	$(me).find('li').css('margin-left', opts.margin + 'px');
	    	$(me).find('li').each(function() {
	    		$(this).find(':header').first().css('margin-left', '-' + opts.margin + 'px');
	    	})
	    	$(me).find('li').children(':header').parent().css('list-style-type', 'none');
	    	$(me).find(':header').css('margin-bottom', '15px');
	    	$(me).find(':header').css('margin-top', '15px');
	    	$(me).find('li').each(function() {
	    		$(this).find(':header').first().hover(function() { $(this).css('cursor', 'pointer'); });
	    	})
	    	$.docCollapse.closeAll(me);
	    	$('.DCarrow').css('fill', opts.color);
	    	var hash = $(location).attr('hash');
	    	
	    	if (hash) {
	    		$.docCollapse.goToHash(hash)
	    	}
	        
	        $(window).on('hashchange', function() {
	        	var hash = $(location).attr('hash');
	        	$.docCollapse.closeAll();
	        	$.docCollapse.goToHash(hash);
	        });
		},
		
		closeAll: function(me) {
	    	$(me).children().children().find('ul').hide();
	    	$(me).children().children().find('li').hide();
	    	$(me).children().children().find('li').children(':header').parent().children().hide();
	    	$(me).children().children().find('.DCarrow').show();
	    	$(me).children().children('p').hide();
	    	$(me).find(':header').removeClass('DCOpen');
	    	$(me).children(':header').children('svg').each(function() {
	    		$.docCollapse.rotate(this, 0)
	    	});
	    	$(me).find('li').each(function() {
	    		$(this).find(':header').first().addClass('DCClosed');
	    	})
	    	$(me).children().each(function() {
	    		$(this).children(':header').hide();
	    		$(this).children(':header').first().show();
	    	})
	    },
	    
	    expand: function(me) {
	    	$(me).children(':header').children('svg').each(function() {
	    		$.docCollapse.rotate(this, 90)
	    	});
			$(me).children(':header').addClass('DCOpen');
			$(me).children(':header').removeClass('DCClosed');
	    	$(me).children().show();
	    	$(me).children('ul').children('li').show();
	    	$(me).children('ul').children('li').each(function() {
	    		$(this).children(':header').first().show();
	    	})
	    },
	    
	    close: function(me) {
	    	$(me).children(':header').children('svg').each(function() {
	    		$.docCollapse.rotate(this, 0)
	    	});
			$(me).children(':header').addClass('DCClosed');
			$(me).children(':header').removeClass('DCOpen');
	    	$(me).children('ul').children('li').children(':header').hide();
	    	$(me).children('ul').children('li').hide();
	    	$(me).children().hide();
	    	$(me).children(':header').first().show();
	    },
	    
	    setChildren: function(me) {
	    	$(me).children('li').each(function() {
	    		$(this).children(':header').first().each(function() {
		        	$(this).html('<svg width="' + $(this).height() * .75 + '" height="' + $(this).height() * .75 + '" class="DCarrow"><g transform="scale(' + $(this).height() * .0019 + ')">' + svg + $(this).html());
					$(this).addClass('DCClosed');
		    		$(this).click(function() {
		    			if ($(this).hasClass('DCClosed')) {
		    				$.docCollapse.expand($(this).parent());
		    			} else {
		    				$.docCollapse.close($(this).parent())
		    			}
		    		});
		    		$.docCollapse.setChildren($(this).parent().children('ul'))
		    	})
	    	})
	    },
	    
	    goToHash: function(hash) {
	    	$.docCollapse.expandUp($(hash))
			window.location.hash = hash;
	    },
	    
	    expandUp: function(me) {
	    	if ($(me).hasClass('DCClosed')) {
	    		$.docCollapse.expand($(me).parent())
	    		$.docCollapse.expandUp($(me).parent().parent().parent().children(':header'))
	    	}
	    },
	    
	    rotate: function(me, deg) {
	    	$(me).css({
	    		'-webkit-transform': 'rotate(' + deg + 'deg) translate3d(0, 0, 0)',
	        	'-moz-transform': 'rotate(' + deg + 'deg)',
	        	'-o-transform': 'rotate(' + deg + 'deg)',
	        	'-ms-transform': 'rotate(' + deg + 'deg)',
	        	'transform': 'rotate(' + deg + 'deg)'});
	    }
	};

    jQuery.fn.docCollapse = function(options) {
    	$.docCollapse.init(this, options);
    };

}( jQuery ));