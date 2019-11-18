<div class="radio radio-inline radio-info form-check form-check-inline mr-0">
    <%
        for(item in items){
    %>
        <input name="${path}" id="${path}${itemLP.index}" ${item.value == value! ?"checked":""} type="radio"  class="form-check-input m-r-10"  value="${item.value}"/>
        <label for="${path}${itemLP.index}" class="form-check-label">${item[itemLabel]}</label>
    <% }%>
</div>
