package com.jeeplus.modules.sys.web;

import com.jeeplus.core.web.BaseController;
import com.jeeplus.plugin.PluginPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 机构Controller
 * @author jeeplus
 * @version 2016-5-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/plugin")
public class PluginController extends BaseController {
    @Autowired
    PluginPool pluginPool;

    @RequestMapping(value = {"list", ""})
    public String list(Model model) {
        return "modules/sys/plugin/index";
    }

    @ResponseBody
    @RequestMapping(value = "data")
    public  Map<String, Object>  data(Model model) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rows", pluginPool.getPlugins());
        map.put("total", pluginPool.getPlugins().size());
        return map;
    }


}
