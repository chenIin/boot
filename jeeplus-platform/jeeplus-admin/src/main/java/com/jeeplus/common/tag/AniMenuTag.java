package com.jeeplus.common.tag;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.config.properties.JeePlusProperites;
import com.jeeplus.modules.sys.entity.Menu;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.beetl.core.GeneralVarTagBinding;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * 
 * 类描述：菜单标签
 * 
 * 刘高峰
 * 
 * @date： 日期：2019-1-23 时间：上午10:17:45
 * 
 * @version 1.0
 */
public class AniMenuTag extends GeneralVarTagBinding {



	@Override
	public void render(){
		StringBuffer sb = new StringBuffer();
		Menu menu = (Menu)getAttributeValue("value");
		sb.append(getChildOfTree(menu, 0, UserUtils.getMenuList()));

		try{
			ctx.byteWriter.writeString(sb.toString());
		}catch (IOException e){
			throw new RuntimeException(e);
		}
	}

	private static String encodeURIComponent(String href){
		try{
			return href = URLEncoder.encode(href, "UTF-8")
					.replaceAll("\\+", "%20")
					.replaceAll("\\!", "%21")
					.replaceAll("\\'", "%27")
					.replaceAll("\\(", "%28")
					.replaceAll("\\)", "%29")
					.replaceAll("\\~", "%7E");
		}catch (Exception e){
			return href;
		}

	}

	private static String getChildOfTree(Menu menuItem, int level, List<Menu> menuList) {
		StringBuffer menuString = new StringBuffer();
		String href = "";
		if (!menuItem.hasPermisson())
			return "";
		if (level > 0) {// level 为0是功能菜单

			ServletContext context = SpringContextHolder.getBean(ServletContext.class);
			if (menuItem.getHref() != null && menuItem.getHref().length() > 0) {// 如果是子节点

				if (menuItem.getHref().startsWith("http://") || menuItem.getHref().startsWith("https://")) {// 如果是互联网资源
					href = menuItem.getHref();
				} else if (menuItem.getHref().endsWith(".html") ) {// 如果是静态资源并且不是ckfinder.html，直接访问不加adminPath
					href = context.getContextPath() + menuItem.getHref();
				} else if (menuItem.getHref().startsWith("ref:") ) {// 如果是静态资源并且不是ckfinder.html，直接访问不加adminPath
					href = context.getContextPath() + menuItem.getHref().substring(4);
				} else {
					href = context.getContextPath() + SpringContextHolder.getBean(JeePlusProperites.class).getAdminPath() + menuItem.getHref();
				}
				if("blank".equals(menuItem.getTarget())){
					menuString.append("<li><a class=\"J_menuItem\" href=\"" + href + "\" target=\"_blank\">" + (StringUtils.isBlank(menuItem.getIcon())?"":("<i class=\"fa " + menuItem.getIcon() + "\"></i>"))+"<span> "
							+ menuItem.getName() + " </span></a></li>\n");
				}else{
					menuString.append("<li><a class=\"J_menuItem\"  href=\"" + href +"\"  >"+ (StringUtils.isBlank(menuItem.getIcon())?"":("<i class=\"fa " + menuItem.getIcon() + "\"></i>"))+"<span> "
							+ menuItem.getName() + " </span></a></li>\n");
				}
				
			}
		}

		if ((menuItem.getHref() == null || menuItem.getHref().trim().equals("")) && menuItem.getIsShow().equals("1")) {// 如果是父节点且显示
			if (level == 0) {// 如果是功能菜单
				for (Menu child : menuList) {
					if (child.getParentId().equals(menuItem.getId()) && child.getIsShow().equals("1")) {
						menuString.append(getChildOfTree(child, level + 1, menuList));
					}
				}
			}

			if (level > 0) {// 不是功能菜单
				menuString.append("<li class=\"has-sub\">\n");
				menuString.append(" <a href=\"javascript:;\" class=\"waves-effect\">  "+ (StringUtils.isBlank(menuItem.getIcon())?"":("<i class=\"fa " + menuItem.getIcon() + "\"></i>"))+"<span> "
						+ menuItem.getName() + " </span><span class=\"menu-arrow\"></span></a>\n");
				menuString.append(
						"  <ul>");

				for (Menu child : menuList) {
					if (child.getParentId().equals(menuItem.getId()) && child.getIsShow().equals("1")) {
						menuString.append(getChildOfTree(child, level + 1, menuList));
					}
				}
				menuString.append("</ul>\n");
				menuString.append("</li>\n");
			}

		}

		return menuString.toString();
	}

}
