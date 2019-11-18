<script>
$(document).ready(function() {
	$('#table').bootstrapTable({
		 
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
				//显示检索按钮
		       showSearch: true,
				//允许列拖动大小
				resizable: true,
				//固定表头
				stickyHeader: true,
				stickyHeaderOffsetY: 0,
               //显示 内容列下拉框
    	       showColumns: true,
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
			singleSelect:true,
                //排序方式 
               sortOrder: "asc",  
               //初始化加载第一页，默认第一页
               pageNumber:1,   
               //每页的记录行数（*）   
               pageSize: 10,  
               //可供选择的每页的行数（*）    
               pageList: [10, 25, 50, 100],
				onShowSearch: function () {
					$("#search-collapse").slideToggle();
				},
               //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据  
               url: "${ctx}/monitor/scheduleJob/data",
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
		        field: 'name',
		        title: '任务名',
		        sortable: true
		       
		    }
			,{
		        field: 'group',
		        title: '任务组',
		        sortable: true,
				sortName: 't_group',
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fn.toJson(fn.getDictList('schedule_task_group'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'cronExpression',
		        title: '定时规则',
		        sortable: true
		       
		    }
			,{
		        field: 'status',
		        title: '启用状态',
		        sortable: true,
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fn.toJson(fn.getDictList('yes_no'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'isInfo',
		        title: '通知用户',
		        sortable: true,
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fn.toJson(fn.getDictList('schedule_task_info'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'className',
		        title: '任务类',
		        sortable: true
		       
		    }
			,{
		        field: 'description',
		        title: '描述',
		        sortable: true
		       
		    }, {
                field: 'operate',
                title: '操作',
                align: 'center',
                class: 'text-nowrap',
                events: {
    		        'click .resume': function (e, value, row, index) {
    		        	resume(row.id);
    		        },
    		        'click .stop': function (e, value, row, index) {
    		        	stop(row.id);
    		        }
    		    },
                formatter:  function operateFormatter(value, row, index) {
    		        return [

                        <% if(shiro.hasPermission("monitor:scheduleJob:resume")){ %>
						'<a href="#" class="resume btn btn-custom btn-xs waves-effect waves-custom m-r-5" title="启动" >启动</a>',
                    	<% } %>
                   		 <% if(shiro.hasPermission("monitor:scheduleJob:stop")){ %>
							'<a href="#" class="stop btn btn-danger btn-xs waves-effect waves-custom" title="暂停">暂停</a>'
                            <% } %>
    		        ].join('');
    		    }
		    }]
	});
		
	  $('#table').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#table').bootstrapTable('getSelections').length);
            $('#edit,#stop,#resume,#startNow').prop('disabled', $('#table').bootstrapTable('getSelections').length!=1);
        });
		  
	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#table').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $('#table').bootstrapTable('refresh');
		});
	});
		
  function getIdSelections() {
        return $.map($("#table").bootstrapTable('getSelections'), function (row) {
            return row.id 
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该定时任务记录吗？', function(){
			var index = jp.loading();
			jp.get("${ctx}/monitor/scheduleJob/deleteAll?ids=" + getIdSelections(), function(data){
				jp.close(index);
         	  		if(data.success){
         	  			$('#table').bootstrapTable('refresh');
         	  			jp.toastr_success(data.msg);
         	  		}else{
                        jp.toastr_error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  function edit(){

  	jp.go("${ctx}/monitor/scheduleJob/form?id=" + getIdSelections());
}

		   
//暂停
  function stop(id){
	  if(!id){
		  id = getIdSelections();
	  }
	  jp.confirm('确定要暂停任务？', function(){
			jp.loading();  	
			jp.get("${ctx}/monitor/scheduleJob/stop?id=" + id, function(data){
       	  		if(data.success){
       	  			$('#table').bootstrapTable('refresh');
       	  			jp.success(data.msg);
       	  		}else{
       	  			jp.error(data.msg);
       	  		}
       	  	})
        	   
		})
  }

  //恢复
  function resume(id){
	  if(!id){
		  id = getIdSelections();
	  }
	  jp.confirm('确定要恢复任务？', function(){
			jp.loading();  	
			jp.get("${ctx}/monitor/scheduleJob/resume?id=" + id, function(data){
       	  		if(data.success){
       	  			$('#table').bootstrapTable('refresh');
       	  			jp.success(data.msg);
       	  		}else{
       	  			jp.error(data.msg);
       	  		}
       	  	})
        	   
		})
  }

  //立即运行一次
  function startNow(){
	  jp.confirm('确定要立即运行一次该任务？', function(){
			jp.loading();  	
			jp.get("${ctx}/monitor/scheduleJob/startNow?id=" + getIdSelections(), function(data){
       	  		if(data.success){
       	  			$('#table').bootstrapTable('refresh');
       	  			jp.success(data.msg);
       	  		}else{
       	  			jp.error(data.msg);
       	  		}
       	  	})
        	   
		})
  	};
</script>