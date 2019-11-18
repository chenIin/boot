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
    	       //显示到处按钮
    	       showExport: true,
        		showSearch: true,
				//允许列拖动大小
				resizable: true,
				//固定表头
				stickyHeader: true,
				stickyHeaderOffsetY: 0,
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
		        field: 'key',
		        title: '流程标识',
			    formatter:function (value, row, index) {
				   return '<a href="${ctx}/act/task/form?procDefId='+row.id+'">'+row.key+'</a>';
			    }
		       
		    }
			,{
		        field: 'name',
		        title: '流程名称',
		        sortable: true
		       
		    }
			,{
		        field: 'diagramResourceName',
		        title: '流程图',
			   formatter:function (value, row, index) {
				   return "<a  href=\"javascript:jp.openViewDialog('流程图','${ctx}/act/process/resource/read?procDefId="+row.id+"&resType=image','1000px', '600px')\">"+row.diagramResourceName+"</a>";
			   }
		       
		    }
			,{
		        field: 'version',
		        title: '流程版本',
			   formatter:function (value, row, index) {
				   return "<font class='badge badge-pill badge-custom'>" + value +"</font>";
			   }

		    }
			,{
		        field: 'deploymentTime',
		        title: '更新时间',
			   formatter:function (value, row, index) {
				   return jp.dateFormat(value,"yyyy-MM-dd hh:mm:ss");
			   }
		       
		    }, {
                       field: 'operate',
                       title: '操作',
                       align: 'center',
                       class: 'text-nowrap',
					   formatter: function operateFormatter(value, row, index) {
						   return [
							   '<a class="start btn btn-custom btn-xs waves-effect" href="${ctx}/act/task/form?procDefId=' + row.id + '&status=start">启动流程</a>'].join('');
					   }
                   }
		     ]
		
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
		
  function getIdSelections() {
        return $.map($("#processTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  

</script>