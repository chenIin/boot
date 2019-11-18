package com.jeeplus.modules.sys.utils;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.CookieUtils;
import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.sys.entity.DictValue;
import com.jeeplus.modules.sys.service.SysConfigService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ConfigUtils {

    public static String getProductName(){
        return SpringContextHolder.getBean(SysConfigService.class).get("1").getProductName();
    }

    public static String getLogo(){
        return SpringContextHolder.getBean(SysConfigService.class).get("1").getLogo();
    }

    public static String getDefaultTheme(){
        return SpringContextHolder.getBean(SysConfigService.class).get("1").getDefaultTheme();
    }

    public static String getDefaultLayout(){
        return SpringContextHolder.getBean(SysConfigService.class).get("1").getDefaultLayout();
    }

    public static String getHomeUrl(){
        String url = SpringContextHolder.getBean(SysConfigService.class).get("1").getHomeUrl();

        return StringUtils.isBlank(url)? "/home" : url;
    }


}
