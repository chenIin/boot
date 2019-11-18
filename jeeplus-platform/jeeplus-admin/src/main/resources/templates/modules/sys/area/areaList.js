<script>
var $treeTable = $('#treeTable').bootstrapTreeTable({
	type: 'get',                   // 请求方式（*）
	url: "${ctx}/sys/area/data",            // 请求后台的URL（*）
	ajaxParams : {},               // 请求数据的ajax的data属性
	toolbar: "#toolbar",      //顶部工具条
	expandColumn : 1,              // 在哪一列上面显示展开按钮
	columns: [{
		field: 'selectItem',
		checkbox: true
	},{
		title: '区域名称',
		field: 'name'
	},{
		title: '区域编码',
		field: 'area.name'
	},{
		title: '区域类型',
		field: 'code'
	},{
		title: '备注',
		field: 'remarks'
	},
		{
			title: '操作',
			width:'300px',
			align: "center",
			formatter: function(value,row, index) {
				var actions = []
					<% if(shiro.hasPermission("sys:area:view")){ %>
					actions.push('<a class="btn btn-icon waves-effect waves-light btn-custom btn-xs m-r-5" onclick=\'jp.openViewDialog("查看区", "${ctx}/sys/area/form?id='+row.id+'","800px", "600px")\'><i class="fa fa-search"></i>查看</a>')
					<% } %>
			<% if(shiro.hasPermission("sys:area:edit")){ %>
					actions.push('<a class="btn btn-icon waves-effect waves-light btn-success btn-xs m-r-5" onclick=\'jp.openSaveDialog("修改区域", "${ctx}/sys/area/form?id='+row.id+'","800px", "600px")\'><i class="fa fa-pencil"></i>修改</a>')
					<% } %>
			<% if(shiro.hasPermission("sys:area:del")){ %>
					actions.push('<a class="btn btn-icon waves-effect waves-light btn-danger btn-xs m-r-5" onclick=\'return del("'+row.id+'")\'><i class="fa fa-trash-o"></i>删除</a>')
					<% } %>
			<% if(shiro.hasPermission("sys:area:add")){ %>
					actions.push('<a class="btn btn-icon waves-effect waves-light btn-primary btn-xs" onclick=\'jp.openSaveDialog("添加下级区域", "${ctx}/sys/area/form?parent.id='+row.id+'","800px", "600px")\'><i class="fa fa-plus"></i>添加下级区域</a>');
				<% } %>

				return actions.join('');
			}
		}]
});

function del(id){
	jp.confirm('确认要删除区域吗？', function(){
		jp.loading();
		$.get("${ctx}/sys/area/delete?id="+id, function(data){
			if(data.success){
				$treeTable.bootstrapTreeTable('refresh');
				jp.success(data.msg);
			}else{
				jp.error(data.msg);
			}
		})

	});

}

var _expandFlag_all = false;
$("#expandAllBtn").click(function(){
	if(_expandFlag_all){
		$treeTable.bootstrapTreeTable('expandAll');
	}else{
		$treeTable.bootstrapTreeTable('collapseAll');
	}
	_expandFlag_all = _expandFlag_all?false:true;
})
function refresh() {
	$treeTable.bootstrapTreeTable('refresh');
}
</script>
