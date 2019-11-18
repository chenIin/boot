	<div id="flowChart">
		正在加载流程信息...
	</div>
	<style>
		#flowChart .jp-card{
			position: relative;
			float: none;
			width: 100%;
			border: 1px solid #ccc;
			margin: 0px;
		}
	</style>
<script type="text/javascript">
	$.get("${ctx}/act/task/flowChart?procInsId=${procInsId!}&startAct=${startAct!}&endAct=${endAct!}&t="+new Date().getTime(), function(data){
		$("#flowChart").html(data);
	});
</script>