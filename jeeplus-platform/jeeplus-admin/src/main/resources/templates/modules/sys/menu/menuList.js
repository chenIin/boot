<script type="text/javascript">

var $treeTable=null;  
$(document).ready(function(){  
         
       $treeTable=$('#treeTable').treeTable({  
    	   theme:'vsStyle',	           
			expandLevel : 1,
			column:0,
			checkbox: false,
            url:'${ctx}/sys/menu/getChildren?parentId=',  
            callback:function(item) { 
            	
				item.isShow = (item.isShow == '1'?true:false);
                if(item.href&&item.href.length>4){
                 	item.hideFullName = item.href.substring(0,4)+"...";
            	}else if(item.href == undefined){
                	item.hideFullName = "";
				}else{
                    item.hideFullName = item.href;
                }
               if(item.permission&&item.permission.length>4){
				   item.hidePermission = item.permission.substring(0,4)+"...";
				}else if(item.permission == undefined){
                   item.hidePermission = "";
               }else{
				   item.hidePermission = item.permission;
			   }
				var menuItemTpl= $("#menuItemTpl").html();
                var result = laytpl(menuItemTpl).render(item);
                return result;
            },  
            beforeClick: function($treeTable, id) { 
                //异步获取数据 这里模拟替换处理  
                $treeTable.refreshPoint(id);  
            },  
            beforeExpand : function($treeTable, id) {     
              
            },  
            beforeClose : function($treeTable, id) {    
            
            }  
        });  
       
       
});  
		  
	  
	  
function sort(id,isUpSort){  
		
		var $ctr=$("tr[id='"+id+"']",$treeTable);  
        var $trs=null;  
        if($ctr.attr("pId")!=null)  
           $trs=$("tr[pId='"+$ctr.attr("pId")+"']",$treeTable);  
        else  
           $trs=$("tr[depth='1']",$treeTable);   
        if($trs.length>1) {
             //递归深度  
            if(isUpSort) {  
               if($trs[0].id==$ctr[0].id){
            	   jp.error('已经置顶了!');
            	   return;
               }  
               var i=0;  
               $trs.each(function(k){  
                   if(this.id==$ctr[0].id) i=k;  
               });  
               var $ptr=$($trs[i-1]);  
                 
                 
               //封装  
               var indexB=0,indexE=0,depth=parseInt($ctr.attr("depth"),10);  
                 
               var $alltrs=$("tr",$treeTable);  
               for(var k=0;k<$alltrs.length;k++)
               {  
                   if($alltrs[k].id==$ctr[0].id) {  
                       indexB=k;  
                   } else if(indexB>0&&parseInt($($alltrs[k]).attr("depth"),10)>depth) {  
                       indexE=k;  
                   } else if(indexB>0&&parseInt($($alltrs[k]).attr("depth"),10)<=depth) {  
                       break;  
                   }  
               }  
                 
               var selector="tr:eq("+indexB+")";  
               indexB++;  
               while(indexB<=indexE) {  
                   selector+=",tr:eq("+indexB+")";  
                   indexB++;  
               }  
               var moveNode = $(selector,$treeTable);
	  			   var targetNode = $ptr;
	  			   
	  			   var id1 = moveNode.attr("id");
	  			   var sort1 = parseInt(moveNode.attr("sort"));//交换排序值
	  			   var id2 = targetNode.attr("id");
  			   var sort2 = parseInt(targetNode.attr("sort"));
  			   if(sort1 > sort2){
  				   var c = sort1;
  				   sort1 = sort2;
  				   sort2 = c;
  			   }else if(sort1 == sort2){
  				   if(sort1 > 0){
  					 sort1 = sort1 -1;//向上移动，相等减1
  				   }else{
  					 sort2 = sort2 + 1;
  				   }
  				   
  			   }
               //交换  
                $.get("${ctx}/sys/menu/sort?id1="+id1+"&sort1="+sort1+"&id2="+id2+"&sort2="+sort2, function(data){
	    	  		if(data.success){
	    	  			moveNode.attr("sort", sort1);
	    	  			targetNode.attr("sort", sort2);
	    	  			moveNode.insertBefore(targetNode);  
	    	  			jp.success(data.msg);
	    	  		}else{
	    	  			jp.error(data.msg);
	    	  		}
	           
	               
	   	   		})
              
            }else {  
               if($trs[$trs.length-1].id==$ctr[0].id){
            	   jp.error('已经末端了!'); 
            	   return;
            	}  
               var i=0;  
               $trs.each(function(k){  
                   if(this.id==$ctr[0].id) i=k;  
               });  
               var $ntr=$($trs[i+1]);  
             
               //封装  
               var indexB=0,indexE=0,depth=parseInt($ntr.attr("depth"),10);  
                 
               var $alltrs=$("tr",$treeTable);  
               for(var k=0;k<$alltrs.length;k++)
               {  
                   if($alltrs[k].id==$ntr[0].id) {  
                       indexB=k;  
                   } else if(indexB>0&&parseInt($($alltrs[k]).attr("depth"),10)>depth) {  
                       indexE=k;  
                   } else if(indexB>0&&parseInt($($alltrs[k]).attr("depth"),10)<=depth) {  
                       break;  
                   }  
               }  
                 
               var selector="tr:eq("+indexB+")";  
               indexB++;  
               while(indexB<=indexE) {  
                   selector+=",tr:eq("+indexB+")";  
                   indexB++;  
               }  
               
               var moveNode = $(selector,$treeTable);
	  			   var targetNode = $ctr;
	  			   
	  			   var id1 = moveNode.attr("id");
  			   var sort1 = parseInt(targetNode.attr("sort"));//交换排序值
  			   var id2 = targetNode.attr("id");
  			   var sort2 = parseInt(moveNode.attr("sort"));
  			   
  			 	if(sort1 > sort2){
  				   var c = sort1;
  				   sort1 = sort2;
  				   sort2 = c;
  			   }else if(sort1 == sort2){
  				 sort2 = sort2 +1;//向下移动，相等加1
  			   }
  			   
             //交换  
              $.get("${ctx}/sys/menu/sort?id1="+id1+"&sort1="+sort1+"&id2="+id2+"&sort2="+sort2, function(data){
	    	  		if(data.success){
	    	  			moveNode.attr("sort", sort1);
	    	  			targetNode.attr("sort", sort2);
	    	  			moveNode.insertBefore(targetNode);  
	    	  			jp.success(data.msg)
	    	  		}else{
	    	  			jp.error(data.msg);
	    	  		}
	           
	               
	   	   		})
               
            }   
              
        }  
        
	   	  
	}
	  
	
	function del(con,id){  
		 jp.confirm('确认要删除菜单吗？', function(){
			jp.loading();
    	  	$.get("${ctx}/sys/menu/delete?id="+id, function(data){
    	  		if(data.success){
    	  			$treeTable.del(id);
    	  			jp.success(data.msg);
    	  		}else{
    	  			jp.error(data.msg);
    	  		}
    	  	})
    	   
    	});

	}  
        
	function refreshMenu(){
		jp.block("#treeTable");
		$treeTable.refresh();
		jp.unblock("#treeTable",500);
	}

	function refreshNode(data) {//刷新节点
        var current_id = data.body.menu.id;
        var target = $treeTable.get(current_id);
        var old_parent_id = target.attr("pid") == undefined?'1':target.attr("pid");
        var current_parent_id = data.body.menu.parentId;
        var current_parent_ids = data.body.menu.parentIds;

        if(old_parent_id == current_parent_id){
            if(current_parent_id == '1'){
                $treeTable.refreshPoint(-1);
            }else{
                $treeTable.refreshPoint(current_parent_id);
            }
        }else{
            $treeTable.del(current_id);//刷新删除旧节点
            $treeTable.initParents(current_parent_ids, "1");
        }
	}
	
	function setDataRule(id, name) {
		$("#left").attr("class", "col-sm-6");
		setTimeout(function(){
			$("#right").fadeIn(500);
		},50)
		$("#menuLabel").html(name);
		$("#menuId").val(id);
		$('#dataRuleTable').bootstrapTable("refresh",{query:{menuId:id}})
    }
    function hideDataRule() {
		$("#right").hide();
        $("#left").attr("class", "col-sm-12");
    }

</script>
<script type="text/html" id="menuItemTpl">
	<td>
	<% if(shiro.hasPermission("sys:menu:edit")) { %>
		<a href="#" onclick="jp.openSaveDialog('修改菜单', '${ctx}/sys/menu/form?id={{d.id}}','800px', '510px')"><i class="{{d.icon}}"></i> {{d.name}}</a>
	<% }else if(shiro.hasPermission("sys:menu:view")) { %>
		<a href="#" onclick="jp.openViewDialog('查看菜单', '${ctx}/sys/menu/form?id={{d.id}}','800px', '510px')"><i class="{{d.icon}}"></i> {{d.name}}</a>
    <% }else{%>
   	 <i class="{{d.icon}}"></i> {{d.name}}
	<%}%>
	</td>
	<td title="{{d.href}}">{{d.hideFullName}}</td>
	<td>
		<a href="javascript:;" onclick="sort('{{d.id}}',true);"><i class="fa  fa-long-arrow-up"></i></a>
        <a href="javascript:;" onclick="sort('{{d.id}}',false);"><i class="fa  fa-long-arrow-down"></i></a>
	</td>
	<td>
		{{# if(d.isShow){ }}<i class="fa fa-circle text-success ml5"></i>{{# }else{ }}
		<i class="fa fa-circle text-muted ml5"></i>{{# } }}
	</td>
	<td  title="{{d.permission}}">
		{{d.hidePermission}}
	</td>
	<td>
		<div class="btn-group dropdown">
				<% if(shiro.hasPermission("sys:role:datarule")) { %>
					<a href="javascript:setDataRule('{{d.id}}','{{d.name}}');"  class="btn btn-custom btn-xs waves-effect waves-light">数据规则</a>
				<% }%>
					<button type="button" class="btn btn-xs btn-custom dropdown-toggle waves-effect waves-light" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">更多</button>
					<ul class="dropdown-menu" x-placement="bottom-start" style="position: absolute; will-change: transform; top: 0px; left: 0px; transform: translate3d(102px, 35px, 0px);">
				<% if(shiro.hasPermission("sys:menu:view")) { %>
				<li><a href="#"  class="dropdown-item" onclick="jp.openViewDialog('查看菜单', '${ctx}/sys/menu/form?id={{d.id}}','800px', '600px')"><i class="fa fa-search-plus"></i> 查看</a></li>
					<% }%>
				<% if(shiro.hasPermission("sys:menu:edit")) { %>
				<li><a href="#"   class="dropdown-item" onclick="jp.openSaveDialog('修改菜单', '${ctx}/sys/menu/form?id={{d.id}}','800px', '600px')"><i class="fa fa-edit"></i> 修改</a></li>
					<% }%>
				<% if(shiro.hasPermission("sys:menu:del")) { %>
				<li><a  href="#"  class="dropdown-item" onclick="return del(this, '{{d.id}}')"><i class="fa fa-trash"></i> 删除</a></li>
					<% }%>
				<% if(shiro.hasPermission("sys:menu:add")) { %>
				<li><a href="#"  class="dropdown-item" onclick="jp.openSaveDialog('添加下级菜单', '${ctx}/sys/menu/form?parent.id={{d.id}}','800px', '600px')"><i class="fa fa-plus"></i> 添加下级菜单</a></li>
					<% }%>
				</ul>
				</div>
		</div>
  	</td>
</script>