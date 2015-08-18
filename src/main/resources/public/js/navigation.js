$(document).ready(function() {
    //создаём регулярное выражение для поиска точного соответствия содержимого
    var count = 1;
    var current = 1;
//Разобравться
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
        constructPanel(current, getSumOfPages(), parentId);
    }


    $('.left-content').click(function() {
        getNewContent();
        var pages = getSumOfPages();
        current = $(".navigation-current").html();
        if (currentPage > 0) {
            current--;
        	constructPanel(current, pages, $(this).attr('parentid'));
        }
    });
    $('.right-content').click(function() {
        getNewContent();
        var pages = getSumOfPages();
        var currentPage = $(".navigation-current").html();
        if (currentPage < pages ){ 
            current++;
        	constructPanel(current, pages, $(this).attr('parentid'));
        }
    });
    $('.navigation-content-pages').on('click', '.navigation-num', (function(){
    	if ($(this).html() != '...'){
            current = $(this).html();
            getNewContent();
        	constructPanel($(this).html(), getSumOfPages(), $(this).attr('parentid'));
        }
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

    function getSumOfPages() {
        $.ajax({
            type: "GET",
            url: '/count_news',
            data: {},
            success: function (response) {
                count = response.countOnPage;
                return response.countNews/count;
            }
        });
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

    function getNewContent(){
        $.ajax({
            type: "GET",
            url: '/load_news',
            data: {
                'checked': 1,
                'count': 1
            },
            success: function (response) {
                alert(response.content);
            }
        });
    }

});