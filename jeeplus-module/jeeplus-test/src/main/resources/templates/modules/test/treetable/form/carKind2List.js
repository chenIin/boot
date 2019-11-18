<script>
var $carKind2TreeTable;
$(document).ready(function() {
	  $carKind2TreeTable = $('#carKind2TreeTable').bootstrapTreeTable({

		 	type: 'get',                   // 请求方式（*）
            url: "${ctx}/test/treetable/form/carKind2/data",            // 请求后台的URL（*）
            ajaxParams : {},               // 请求数据的ajax的data属性
            toolbar: "#toolbar",      //顶部工具条
            expandColumn : 1,
               columns: [{
		        checkbox: true

		    }
			,{
		        field: 'name',
		        title: '名称',
		        sortable: true,
		        sortName: 'name'
		        ,formatter:function(value, row , index){
		        	  <% if(shiro.hasPermission("test:treetable:form:carKind2:edit") ){ %>
					   if(!value){
						  return "<a  href='#' onclick='edit(\""+row.id+"\")'>-</a>";
					   }else{
						  return "<a  href='#' onclick='edit(\""+row.id+"\")'>"+value+"</a>";
						}
                     <% }else if(shiro.hasPermission("test:treetable:form:carKind2:view")){ %>
					   if(!value){
						  return "<a  href='#' onclick='view(\""+row.id+"\")'>-</a>";
                       }else{
                          return "<a  href='#' onclick='view(\""+row.id+"\")'>"+value+"</a>";
                       }
                     <% }else{ %>
					      return value;
					 <% } %>
		         }

		    }
			,{
		        field: 'remarks',
		        title: '备注信息',
		        sortable: true,
		        sortName: 'remarks'

		    }
			,{
			   field: 'operate',
			   title: '操作',
			   align: 'center',
			   class: 'text-nowrap',
			   formatter:  function operateFormatter(value, row, index) {
				   return [
					<% if(shiro.hasPermission("test:treetable:form:carKind2:view")){ %>
					   '<a class="view btn btn-icon waves-effect waves-light btn-custom btn-xs m-r-5" onclick="view(\''+row.id+'\')">查看</a>',
				   <% } %>
				   <% if(shiro.hasPermission("test:treetable:form:carKind2:edit")){ %>
					   '<a class="edit btn btn-icon waves-effect waves-light btn-success btn-xs m-r-5" onclick="edit(\''+row.id+'\')">编辑</a>',
				   <% } %>
				   <% if(shiro.hasPermission("test:treetable:form:carKind2:del")){ %>
					   '<a class="del btn btn-icon waves-effect waves-light btn-danger btn-xs  m-r-5" onclick="del(\''+row.id+'\')">删除</a>',
				   <% } %>
				   <% if(shiro.hasPermission("test:treetable:form:carKind2:add") ){ %>
					   '<a class="addChild btn btn-icon waves-effect waves-light btn-primary btn-xs" onclick="addChild(\''+row.id+'\')">添加下级车系</a>'
				   <% } %>
				   ].join('');
			   }
		   }
		     ]

		});


	});

	//获取选中行
  function getSelections() {

        return $("#carKind2TreeTable").bootstrapTreeTable('getSelections');
    }

  //删除
  function del(id){

	 jp.confirm('确认要删除该车系记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/test/treetable/form/carKind2/delete?id=" + id, function(data){
				if(data.success){
					refresh();
					jp.toastr_success(data.msg);
				}else{
					jp.toastr_error(data.msg);
				}
				jp.close(index);
			})

	 })
  }

    //添加下级
 function addChild(id){//添加下级
    jp.go('${ctx}/test/treetable/form/carKind2/form/add?parent.id='+id);
 }
    //刷新列表
  function refresh() {
      $('#carKind2TreeTable').bootstrapTreeTable('refresh');
  }

   //新增表单页面
 function add() {
     jp.go("${ctx}/test/treetable/form/carKind2/form/add");
 }
  //编辑表单页面
  function edit(id){
	  jp.go("${ctx}/test/treetable/form/carKind2/form/edit?id="+id);
  }
  //查看表单页面
  function view(id) {
      jp.go( "${ctx}/test/treetable/form/carKind2/form/view?id="+id);
  }

      var _expandFlag_all = false;
    $("#expandAllBtn").click(function(){
        if(_expandFlag_all){
            $carKind2TreeTable.bootstrapTreeTable('expandAll');
        }else{
            $carKind2TreeTable.bootstrapTreeTable('collapseAll');
        }
        _expandFlag_all = _expandFlag_all?false:true;
    })
	function refresh() {
		$carKind2TreeTable.bootstrapTreeTable('refresh');
	}
</script>