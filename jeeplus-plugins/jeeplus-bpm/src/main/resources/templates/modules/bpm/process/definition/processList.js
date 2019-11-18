<script>
$(document).ready(function() {
	$('#processTable').bootstrapTable({
		 
		  //请求方法
                method: 'post',
                //类型json
                dataType: "json",
                contentType: "application/x-www-form-urlencoded",
                //移动端自适应
                mobileResponsive: true,
               //显示刷新按钮
               showRefresh: true,
               //显示切换手机试图按钮
               showToggle: true,
               //显示 内容列下拉框
    	       showColumns: true,
               showSearch:true,
                //允许列拖动大小
                resizable: true,
                //固定表头
                stickyHeader: true,
                stickyHeaderOffsetY: 0,
    	       //显示到处按钮
    	       showExport: true,
    	       //显示切换分页按钮
    	       showPaginationSwitch: true,
    	       //最低显示2行
    	       minimumCountColumns: 2,
               //是否显示行间隔色
               striped: false,
               //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）     
               cache: false,    
               //是否显示分页（*）  
               pagination: true,   
                //排序方式 
               sortOrder: "asc",  
               //初始化加载第一页，默认第一页
               pageNumber:1,   
               //每页的记录行数（*）   
               pageSize: 10,  
               //可供选择的每页的行数（*）    
               pageList: [10, 25, 50, 100],
               //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据  
               url: "${ctx}/act/process/list/data",
               //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
               //queryParamsType:'',   
               ////查询参数,每次调用是会带上这个参数，可自定义                         
               queryParams : function(params) {
               	var searchParam = $("#searchForm").serializeJSON();
               	searchParam.pageNo = params.limit === undefined? "1" :params.offset/params.limit+1;
               	searchParam.pageSize = params.limit === undefined? -1 : params.limit;
                if(params.sort && params.order){
                   searchParam.orderBy = params.sort+ " "+  params.order;
                }
                return searchParam;
               },
               //分页方式：client客户端分页，server服务端分页（*）
               sidePagination: "server",
                onShowSearch: function () {
                    $("#search-collapse").fadeToggle();
                },
               columns: [{
		        checkbox: true
		       
		    }
			,{
		        field: 'category',
		        title: '流程分类',
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fn.toJson(fn.getDictList('act_category'))}, value, "无分类");
		        }
		       
		    }
			,{
		        field: 'id',
		        title: '流程标识'
		       
		    }
			,{
		        field: 'name',
		        title: '流程名称'
		       
		    }
			,{
		        field: 'version',
		        title: '流程版本',
               formatter:function (value, row, index) {
                    return "<font class='badge badge-pill badge-custom'>" + value +"</font>";
                }
		    }
			,{
		        field: 'resourceName',
		        title: '流程XML',
			   formatter:function(value, row , index){
				   return '<a target="_blank" href="${ctx}/act/process/resource/read?procDefId='+row.id+'&resType=xml">'+value+'</a>';
			   }
		    }
			,{
		        field: 'diagramResourceName',
		        title: '流程图片',
			   formatter:function(value, row , index){
				   return '<a target="_blank" href="${ctx}/act/process/resource/read?procDefId='+row.id+'&resType=image">'+value+'</a>';
			   }


                   }
			,{
		        field: 'deploymentTime',
		        title: '部署时间',
			   formatter:function (value, row, index) {
				   return jp.dateFormat(value,"yyyy-MM-dd hh:mm:ss");
			   }
		       
		    }, {
                       field: 'operate',
                       title: '操作',
                       align: 'center',
                       class: 'text-nowrap',
                       events: {
                           'click .active': function (e, value, row, index) {
                               jp.confirm('确认要激活吗？',function () {
                                   jp.get("${ctx}/act/process/update/active?procDefId="+row.id,function (data) {
                                       if(data.success){
                                           jp.success(data.msg);
                                           $('#processTable').bootstrapTable('refresh');

                                       }else{
                                           jp.error(data.msg);
                                       }

                                   })
                               })

                           },
                           'click .del': function (e, value, row, index) {
                               jp.confirm('确认要删除该流程吗？', function() {
                                   jp.loading();
                                   jp.get("${ctx}/act/process/delete?deploymentId="+row.deploymentId, function (data) {
                                       if (data.success) {
                                           $('#processTable').bootstrapTable('refresh');
                                           jp.success(data.msg);
                                       } else {
                                           jp.error(data.msg);
                                       }
                                   })
                               })
                           },
                           'click .suspend': function (e, value, row, index) {
                               jp.confirm('确认挂起该流程吗？', function() {
                                   jp.loading();
                                   jp.get("${ctx}/act/process/update/suspend?procDefId="+row.id, function (data) {
                                       if (data.success) {
                                           $('#processTable').bootstrapTable('refresh');
                                           jp.success(data.msg);
                                       } else {
                                           jp.error(data.msg);
                                       }
                                   })
                               })
                           },
                           'click .toModel': function (e, value, row, index) {
                               jp.confirm('确认要转换为模型吗？', function() {
                                   jp.loading();
                                   jp.get("${ctx}/act/process/convert/toModel?procDefId="+row.id, function (data) {
                                       if (data.success) {
                                           $('#processTable').bootstrapTable('refresh');
                                           jp.success(data.msg);
                                       } else {
                                           jp.error(data.msg);
                                       }
                                   })
                               })
                           }
                       },
                       formatter: function operateFormatter(value, row, index) {
                           if(row.suspended){
                               return [
                                   '<a class="active btn btn-custom btn-xs waves-effect m-r-5">激活</a>',
                           			'<a class="del btn btn-danger btn-xs waves-effect m-r-5">删除</a>',
                                   '<a class="toModel btn btn-primary btn-xs waves-effect m-r-5">转换为模型</a>'
                           		].join('');
                           }else {
                               return [
                                   '<a class="suspend btn btn-custom btn-xs waves-effect m-r-5">挂起</a>',
                                   '<a class="del btn btn-danger btn-xs waves-effect m-r-5">删除</a>',
                                   '<a class="toModel btn btn-primary btn-xs waves-effect m-r-5">转换为模型</a>'
                           		].join('');
						   }

                       }
                   }
		     ]
		
		});
		

	  $('#processTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#processTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#processTable').bootstrapTable('getSelections').length!=1);
        });
		  

	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#processTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#processTable').bootstrapTable('refresh');
		});
		
		
	});
  function refresh() {
      $('#processTable').bootstrapTable('refresh');
   }
  function getIdSelections() {
        return $.map($("#processTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该流程管理记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/act/process/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#processTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
   function upload(){

	  jp.openSaveDialog('上传流程文件', " ${ctx}/act/process/deploy/",'800px', '500px');
  }

</script>