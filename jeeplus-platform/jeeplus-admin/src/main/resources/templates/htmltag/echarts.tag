
<div id="${id}" style="min-height:20px;width: ${width!'100%'};height: ${height!'100%'}"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例4

    $(function () {
        var ${id}Charts= echarts.init(document.getElementById('${id}'));
        window.onresize =  ${id}Charts.resize;
        jp.block("#${id}")
        jp.get("${url}", function (option) {
            // 指定图表的配置项和数据
            // 使用刚指定的配置项和数据显示图表。
            ${id}Charts.setOption(option);
           jp.unblock("#${id}")
        })
    })
</script>
