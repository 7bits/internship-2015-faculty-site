(function($){				
    jQuery.fn.lightTabs = function(options){
        
        var createTabs = function(){
            tabs = this;
            i = 0;
            
            showPage = function(i){
                $(".tab-content").hide();
                $(".tab-content").eq(i).show();
                $(".tab-title").removeClass("active-tab");
                $(".tab-title").eq(i).addClass("active-tab");
            }
            
            showPage(0);				
            
            $(".tab-title").each(function(index, element){
                $(element).attr("data-page", i);
                i++;                        
            });
            
            $(".tab-title").click(function(){
                showPage(parseInt($(this).attr("data-page")));
            });				
        };		
        return this.each(createTabs);
    };	
})(jQuery);
$(document).ready(function(){
    $(".tabs").lightTabs();
});