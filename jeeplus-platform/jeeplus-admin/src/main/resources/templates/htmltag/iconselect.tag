<i id="${id}Icon" class="${isNotEmpty(value)?value:' hide'}"></i>&nbsp;<span id="${id}IconLabel">${isNotEmpty(value)?value:'无'}</span>&nbsp;
<input id="${id}" name="${name}" type="hidden" value="${value!}"/>
<a id="${id}Button" href="javascript:" class="btn waves-effect waves-light btn-custom m-r-5">选择</a>
<a id="${id}clear" class="btn waves-effect waves-light btn-danger"  onclick="clear()"> 清除</a>
<script type="text/javascript">
$(document).ready(function(){
	$("#${id}Button").click(function(){

		top.layer.open({
			type: 2, 
			title:"选择图标",
            auto:true,
			area: ['700px',  $(top.document).height()-180+"px"],
		    content: '${ctx}/tag/iconselect?value="+$("#${id}").val()',
		    btn: ['确定', '关闭'],
		    yes: function(index, layero){ //或者使用btn1
		    	var icon = layero.find("iframe")[0].contentWindow.$("#icon").val();
            	$("#${id}Icon").attr("class", icon);
                $("#${id}IconLabel").text(icon);
                $("#${id}").val(icon);
                top.layer.close(index);
		    },cancel: function(index){ //或者使用btn2
		    	
		    }
		});
	});
	$("#${id}clear").click(function(){
		 $("#${id}Icon").attr("class", "icon- hide");
         $("#${id}IconLabel").text("无");
         $("#${id}").val("");

	});
})
</script>