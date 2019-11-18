var uid=0;
class TreeSelect{
    static getUniquId(){
       return uid++;
    }
    constructor(options){
        var self=this;

        var defaultOptions={
            valueKey:'id'
        }
        self.options=options=$.extend(defaultOptions,options);
        var uid=TreeSelect.getUniquId();
        var name = options.name;
        var initValue = options.initValue;
        var initText = options.initText;
        var disabled = options.disabled;
        var tpl=`
            <input type="hidden" name="${name}" value="${initValue}" class="treeSelect-value"/>
            <input type="text" ${disabled} value="${initText}" class="jp-input treeSelect-input "/>
             <div class=" treeSelect-panel" id="treeSelect_panel_${uid}"></div>
        `;
        var ele=$(options.element);
        ele.html(tpl);
        var hiddenVm=ele.find('.treeSelect-value');
        var input=ele.find('.treeSelect-input');
        var panel=ele.find('.treeSelect-panel');
        self.element=ele;
        self.hiddenVm = hiddenVm;
        self.input=input;
        self.panel=panel;
        ele.css({
            'position':'relative'
        });
        input.on('keydown',function(){

            //input.val(self.text);
            return false;
        });
        input.click(function(){
            if(!self.isOpen()){
                self.open();
            }else{
                self.close();
            }

        });
        if(options.url){
            $.ajax({
                type: options.type,
                url: options.url,
                dataType:'json',
                data:options.param,
                sucess:function(data){
                    self.render(data);
                }
            })
        }else if(options.data){
            self.render(options.data);
        }
    }

    isOpen(){
        var panel=this.panel;
        return   !(panel.css('display')=='none'||panel.height()==0||panel.css('opacity')==0)
    }
    render(data){
        var self=this;
        var panel=self.panel;
       // self.ztree=$.fn.jstree.init(panel, setting);
        self.jstree = $(panel).jstree({
            'core': {
                "multiple": false,
                "animation": 0,
                "themes": {"icons": true, "stripes": false},
                'data':data
            },
            "conditionalselect": function (node, event) {
                return false;
            },
            'plugins': ['types', "wholerow", "search"],
            "types": {
                'default': {'icon': 'fa fa-folder-o'}
            }
        }).bind("activate_node.jstree", function (obj, e) {
            var node = $(panel).jstree(true).get_selected(true)[0];

            // if (node.original.type == "db") {
            //     nodeId = node.id;
            //     jp.post(ctx + "/gen/genTable/importTableData", {"dataSource.enName": node.original.enName}, function (data) {
            //
            //
            //         $('#table').bootstrapTable('load', data.rows);
            //     })
            // }
            if(node.children.length == 0){
                self.hiddenVm.val(node.id);
                self.input.val(node.text);
                self.value=node.id;
                self.text=node.text;
                self.close();
            }


        }).on('loaded.jstree', function () {
            $(panel).jstree('open_all');
        });
        ;

    }
    open(){
        var self=this;
        var panel=self.panel;
        panel.css({
            height:'auto',
            opacity:1
        });
        panel.show();
        self.mask=$('<div class="treeSelect-mask"></div>');
        $('body').append(self.mask);
        self.mask.click(function(){
            self.close();
        })
    }
    close(){

        var self=this;
        //panel.animate({
        //    height:0,
        //    opacity:0
        //},500);
        self.panel.hide();
        self.mask.remove();
    }

}