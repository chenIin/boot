
<% if(isNotEmpty(content)){
	var ctype;
	var cshowType = '0';
	if(isNotEmpty(type)){
		ctype = type;
	}else{
		ctype =  strutil.contain (content,'失败')?'success':'danger';
	}
	if(isNotEmpty(showType)){
		cshowType = showType;
	}

	if(cshowType == '1'){
%>
<div id="messageBox" class="alert alert-${ctype!}" style="margin: 5px;padding: 7px">
	<button data-dismiss="alert" class="close">×</button>${content!}</div>
<% }else{ %>
<script type="text/javascript">
    top.layer.msg("${content!}", {icon: "${ctype=='success'? 1:0}"});
</script>
<% }} %>