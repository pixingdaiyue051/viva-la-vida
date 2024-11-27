<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<%@ include file="/WEB-INF/includes/common.jsp" %>
	<title><spring:message code="_demo"/></title>
  </head>
  <body>
    <div>
    	<input id="search_param" type="text" placeholder="<spring:message code='_lucene'/>"/>
    	<input id="search_btn" type="button" onclick="searchSolr('init')" value="<spring:message code='_search'/>"/>
    </div>
    <div id="search_result">
   		<table id="search_table">
   		</table>
   	</div>
    <div id="search_tab">
   		<a href="javascript:searchSolr('bak')">上一页</a>
   		<a href="javascript:searchSolr('pre')">下一页</a>
   	</div>
  </body>
  <script type="text/javascript">
  $('#search_param').on('keydown',function(event){
      if(event.keyCode == "13")
    	  searchSolr('init');
   });
  var index = -1;
  var pageIndex = 0;
  var pageSize = 100;
  function searchSolr(pagerEvent){
	  if(isEqual(pagerEvent,'pre')){
		  pageIndex = pageSize * (++index);
	  }else if(isEqual(pagerEvent,'bak')){
		  pageIndex = pageSize * (--index);
	  }else{
		  index = -1;
		  pageIndex = 0;
		  pageSize = 100;
	  }
	  $('#search_table').empty();
	  var searchParam = $('#search_param').val();
 	   $.ajax({
 		   type:"POST",
 		   url: "${webroot}/test/searchSolr.do",
            data: {
            	searchParam : searchParam,
            	isPager : true,
            	pageIndex : pageIndex,
            	pageSize : pageSize
            },
            dataType:'JSON',
   		    success: function (msg) {
   		    	console.log(msg.total);
	    		var tag = '';
	    		$.each(msg.data,function(i,d){
	    			var keys = Object.keys(d).sort();
	    			tag += '<tr><td>'+(i+1)+'</td>';
	    			$.each(keys,function(i_i,key){
		    			tag += '<td>'+d[key]+'</td>';
	    			});
	    			tag += '</tr>';
	    			$('#search_table').append(tag);
	    			tag = '';
	    		});
   		    }
 	   });
	}
  </script>
</html>