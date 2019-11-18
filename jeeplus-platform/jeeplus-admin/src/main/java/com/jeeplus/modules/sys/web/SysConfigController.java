/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.utils.sms.SmsUtils;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.sys.entity.SysConfig;
import com.jeeplus.modules.sys.service.SysConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统配置Controller
 * @author 刘高峰
 * @version 2018-10-18
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysConfig")
public class SysConfigController extends BaseController {

	@Autowired
	private SysConfigService sysConfigService;
	

	/**
	 * 系统配置列表页面
	 */
	@RequiresPermissions("sys:sysConfig:index")
	@RequestMapping(value = {"index", ""})
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		SysConfig config = sysConfigService.get("1");
		model.addAttribute("config", config);
		return "modules/sys/config/sysConfig";
	}

	@ResponseBody
	@RequestMapping(value = "testSms")
	public AjaxJson testSms(@RequestParam("tel")String tel)throws ClientException {
		AjaxJson j = new AjaxJson();
		SendSmsResponse response =  SmsUtils.sendSms(tel,"{code:123}");
		if (response.getCode() != null && response.getCode().equals("OK")) {
			j.setMsg("短信发送成功!");
		}else {
			j.setSuccess(false);
			j.setMsg("短信发送失败!"+ response.getMessage());
		}
		return j;
	}
	/**
	 * 保存系统配置
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(SysConfig config, Model model)throws Exception {
		AjaxJson j = new AjaxJson();
		if(jeePlusProperites.isDemoMode()){
			j.setSuccess(false);
			j.setMsg("演示模式，禁止修改!");
			return j;
		}
		String message = "保存系统配置成功";
		if(config.getMultiAccountLogin() == null){
			config.setMultiAccountLogin("0");
		}
		SysConfig target = sysConfigService.get("1");
		MyBeanUtils.copyBeanNotNull2Bean(config, target);
		sysConfigService.save(target);
		j.setSuccess(true);
		j.setMsg(message);
		return j;
	}

}