

var ReportServerPlugin = {
    bind_callbacks: function(workspace) {
    	/* add reload button */
    	var $chart_button = 
            $('<a href="#reloadsaiku" class="button toolbar i18n reloadsaiku" title="Reload Cube"></a>')
            .css({  'background-image': "url('js/saiku/plugins/ReportServer/arrow_rotate_clockwise_red.png')",
                    'background-repeat':'no-repeat',
                    'background-position':'5px 5px'
                })
             .click(function(){
            	 $.get( Settings.REST_MOUNT_POINT + Saiku.session.username + "/repository2/resource/reset", function(result) {
         			if(result){
         				location.reload();
         			}
         		});
            	 
             });
        var $chart_li = $('<li class="seperator"></li>').append($chart_button);
       	$('.workspace_toolbar').find("ul").append($chart_li);
    	
    	/* bind callback */
    	workspace.bind('dimensions:loaded', function(){
    		$('.cubes').hide();
    		$('.cubes').parent().hide();
    		$('.cubes').parent().prev().hide();
    		$('.cubes').parent().next().addClass("top");
    		
    		$('.new').hide();
    		$('.open').hide();
    		$('.save').hide();
    		
    		$('.buckets').hide();
    		$('.explain_query').hide();

    		$.get( Settings.REST_MOUNT_POINT + Saiku.session.username + "/repository2/resource/isMDX", function(result) {
    			if(!result){
    				$('.switch_to_mdx').hide();
    				$('.mdx').hide();
    			}
    		});
    		
    		
    		$('.export_xls').hide();
    		$('.export_csv').hide();
    		$('.export_pdf').hide();
    		
    		$('.run').trigger('click');
    	});
    	
    	workspace.bind('query:new', function(){
        	$('.reloadsaiku').removeClass('disabled_toolbar');        	
        });
    	
        workspace.bind('query:result', function(){
        	$('.reloadsaiku').removeClass('disabled_toolbar');        	
        });
    }
};



Saiku.events.bind('session:new', function(session) {
	$('#header').remove();
	
	if (Saiku.tabs._tabs[0] && Saiku.tabs._tabs[0].content) {
    	ReportServerPlugin.bind_callbacks(Saiku.tabs._tabs[0].content);
    }

    Saiku.session.bind('workspace:new', function(args) {
    	ReportServerPlugin.bind_callbacks(args.workspace);
    });
    
    setTimeout(function(){
		if($('.cubes').prop('selectedIndex') != 1){
			$('.cubes').prop('selectedIndex', 1);
			$('.cubes').trigger('change');
		}
	}, 100);
});




