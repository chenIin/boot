<%
    for(item in items){
%>

<div class="checkbox checkbox-custom">
    <input id="${path}${itemLP.index}" name="${path!}" type="checkbox" value="${item[itemValue]}"
           <%
             var all_value = ",";
             var item_value = "," + item[itemValue] + ",";
             if(isNotEmpty(values)){
             	var t = type.name(values);
             	if("String"==t){
             		values = strutil.split(values,",");             		
             	}
	            for(d in values){
	            	all_value =  all_value + d +",";
               	};
             }
           %>
           <% if(strutil.contain(all_value, item_value) == true){ %>
            checked
            <% } %>
          class="${class!}"/>
    <label for="${path}${itemLP.index}">${item[itemLabel]}</label>
</div>

<% }%>

<input type="hidden" name="${path!}" >
<label id="${path!}-error" class="error" for="${path!}" style="display: none;"></label>