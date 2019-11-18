<script>
    $(document).ready(function() {
        $(document.getElementById("${path}")).select2({
            theme: "bootstrap"
        });
    });
</script>


<select id="${path}" name="${path}" readonly="${readonly!false}" class="select2">

    <% if(notAllowNull!false == false){ %>
    <option value="">--请选择--</option>
    <% } %>

    <%
        for(item in items){
    %>
    <option value="${item[itemValue]}" ${item[itemValue] == value!""?"selected=\"selected\"":""} >${item[itemLabel]}</option>

    <% }%>
</select>
<label id="${path!}-error" class="error" for="${path!}" style="display: none;"></label>