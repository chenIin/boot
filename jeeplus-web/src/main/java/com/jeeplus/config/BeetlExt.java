/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.config;

/**
 * Created by jeeplus on 2017/7/6.
 */

import com.jeeplus.common.tag.AniMenuTag;
import com.jeeplus.common.tag.JPMenuTag;
import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.config.properties.JeePlusProperites;
import com.jeeplus.config.shiro.ShiroExt;
import com.jeeplus.modules.gencode.util.C2.C8;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.ext.web.SessionWrapper;
import org.beetl.ext.web.WebRenderExt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BeetlExt implements WebRenderExt {

    static long version = System.currentTimeMillis();


    @Override
    public void modify(Template template, GroupTemplate groupTemplate, HttpServletRequest request, HttpServletResponse response) {
        response.setBufferSize(1024*24);
        //js,css 的版本编号
        template.binding("version",version);
        template.binding("session", new SessionWrapper(request, request.getSession(false)));
        template.binding("request", request);
        template.binding("f", request.getContextPath()+ SpringContextHolder.getBean(JeePlusProperites.class).getFrontPath());
        template.binding("ctxPath", request.getContextPath());
        template.binding("ctx", request.getContextPath()+ SpringContextHolder.getBean(JeePlusProperites.class).getAdminPath());
        template.binding("ctxStatic", request.getContextPath()+"/static");
        groupTemplate.registerTag("menu", AniMenuTag.class);
        groupTemplate.registerTag("jpMenu", JPMenuTag.class);
        groupTemplate.registerFunctionPackage("fn",com.jeeplus.modules.sys.utils.UserUtils.class);
        groupTemplate.registerFunctionPackage("fn", JeePlusProperites.class);
        groupTemplate.registerFunctionPackage("fn",com.jeeplus.modules.sys.utils.DictUtils.class);
        groupTemplate.registerFunctionPackage("fn",com.jeeplus.common.utils.Encodes.class);
        groupTemplate.registerFunctionPackage("fn",com.jeeplus.common.utils.DateUtils.class);
        groupTemplate.registerFunctionPackage("fn",com.jeeplus.core.mapper.JsonMapper.class);
        groupTemplate.registerFunctionPackage("fn",com.jeeplus.common.utils.StringUtils.class);
        groupTemplate.registerFunctionPackage("fn",C8.class);
        groupTemplate.registerFunctionPackage("fn",com.jeeplus.common.utils.CookieUtils.class);
        groupTemplate.registerFunctionPackage("fn",com.jeeplus.modules.sys.utils.ConfigUtils.class);
        groupTemplate.registerFunctionPackage("shiro",new ShiroExt());
    }

}
