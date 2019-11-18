<script>
$(document).ready(function() {
	$('#testDataMainForm2Table').bootstrapTable({
		 
		  //请求方法
               method: 'post',
               //类型json
               dataType: "json",
               contentType: "application/x-www-form-urlencoded",
               //移动端自适应
               mobileResponsive: true,
               //允许列拖动大小
               resizable: true,
               //固定表头
               stickyHeader: true,
               stickyHeaderOffsetY: 0,
			   //显示检索按钮
		       showSearch: true,
               //显示刷新按钮
               showRefresh: true,
               //显示切换手机试图按钮
               showToggle: true,
               //显示 内容列下拉框
    	       showColumns: true,
    	       //显示到处按钮
    	       showExport: true,
    	       //显示切换分页按钮
    	       showPaginationSwitch: true,
    	       //显示详情按钮
    	       detailView: true,
    	       	//显示详细内容函数
	           detailFormatter: "detailFormatter",
    	       //最低显示2行
    	       minimumCountColumns: 2,
               //是否显示行间隔色
               striped: false,
               //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性(*)
               cache: false,    
               //是否显示分页(*)
               pagination: true,
               //分页方式: client客户端分页，server服务端分页(*)
               sidePagination: "server",
                //排序方式 
               sortOrder: "asc",  
               //初始化加载第一页，默认第一页
               pageNumber:1,   
               //每页的记录行数(*)
               pageSize: 10,  
               //可供选择的每页的行数(*)
               pageList: [10, 25, 50, 100],
               //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据  
               url: "${ctx}/test/onetomany/form/testDataMainForm2/data",
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
               onShowSearch: function () {
               	 $("#import-collapse").hide();
				 $("#search-collapse").fadeToggle();
               },
               columns: [{
		        checkbox: true
		       
		    }
			,{
		        field: 'tuser.name',
		        title: '归属用户',
		        sortable: true,
		        sortName: 'tuser.name'
		        ,formatter:function(value, row , index){
		             <% if(shiro.hasPermission("test:onetomany:form:testDataMainForm2:edit") ){ %>
					   if(!value){
						  return "<a  href='#' onclick='edit(\""+row.id+"\")'>-</a>";
					   }else{
						  return "<a  href='#' onclick='edit(\""+row.id+"\")'>"+value+"</a>";
						}
                     <% }else if(shiro.hasPermission("test:onetomany:form:testDataMainForm2:view")){ %>
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
		        field: 'office.name',
		        title: '归属部门',
		        sortable: true,
		        sortName: 'office.name'
		       
		    }
			,{
		        field: 'area.name',
		        title: '归属区域',
		        sortable: true,
		        sortName: 'area.name'
		       
		    }
			,{
		        field: 'name',
		        title: '名称',
		        sortable: true,
		        sortName: 'name'
		       
		    }
			,{
		        field: 'sex',
		        title: '性别',
		        sortable: true,
		        sortName: 'sex',
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fn.toJson(fn.getDictList('sex'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'inDate',
		        title: '加入日期',
		        sortable: true,
		        sortName: 'inDate'
		       
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
			   events: {
				   'click .view': function (e, value, row, index) {
					   view(row.id);
				   },
				   'click .edit': function (e, value, row, index) {
					   edit(row.id)
				   },
				   'click .del': function (e, value, row, index) {
					   del(row.id);

				   }
			   },
			   formatter:  function operateFormatter(value, row, index) {
				   return [
					<% if(shiro.hasPermission("test:onetomany:form:testDataMainForm2:view")){ %>
					   '<a class="view btn btn-icon waves-effect waves-light btn-custom btn-xs m-r-5"> <i class="fa fa-search"></i></a>',
				   <% } %>
				   <% if(shiro.hasPermission("test:onetomany:form:testDataMainForm2:edit")){ %>
					   '<a class="edit btn btn-icon waves-effect waves-light btn-success btn-xs m-r-5"> <i class="fa fa-pencil"></i></a>',
				   <% } %>
				   <% if(shiro.hasPermission("test:onetomany:form:testDataMainForm2:del")){ %>
					   '<a class="del btn btn-icon waves-effect waves-light btn-danger btn-xs"> <i class="fa fa-trash-o"></a>'
				   <% } %>
				   ].join('');
			   }
		   }
		     ]
		
		});
		

	  
	  $('#testDataMainForm2Table').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#testDataMainForm2Table').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#testDataMainForm2Table').bootstrapTable('getSelections').length!=1);
        });

	 $("#import").click(function(){//显示导入面板
            $("#search-collapse").hide();
            $("#import-collapse").fadeToggle()

      })

	 $("#btnImportExcel").click(function(){//导入Excel
		 var importForm = $('#importForm')[0];
		 jp.block('#import-collapse',"文件上传中...");
		 jp.uploadFile(importForm,"${ctx}/test/onetomany/form/testDataMainForm2/import",function (data) {
			 if(data.success){
				 jp.toastr_success(data.msg);
				 refresh();
			 }else{
				 jp.toastr_error(data.msg);
			 }
			 jp.unblock('#import-collapse',200);
		 })
	  })

	 $("#btnDownloadTpl").click(function(){//下载模板文件
            jp.downloadFile('${ctx}/test/onetomany/form/testDataMainForm2/import/template');
		})

	 $("#export").click(function(){//导出Excel文件
	        var searchParam = $("#searchForm").serializeJSON();
	        searchParam.pageNo = 1;
	        searchParam.pageSize = -1;
            var sortName = $('#testDataMainForm2Table').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#testDataMainForm2Table').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/test/onetomany/form/testDataMainForm2/export?'+values);
	  })

	  $("#search").click("click", function() {// 绑定查询按扭
		  refresh();
		});

	 $("#reset").click("click", function() { //绑定重置按钮
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  refresh();
		});

	 $('#inDate').datepicker({//日期控件初始化
			toggleActive: true,
			language:"zh-CN",
    			format:"yyyy-mm-dd"
		});
		
	});

	//获取选中行
  function getIdSelections() {
        return $.map($("#testDataMainForm2Table").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

  //删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要删除该票务代理记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/test/onetomany/form/testDataMainForm2/delete?ids=" + ids, function(data){
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

     //刷新列表
  function refresh() {
      $('#testDataMainForm2Table').bootstrapTable('refresh');
  }

   //新增表单页面
 function add() {
     jp.go( "${ctx}/test/onetomany/form/testDataMainForm2/form/add" );
 }
  //编辑表单页面
  function edit(id){
      if(!id){
          id = getIdSelections();
      }
	  jp.go( "${ctx}/test/onetomany/form/testDataMainForm2/form/edit?id=" + id);
  }
  //查看表单页面
  function view(id) {
      if(!id){
          id = getIdSelections();
      }
      jp.go( "${ctx}/test/onetomany/form/testDataMainForm2/form/view?id=" + id);
  }
 //子表展示
		   
  function detailFormatter(index, row) {
	  var htmltpl =  $("#testDataMainForm2ChildrenTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
	  var html = Mustache.render(htmltpl, {
			idx:row.id
		});
	  $.get("${ctx}/test/onetomany/form/testDataMainForm2/detail?id="+row.id, function(testDataMainForm2){
    	var testDataMainForm2Child1RowIdx = 0, testDataMainForm2Child1Tpl = $("#testDataMainForm2Child1Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data1 =  testDataMainForm2.testDataChild21List;
		for (var i=0; i<data1.length; i++){
			data1[i].dict = {};
			addRow('#testDataMainForm2Child-'+row.id+'-1-List', testDataMainForm2Child1RowIdx, testDataMainForm2Child1Tpl, data1[i]);
			testDataMainForm2Child1RowIdx = testDataMainForm2Child1RowIdx + 1;
		}
				
    	var testDataMainForm2Child2RowIdx = 0, testDataMainForm2Child2Tpl = $("#testDataMainForm2Child2Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data2 =  testDataMainForm2.testDataChild22List;
		for (var i=0; i<data2.length; i++){
			data2[i].dict = {};
			addRow('#testDataMainForm2Child-'+row.id+'-2-List', testDataMainForm2Child2RowIdx, testDataMainForm2Child2Tpl, data2[i]);
			testDataMainForm2Child2RowIdx = testDataMainForm2Child2RowIdx + 1;
		}
				
    	var testDataMainForm2Child3RowIdx = 0, testDataMainForm2Child3Tpl = $("#testDataMainForm2Child3Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data3 =  testDataMainForm2.testDataChild23List;
		for (var i=0; i<data3.length; i++){
			data3[i].dict = {};
			addRow('#testDataMainForm2Child-'+row.id+'-3-List', testDataMainForm2Child3RowIdx, testDataMainForm2Child3Tpl, data3[i]);
			testDataMainForm2Child3RowIdx = testDataMainForm2Child3RowIdx + 1;
		}
				
      	  			
      })
     
        return html;
    }
  
	function addRow(list, idx, tpl, row){
		$(list).append(Mustache.render(tpl, {
			idx: idx, delBtn: true, row: row
		}));
	}
</script>
<script type="text/template" id="testDataMainForm2ChildrenTpl">//<!--
	<div class="card card-tabs">
	<div class="card-heading  pb-0">
	    <ul class="nav nav-pills float-left" role="tablist">
				<li class="nav-item"><a data-toggle="tab" class="nav-link show active" href="#tab-{{idx}}-1" aria-expanded="true">火车票</a></li>
				<li class="nav-item"><a data-toggle="tab" class="nav-link show" href="#tab-{{idx}}-2" aria-expanded="true">飞机票</a></li>
				<li class="nav-item"><a data-toggle="tab" class="nav-link show" href="#tab-{{idx}}-3" aria-expanded="true">汽车票</a></li>
		</ul>
		</div>
		<div class="card-body">
		<div class="tab-content">
				 <div id="tab-{{idx}}-1" class="tab-pane fade active show" >
						<table class="table table-bordered">
						<thead>
							<tr>
								<th>出发地</th>
								<th>目的地</th>
								<th>出发时间</th>
								<th>到达时间</th>
								<th>代理价格</th>
								<th>文件</th>
								<th>备注信息</th>
							</tr>
						</thead>
						<tbody id="testDataMainForm2Child-{{idx}}-1-List">
						</tbody>
					</table>
				</div>
				<div id="tab-{{idx}}-2" class="tab-pane fade show">
					<table class="ani table">
						<thead>
							<tr>
								<th>出发地</th>
								<th>目的地</th>
								<th>出发时间</th>
								<th>代理价格</th>
								<th>备注信息</th>
							</tr>
						</thead>
						<tbody id="testDataMainForm2Child-{{idx}}-2-List">
						</tbody>
					</table>
				</div>
				<div id="tab-{{idx}}-3" class="tab-pane fade show">
					<table class="ani table">
						<thead>
							<tr>
								<th>出发地</th>
								<th>目的地</th>
								<th>代理价格</th>
								<th>备注信息</th>
							</tr>
						</thead>
						<tbody id="testDataMainForm2Child-{{idx}}-3-List">
						</tbody>
					</table>
				</div>
		</div>
		</div>
		</div>//-->
	</script>
	<script type="text/template" id="testDataMainForm2Child1Tpl">//<!--
				<tr>
					<td>
						{{row.startArea.name}}
					</td>
					<td>
						{{row.endArea.name}}
					</td>
					<td>
						{{row.starttime}}
					</td>
					<td>
						{{row.endtime}}
					</td>
					<td>
						{{row.price}}
					</td>
					<td>
						{{row.file}}
					</td>
					<td>
						{{row.remarks}}
					</td>
				</tr>//-->
	</script>
	<script type="text/template" id="testDataMainForm2Child2Tpl">//<!--
				<tr>
					<td>
						{{row.startArea.name}}
					</td>
					<td>
						{{row.endArea.name}}
					</td>
					<td>
						{{row.startTime}}
					</td>
					<td>
						{{row.price}}
					</td>
					<td>
						{{row.remarks}}
					</td>
				</tr>//-->
	</script>
	<script type="text/template" id="testDataMainForm2Child3Tpl">//<!--
				<tr>
					<td>
						{{row.startArea.name}}
					</td>
					<td>
						{{row.endArea.name}}
					</td>
					<td>
						{{row.price}}
					</td>
					<td>
						{{row.remarks}}
					</td>
				</tr>//-->
	</script>
