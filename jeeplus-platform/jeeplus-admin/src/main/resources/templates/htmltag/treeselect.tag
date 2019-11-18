
	<input id="${id}Id" name="${name}"  type="hidden" value="${value!}"/>
	<div class="input-group">
		<input class="${class!'form-control'}"  id="${id}Name" name="${labelName!}" ${allowInput!true==true?'':'readonly="readonly"'}  type="text" value="${labelValue!}"  type="text">
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
		// 是否限制选择，如果限制，设置为disabled
		if ($("#${id}Button").hasClass("disabled")){
			return true;
		}
		// 正常打开	
		top.layer.open({
		    type: 2, 
		    area: ['300px', '420px'],
		    title:"选择${title}",
		    ajaxData:{selectIds: $("#${id}Id").val()},
		    content: "${ctx}/tag/treeselect?url="+encodeURIComponent("${url!}")+"&module=${module!}&checked=${checked!}&extId=${extId!}&isAll=${isAll!}&allowSearch=${allowSearch!}" ,
		    btn: ['确定', '关闭']
    	       ,yes: function(index, layero){ //或者使用btn1
						var tree = layero.find("iframe")[0].contentWindow.tree;//h.find("iframe").contents();
						var ids = [], names = [], nodes = [];
						if ("${checked!}" == "true"){
							nodes = tree.get_checked(true);
						}else{
							nodes = tree.get_selected(true);
						}
						for(var i=0; i<nodes.length; i++) {
							//<% if((checked!false == true) &&(notAllowSelectParent!false == true)){ %>
							if (nodes[i].original.isParent){
								continue; // 如果为复选框选择，则过滤掉父节点
							}
							//<% } %><% if(notAllowSelectRoot!false == true){ %>

							if (nodes[i].original.parent == "#"){
								top.layer.msg("不能选择根节点（"+nodes[i].text+"）请重新选择。", {icon: 0});
								return false;
							}
							//<% } %><% if(notAllowSelectParent!false == true){ %>

							if (nodes[i].children && nodes[i].children.length >0){
								top.layer.msg("不能选择父节点（"+nodes[i].text+"）请重新选择。", {icon: 0});
								return false;
							}
							//<% } %>

							ids.push(nodes[i].id);
							names.push(nodes[i].text);//
							<% if(checked!false == false){ %>
							break; // 如果为非复选框选择，则返回第一个选择
							<% } %>
						}

						$("#${id}Id").val(ids.join(",").replace(/u_/ig,""));
						$("#${id}Name").val(names.join(","));
						$("#${id}Name").focus();
						top.layer.close(index);
				    	       },
    	cancel: function(index){ //或者使用btn2
    	           //按钮【按钮二】的回调
    	       }
		}); 
	
	});
	
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