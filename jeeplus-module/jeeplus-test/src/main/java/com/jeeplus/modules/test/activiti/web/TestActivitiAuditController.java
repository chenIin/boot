/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.activiti.web;

import java.util.List;
import java.util.Map;


import com.google.common.collect.Maps;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.act.service.ActProcessService;
import com.jeeplus.modules.act.service.ActTaskService;
import com.jeeplus.modules.test.activiti.entity.TestActivitiAudit;
import com.jeeplus.modules.test.activiti.service.TestActivitiAuditService;

/**
 * 薪酬调整申请Controller
 * @author liugf
 * @version 2019-08-13
 */
@Controller
@RequestMapping(value = "${adminPath}/test/activiti/testActivitiAudit")
public class TestActivitiAuditController extends BaseController {

	@Autowired
	private TestActivitiAuditService testActivitiAuditService;
	@Autowired
	private ActProcessService actProcessService;
	@Autowired
	private ActTaskService actTaskService;
	
	@ModelAttribute
	public TestActivitiAudit get(@RequestParam(required=false) String id) {
		TestActivitiAudit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = testActivitiAuditService.get(id);
		}
		if (entity == null){
			entity = new TestActivitiAudit();
		}
		return entity;
	}
	

	/**
	 * 查看，增加，编辑薪酬调整申请表单页面
	 */
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, TestActivitiAudit testActivitiAudit, Model model) {
		model.addAttribute("testActivitiAudit", testActivitiAudit);
		if("add".equals(mode) || "edit".equals(mode)){
			return "modules/test/activiti/testActivitiAuditForm";
		}else{//audit
			return "modules/test/activiti/testActivitiAuditAudit";
		}
	}
	
		
	/**
	 * 审批
	 *
	 * @param testActivitiAudit
	 */
	@ResponseBody
	@RequestMapping(value = "audit")
	public AjaxJson auditTask(TestActivitiAudit testActivitiAudit) {
		AjaxJson j = new AjaxJson();
		actTaskService.auditSave(testActivitiAudit.getAct());
		j.setMsg("审批成功");
		return j;
	}

	/**
	 * 保存薪酬调整申请
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(TestActivitiAudit testActivitiAudit, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(testActivitiAudit);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}


		/**
		 * 流程审批
		 */
		if (StringUtils.isBlank(testActivitiAudit.getId())){
			//新增或编辑表单保存
			testActivitiAuditService.save(testActivitiAudit);//保存
			// 启动流程
			ProcessDefinition p = actProcessService.getProcessDefinition(testActivitiAudit.getAct().getProcDefId());
			String title = testActivitiAudit.getCurrentUser().getName()+"在"+ DateUtils.getDateTime()+"发起"+p.getName();
			actTaskService.startProcess(p.getKey(),  "test_activiti_audit", testActivitiAudit.getId(), title);
			j.setMsg("发起流程审批成功!");
			j.getBody().put("targetUrl",  "/act/task/process/");
		}else{
			//新增或编辑表单保存
			testActivitiAuditService.save(testActivitiAudit);//保存
			testActivitiAudit.getAct().setComment(("yes".equals(testActivitiAudit.getAct().getFlag())?"[重新申请] ":"[销毁申请] "));
			// 完成流程任务
			Map<String, Object> vars = Maps.newHashMap();
			vars.put("reapply", "yes".equals(testActivitiAudit.getAct().getFlag())? true : false);
			actTaskService.complete(testActivitiAudit.getAct().getTaskId(), testActivitiAudit.getAct().getProcInsId(), testActivitiAudit.getAct().getComment(), testActivitiAudit.getContent(), vars);
			j.setMsg("提交成功！");
			j.getBody().put("targetUrl",  "/act/task/todo/");
		}

		return j;
	}
	


}