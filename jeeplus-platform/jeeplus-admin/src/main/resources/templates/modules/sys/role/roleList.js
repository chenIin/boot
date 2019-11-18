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
		                 //是否显示行间隔色
		                striped: false,
		                //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）     
		                cache: false,
				        showSearch: true,
						//允许列拖动大小
						resizable: true,
						//固定表头
						stickyHeader: true,
						stickyHeaderOffsetY: 0,
						//显示刷新按钮
						showRefresh: true,
						//显示切换手机试图按钮
						showToggle: true,
						//显示 内容列下拉框
						showColumns: true,
						//显示切换分页按钮
		                //是否显示分页（*）  
		                pagination: false,   
		                //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据  
		                url: "${ctx}/sys/role/data",
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
							$("#search-collapse").slideToggle();
						},
		                //分页方式：client客户端分页，server服务端分页（*）
		                sidePagination: "server",
		                columns: [{
					        checkbox: true
					       
					    }, {
					        field: 'name',
					        title: '角色名称',
					        formatter:function(value, row, index){
					        	return '<a  href="#" onclick="jp.openViewDialog(\'查看角色\', \'${ctx}/sys/role/form?id='+row.id+'\',\'800px\', \'500px\')">'+value+'</a>'
					        }
					       
					    }, {
					        field: 'enname',
					        title: '英文名称'
					    }, {
					        field: 'office.name',
					        title: '归属机构'
					    }, {
					        field: 'useable',
					        title: '状态',
					        formatter:function(value, row , index){
					        	return value=="0" ? '<font  class="badge badge-danger">禁用</font>':'<font class="badge badge-success">正常</font>';
					        }
					    }, {
	                        field: 'operate',
	                        title: '操作',
	                        align: 'center',
                            class: 'text-nowrap',
	                        events: {
	            		        'click .view': function (e, value, row, index) {
	            		        	jp.openViewDialog('查看角色', '${ctx}/sys/role/form?id=' + row.id,'800px', '500px');
	            		        },
	            		        'click .edit': function (e, value, row, index) {
	            		        	jp.openSaveDialog('编辑角色', '${ctx}/sys/role/form?id=' + row.id,'800px', '500px');
	            		        },
	            		        'click .del': function (e, value, row, index) {
	            		        	del(row.id);
	            		        },
	            		        'click .auth': function (e, value, row, index) {
	            		        	jp.openSaveDialog('权限设置', '${ctx}/sys/role/auth?id=' + row.id,'350px', '700px');
	            		        },
	            		        'click .assign': function (e, value, row, index) {

	            					showAssign(row);
	            		        }
	            		    },
	                        formatter:  function operateFormatter(value, row, index) {
	            		        return [
                                    <% if(shiro.hasPermission("sys:role:view")){ %>
	        						'<a href="#" class="view btn btn-custom btn-xs waves-effect waves-custom m-r-5">查看</a>',
	        						<% } %>
                                    <% if(shiro.hasPermission("sys:role:edit")){ %>
	        						<%  if(role.sysData == fn.getDictValue('是', 'yes_no', '1') && fn.getUser().admin|| role.sysData != fn.getDictValue('是', 'yes_no', '1')){ %>
	        							'<a href="#" class="edit btn btn-success btn-xs waves-effect waves-success m-r-5">修改</a> ',
                                	<% } %>
                                	<% } %>
                               		 <% if(shiro.hasPermission("sys:role:del")){ %>
	        						    '<a href="#" class="del btn btn-danger btn-xs waves-effect waves-danger m-r-5">删除</a> ',
									<% } %>
									<% if(shiro.hasPermission("sys:role:assign")){ %>
	        							'<a href="#" class="auth btn btn-info btn-xs waves-effect waves-info m-r-5">角色授权</a>',
									<% } %>
									<% if(shiro.hasPermission("sys:role:assign")){ %>
	        							'<a href="#" class="assign btn btn-primary btn-xs waves-effect waves-custom">分配用户</a>'
									<% } %>
	            		        ].join('');
	            		    }
	                    }]
					
					});
				

				  $('#table').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
			                'check-all.bs.table uncheck-all.bs.table', function () {
			            $('#remove').prop('disabled', ! $('#table').bootstrapTable('getSelections').length);
			            $('#edit, #auth').prop('disabled', $('#table').bootstrapTable('getSelections').length!=1);
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
		  
		  function add(){
			  jp.openSaveDialog('新建角色', '${ctx}/sys/role/form','800px', '500px')
		  }
		  
		  function edit(id){
			  if(!id){
					id = getIdSelections();
				}
			  jp.openSaveDialog('编辑角色', "${ctx}/sys/role/form?id=" + id,'800px', '500px')
			  
		  }
		  function del(ids){
				if(!ids){
					ids = getIdSelections();
				}
				jp.confirm('确认要删除选中角色吗？',  function(){
					jp.loading();
             	  	jp.get("${ctx}/sys/role/delete?ids=" + ids, function(data){
             	  		if(data.success){
             	  			$('#table').bootstrapTable('refresh');
            	  			jp.success(data.msg);
            	  		}else{
            	  			jp.error(data.msg);
            	  		}
             	  	})
				})
		  }

		  function refresh() {
              $('#table').bootstrapTable('refresh');
          }

          function auth(id) {
              if(!id){
                  id = getIdSelections();
              }
              jp.openSaveDialog('权限设置', '${ctx}/sys/role/auth?id=' + id,'350px', '700px');
          }
          function showAssign(row) {
              $("#left").attr("class", "col-sm-6");
              setTimeout(function(){
                  $("#right").fadeIn(500);
              },50)
              $("#roleLabel").html(row.name);
              $("#roleId").val(row.id);
              $('#userTable').bootstrapTable("refresh",{query:{id:row.id}})
          }
          function hideAssign() {
              $("#right").hide();
              $("#left").attr("class", "col-sm-12");
          }
	</script>