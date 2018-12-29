var serviceName="";

/**
 * 获取根路径
 * 
 * @returns
 */
function getRootName() {
	var pathName = window.document.location.pathname;
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	return projectName;
}

/**
 * 获取跟路径URL
 * 
 * @returns
 */
function getRootPath() {
	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	var localhostPaht = curWwwPath.substring(0, pos);	
	return (localhostPaht + serviceName);
}

function openPage(url){
	$.get(url, function(data) {
		$(".main-content").html(data);
	});
}

//重新加载第一页
function closeWindowAndRefreshParent(){
	if(window.parent.listData && typeof(window.parent.doSearch)=="function"){
		window.parent.doSearch(); //刷新父页面
	}
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);  // 关闭layer
}

//刷新当前页
function closeWindowAndRefreshParentCurrentPage(){
	setTimeout(function(){
		if(window.parent.listData && typeof(window.parent.doSearchCurrentPage)=="function"){
			window.parent.doSearchCurrentPage(); //刷新父页面
		}
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);  // 关闭layer
	},1500);
}

function closeChildWindow(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);  // 关闭layer
}

function createPager(pageId , totalPages ,onchange){
	pager = $.jqPaginator(pageId, {
		totalPages: totalPages,
        visiblePages: 3,
        currentPage: 1,
        prev: '<li class="prev"><a href="javascript:;">上一页</a></li>',
        next: '<li class="next"><a href="javascript:;">下一页</a></li>',
        page: '<li class="page"><a href="javascript:;">{{page}}</a></li>',
        last: '<li class="page"><a href="javascript:;">{{totalPages}}</a></li>',
        onPageChange: function (num, type) {
        	if('init'!=type){
        		onchange(num,type);
        	}
        }
    });
	return pager;
}

function showErrorMsg(argErrors){
	var txt = "";
	for(var i=0;i<argErrors.length;i++){
		txt+= argErrors[i].message+"<br/>";
	}
	layer.msg(txt);
}

function bindInputEnterUp(){
	$("input").keyup(function(){
		if(event.keyCode ==13){
			if(doSearch){
				doSearch();
			}
          }
    });
}

$(function(){
	bindInputEnterUp();
});

function sajax(options){
	var successHandler = options.success;
	var loading;
	options.beforeSend=function(){
		loading = layer.load(1, {
		  shade: [0.1,'#fff'] //0.1透明度的白色背景
		});
	};
	options.complete=function(){
		layer.close(loading);
	};
	options.success=function (result) {
		if (result.code != 0) {
			if(result.errorType=='args_not_valid'){
				showErrorMsg(result.argErrors);
			}else if(result.errorType=='unexpect_exception'){
				layer.msg('系统内部错误');
			}else{
				layer.msg(result.desc);
			}
			return;
		}
		if(successHandler){
			successHandler(result.data);
		}
    };
	return $.ajax(options);
}