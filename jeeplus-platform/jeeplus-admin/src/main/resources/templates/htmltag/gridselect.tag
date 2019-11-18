	<input id="${id}Id" name="${name}"  type="hidden" value="${value!}"/>


	<div class="input-group">
		<input class="form-control"  id="${id}Name" name="${labelName!}" ${allowInput!true==true?'':'readonly="readonly"'}  type="text" value="${labelValue!}" data-msg-required="${dataMsgRequired!}" type="text">
		<span class="input-group-append">
			<button type="button"   id="${id}Button" class="btn waves-effect waves-light btn-custom  ${disabled!} ${hideBtn!'false'=='true' ? 'hide' : ''}"><i class="fa fa-search"></i></button>
			  <% if(allowClear!'false' == 'true'){ %>
	             	 <button type="button" id="${id}DelButton" class="close" data-dismiss="alert" style="position: absolute;top: 10px;right: 53px;z-index: 999;display: block;font-size: 18px;">×</button>
	            <% } %>
		</span>
	</div>


	 <label id="${id}Name-error" class="error" for="${id}Name" style="display:none"></label>
<script type="text/javascript">
$(document).ready(function(){
	$("#${id}Button, #${id}Name").click(function(){
		if ($("#${id}Button").hasClass("disabled")){
			return true;
		}
		
		top.layer.open({
		    type: 2,  
		    area: ['800px', '500px'],
		    title:"${title!}",
		    auto:true,
		    name:'friend',
		    content: "${ctx}/tag/gridselect?url="+encodeURIComponent("${url}")+"&fieldLabels="+encodeURIComponent("${fieldLabels}")+"&fieldKeys="+encodeURIComponent("${fieldKeys}")+"&searchLabels="+encodeURIComponent("${searchLabels!}")+"&searchKeys="+encodeURIComponent("${searchKeys!}")+"&isMultiSelected=${isMultiSelected!false}",
		    btn: ['确定', '关闭'],
		    yes: function(index, layero){
		    	 var iframeWin = layero.find('iframe')[0].contentWindow; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
		    	 var items = iframeWin.getSelections();
		    	 if(items == ""){
			    	jp.warning("必须选择一条数据!");
			    	return;
		    	 }
		    	 var ids = [];
		    	 var names = [];
		    	 for(var i=0; i<items.length; i++){
		    		 var item = items[i];
		    		 ids.push(item${fn.substring(name, fn.lastIndexOf(name, '.'), fn.length(name))});
		    		 names.push(item${fn.substring(labelName, fn.lastIndexOf(labelName, '.'), fn.length(labelName))})
		    	 }
		    	 $("#${id}Id").val(ids.join(","));
		    	 $("#${id}Name").val(names.join(","));
				 top.layer.close(index);//关闭对话框。
			  },
			  cancel: function(index){ 
		       }
		}); 
	})
	$("#${id}DelButton").click(function(){
		// 是否限制选择，如果限制，设置为disabled
		if ($("#${id}Button").hasClass("disabled")){
			return true;
		}
		// 清除	
		$("#${id}Id").val("");
		$("#${id}Name").val("");
		$("#${id}Name").focus();

	});
})
</script>
