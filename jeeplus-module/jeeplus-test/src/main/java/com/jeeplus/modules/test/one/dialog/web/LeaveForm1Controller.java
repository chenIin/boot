/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.one.dialog.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.test.one.dialog.entity.LeaveForm1;
import com.jeeplus.modules.test.one.dialog.service.LeaveForm1Service;

/**
 * 请假表单Controller
 * @author lgf
 * @version 2019-08-13
 */
@Controller
@RequestMapping(value = "${adminPath}/test/one/dialog/leaveForm1")
public class LeaveForm1Controller extends BaseController {

	@Autowired
	private LeaveForm1Service leaveForm1Service;
	
	@ModelAttribute
	public LeaveForm1 get(@RequestParam(required=false) String id) {
		LeaveForm1 entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = leaveForm1Service.get(id);
		}
		if (entity == null){
			entity = new LeaveForm1();
		}
		return entity;
	}
	
	/**
	 * 请假表单列表页面
	 */
	@RequiresPermissions("test:one:dialog:leaveForm1:list")
	@RequestMapping(value = {"list", ""})
	public String list(LeaveForm1 leaveForm1, Model model) {
		model.addAttribute("leaveForm1", leaveForm1);
		return "modules/test/one/dialog/leaveForm1List";
	}
	
		/**
	 * 请假表单列表数据
	 */
	@ResponseBody
	@RequiresPermissions("test:one:dialog:leaveForm1:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(LeaveForm1 leaveForm1, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LeaveForm1> page = leaveForm1Service.findPage(new Page<LeaveForm1>(request, response), leaveForm1); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑请假表单表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"test:one:dialog:leaveForm1:view","test:one:dialog:leaveForm1:add","test:one:dialog:leaveForm1:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, LeaveForm1 leaveForm1, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("leaveForm1", leaveForm1);
		return "modules/test/one/dialog/leaveForm1Form";
	}

	/**
	 * 保存请假表单
	 */
	@ResponseBody
	@RequiresPermissions(value={"test:one:dialog:leaveForm1:add","test:one:dialog:leaveForm1:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(LeaveForm1 leaveForm1, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(leaveForm1);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		leaveForm1Service.save(leaveForm1);//保存
		j.setSuccess(true);
		j.setMsg("保存请假表单成功");
		return j;
	}
	
	
	/**
	 * 批量删除请假表单
	 */
	@ResponseBody
	@RequiresPermissions("test:one:dialog:leaveForm1:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			leaveForm1Service.delete(leaveForm1Service.get(id));
		}
		j.setMsg("删除请假表单成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("test:one:dialog:leaveForm1:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(LeaveForm1 leaveForm1, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "请假表单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<LeaveForm1> page = leaveForm1Service.findPage(new Page<LeaveForm1>(request, response, -1), leaveForm1);
    		new ExportExcel("请假表单", LeaveForm1.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出请假表单记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("test:one:dialog:leaveForm1:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<LeaveForm1> list = ei.getDataList(LeaveForm1.class);
			for (LeaveForm1 leaveForm1 : list){
				try{
					leaveForm1Service.save(leaveForm1);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条请假表单记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条请假表单记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入请假表单失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入请假表单数据模板
	 */
	@ResponseBody
	@RequiresPermissions("test:one:dialog:leaveForm1:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "请假表单数据导入模板.xlsx";
    		List<LeaveForm1> list = Lists.newArrayList(); 
    		new ExportExcel("请假表单数据", LeaveForm1.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}