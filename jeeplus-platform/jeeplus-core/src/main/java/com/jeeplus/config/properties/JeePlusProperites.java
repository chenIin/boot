/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.config.properties;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * 全局配置类
 * @author jeeplus
 * @version 2017-06-25
 */
@Configuration
public class JeePlusProperites implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(JeePlusProperites.class);

	@Value("${version}")
	private String version;

	@Value("${demoMode}")
	private String demoMode;

	@Value("${spring.cache.type}")
	private String cacheType;

	public String getCacheType() {
		return cacheType;
	}

	public void setCacheType(String cacheType) {
		this.cacheType = cacheType;
	}

	public String getWebStaticFile() {
		return webStaticFile;
	}

	public void setWebStaticFile(String webStaticFile) {
		this.webStaticFile = webStaticFile;
	}

	@Value("${web.staticFile}")
	private String webStaticFile;


	 public static JeePlusProperites newInstance() {

		return SpringContextHolder.getBean(JeePlusProperites.class);
	}

	@Value("${urlSuffix}")
	private String urlSuffix;


	@Value("${adminPath}")
	private String adminPath;

	@Value("${frontPath}")
	private String frontPath;


	public void setUrlSuffix(String urlSuffix) {
		this.urlSuffix = urlSuffix;
	}

	public void setAdminPath(String adminPath) {
		this.adminPath = adminPath;
	}
	public void setFrontPath(String frontPath) {
		this.frontPath = frontPath;
	}

	public String getUserfilesBasedir() {
		return userfilesBasedir;
	}

	public void setUserfilesBasedir(String userfilesBasedir) {
		this.userfilesBasedir = userfilesBasedir;
	}

	@Value("${userfiles.basedir}")
	private String userfilesBasedir;





	/**
	 * 是/否
	 */
	public static final String YES = "1";
	public static final String NO = "0";

	/**
	 * 对/错
	 */
	public static final String TRUE = "true";
	public static final String FALSE = "false";


	/**
	 * 显示/隐藏
	 */
	public static final String SHOW = "1";
	public static final String HIDE = "0";


	/**
	 * 上传文件基础虚拟路径
	 */
	public static final String USERFILES_BASE_URL = "/userfiles/";

	/**
	 * 获取前端根路径
	 */
	public  String getFrontPath() {
		return this.frontPath;
	}
	
	/**
	 * 获取URL后缀
	 */
	public  String getUrlSuffix() {
		return this.urlSuffix;
	}
	

	/**
	 * 页面获取常量
	 */
	public static Object getConst(String field) {
		try {
			return JeePlusProperites.class.getField(field).get(null);
		} catch (Exception e) {
			// 异常代表无配置，这里什么也不做
		}
		return null;
	}

	/**
	 * 获取上传文件的根目录
	 * @return
	 */
	public String getUserfilesBaseDir() {
		String dir = this.userfilesBasedir;
		if (StringUtils.isBlank(dir)){
			try {
				return new File(this.getClass().getResource("/").getPath())
						.getParentFile().getParentFile().getParentFile().getCanonicalPath();
			}catch (Exception e){
				return "";
			}
		}
		if(!dir.endsWith("/")) {
			dir += "/";
		}

//		System.out.println("userfiles.basedir: " + dir);
		return dir;
	}
	

    
	/**
	 * 获取管理端根路径
	 */
	public  String getAdminPath() {
		return this.adminPath;
	}

	/**
	 * 是否是演示模式，演示模式下不能修改用户、角色、密码、菜单、授权
	 */
	public  Boolean isDemoMode() {
		String dm = this.demoMode;
		return "true".equals(dm) || "1".equals(dm);
	}


	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}


	public void setDemoMode(String demoMode) {
		this.demoMode = demoMode;
	}



}
