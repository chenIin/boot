


<select id="${path}" name="${path}" readonly="${readonly!false}" class="${class!}  form-control">

    <% if(notAllowNull!false == false){ %>
    <option value="" label=""></option>
    <% } %>
    <%
        for(item in items){
    %>
    <option value="${item[itemValue]}" ${item[itemValue] == value!""?"selected":""} label="${item[itemLabel]}" ></option>

    <% }%>
</select>
<label id="${path!}-error" class="error" for="${path!}" style="display: none;"></label>