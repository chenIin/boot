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
	$("#${id}Button, #${id}Name").click(function(){
		// 是否限制选择，如果限制，设置为disabled
		if ($("#${id}Button").hasClass("disabled")){
			return true;
		}
		// 正常打开	
		
		jp.openUserSelectDialog(${isMultiSelected!false? true:false},function(ids, names){
			$("#${id}Id").val(ids.replace(/u_/ig,""));
			$("#${id}Name").val(names);
			$("#${id}Name").focus();
		})
	
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
</script>