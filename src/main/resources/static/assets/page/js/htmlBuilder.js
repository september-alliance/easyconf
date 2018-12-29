/**
 * 根据模板render列表
 */
function buildHtmlWithJsonArray(clazz,json,removeTemplate,remainItems,rowIndexOffset){
    var temp = $('.'+clazz);

    var subCatagory = temp.parent();
    var dhtml = temp[0].outerHTML;
    //var temp = $(first);
    var copy=$(dhtml);
    temp.removeClass(clazz);
    temp.remove();
    if(!remainItems){
        $(subCatagory).empty();
    }
    
    for(var i=0;i<json.length;i++){
        //temp[0]表示dom元素
        var html = buildHtmlWithJson(temp[0],json[i] ,i+1 , rowIndexOffset);
        subCatagory.append(html);
    }
    
    var runscripts = subCatagory.find('[script=true]');
    runscripts.each(function(index,obj){
        // if(index>0){
            var val="";
            try{
                //val = eval(obj.textContent);
                val = eval(obj.outerText);
                if(!val){
                	val = eval(obj.textContent).trim();
                }
                if(obj.tagName=='INPUT'){
                    obj.value = val;        
                }else{
                    // obj.textContent = val;  
                    obj.innerHTML = val;  
                }
            }catch(e){
                console.log(e);
                console.log(obj.outerText);
                obj.outerText = "";
            }
            $(obj).attr('script','false');
        // }
    });

    if(!removeTemplate){
        copy.css('display','none');
        subCatagory.prepend(copy);
    }
}
function buildHtmlWithJson(temp,json , rowIndex , rowIndexOffset){
    temp.style.display='';
    if(!rowIndexOffset){
    	rowIndexOffset = 0;
    }
    var dhtml = temp.outerHTML;
    var dataAlias = $(temp).attr('data-item');
    for(var key in json){
        var v = json[key];
        if(v==null){
            v="";
        }
        dhtml = dhtml.replace("$[rowIndex]",rowIndexOffset+rowIndex);
        if(dataAlias){
        	key = dataAlias+"."+key;
        }
        dhtml = dhtml.replace(new RegExp("\\$\\["+key+"\\]","gm"),v);
    }
    //再次替换到没有数据的项
    dhtml = dhtml.replace(new RegExp("/\$\[\S+?\]","gm"),"");
    var subCatagory = $(dhtml);
    
    var cIfs = subCatagory.find('cif');
    cIfs.each(function(index,obj){
        $(obj).parent().html(processCIf(obj));
    });
    return subCatagory[0].outerHTML;
}

function processCIf(cIf){
	var cifParent = $(cIf).parent();
	var result = $(cifParent[0].outerHTML);
	result.empty();
	if( isIE8Before()){
		var test=false;
		for(var i=0;i<cifParent.children().length-1;i++){
	    	var elem = cifParent.children()[i];
	    	if(elem.tagName!='CIF'){
	    		if(test){
	    			result.append(elem.outerHTML);
	    		}
	    	}else{
	    		var script = $(elem).attr('test');
	            try{
	                test = eval(script);
	            }catch(e){
	            	console.error(e);
	            }
	    	}
	    }
	}else{
		for(var i=0;i<cifParent.children().length;i++){
	    	var elem = cifParent.children()[i];
	    	if(elem.tagName!='CIF'){
	    		result.append(elem.outerHTML);
	    	}else{
	    		var script = $(elem).attr('test');
	            try{
	                if(eval(script)){
	                	result.append($(elem).html());
	                }
	            }catch(e){
	            	console.error(e);
	            }
	    	}
	    }
	}
	
	return result.html();
}

function isIE8Before(){
	if(navigator.userAgent.indexOf("MSIE")>0){   
	      if(navigator.userAgent.indexOf("MSIE 6.0")>0){   
	        return true;
	      }   
	      if(navigator.userAgent.indexOf("MSIE 7.0")>0){  
	        return true;
	      }   
	      if(navigator.userAgent.indexOf("MSIE 8.0")>0){
	        return true;
	      }   
	      if(navigator.userAgent.indexOf("MSIE 9.0")>0){  
	        return false;
	      }   
	    }
}