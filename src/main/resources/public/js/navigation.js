$(document).ready(function() {
    //создаём регулярное выражение для поиска точного соответствия содержимого
	$.expr[':'].findContent = function(obj, index, meta) {
		  var matchParams = meta[3].split(','),
		      regexFlags = 'ig',
		      regex = new RegExp('^' + $.trim(matchParams) + '$', regexFlags);
		  return regex.test($(obj).text());
	}
    createPanel("#panelParent");

    function createPanel(parentId) {
        $(parentId).append("<div class='left-content navigation-simple-element' parentId='" + parentId + "'>&lt;</div>");
        $(parentId).append("<div class='navigation-content-pages' parentId='" + parentId + "'>" + "</div>");
        $(parentId).append("<div class='right-content navigation-simple-element' parentId='" + parentId + "'>&gt;</div>");
        var currentPage = 25;
        constructPanel(currentPage, getPages(), parentId);
    }


    $('.left-content').click(function() {
        var pages = getPages();
        var currentPage = $(".navigation-current").html();
        if (currentPage > 0) currentPage--;
        	constructPanel(currentPage, getPages(), $(this).attr('parentid'));
    });
    $('.right-content').click(function() {
        var pages = getPages();
        var currentPage = $(".navigation-current").html();
        if (currentPage < pages ) currentPage++;
        	constructPanel(currentPage, getPages(), $(this).attr('parentid'));
    });
    $('.navigation-content-pages').on('click', '.navigation-num', (function(){
    	if ($(this).html() != '...')
        	constructPanel($(this).html(), getPages(), $(this).attr('parentid'));
    }));

    function constructPanel(current, count, parentId){
    	current = Number(current);
    	count = Number(count);
    	var container = $(".navigation-content-pages [parentid='"+parentId+"'");
    	container.remove("[parentid='"+parentId+"']");
    	var elementsAround = [];
    	for (i = current-2; i<=current+2; i++){
    		if (i > 0 && i < count){
    			elementsAround.push(i);
    		}
    	}
    	if (elementsAround[0] != 1){
    		if (elementsAround[0] != 2)
    			elementsAround.unshift('...');
    		elementsAround.unshift(1);
    	}
    	if (elementsAround[elementsAround.length-1] != count){
    		if (elementsAround[elementsAround.length-1] != count-1){
    			elementsAround.push('...');
    		}
    		elementsAround.push(count);
    	}
    	for (i = 0; i < elementsAround.length; i++){
			createNumPanel(elementsAround[i], parentId);
    	}
    	setActive(current, parentId);
    }

    function getPages() {
        return 85;
    }

    function setActive(currentPage, parentId) {
    	if (typeof currentPage == "number"){
	        $(".navigation-current").removeClass("navigation-current");
	        $("[parentid='"+parentId+"'] .navigation-num:findContent(" + currentPage + ")").addClass("navigation-current");
	    }
    }

    function createNumPanel(text, parentId){
    	var classes = 'navigation-simple-text';
    	if (text != '...')
			classes = "navigation-num navigation-simple-element";
		addNumElement(classes, text, parentId);
    }

    function addNumElement(classes, num, parentId){
		$('.navigation-content-pages ').append("<div class='" + classes + "' parentId='" + parentId + "''>" + num + "</div>");
    }

    

});