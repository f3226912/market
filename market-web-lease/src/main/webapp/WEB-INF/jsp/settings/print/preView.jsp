<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>打印预览</title>
<style type="text/css">
	*{
		margin:0;
		padding:0;
	}
</style>
</head>
<body>
<a class="media" href="${fileUrl }"></a> 
<script type="text/javascript" src="${pageContext.request.contextPath }/lib/jquery.js"></script>   
<script type="text/javascript" src="${pageContext.request.contextPath }/lib/jquery.media.js"></script>
<script type="text/javascript">  
    $(function() {  
    	var width =  $(window).width()-10;
    	var height = $(window).height()-10;
        $('a.media').media({width:width, height:height});
    });
</script>
</body>
</html>