/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.onetomany.form.web;

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
import com.jeeplus.modules.test.onetomany.form.entity.TestDataMainForm2;
import com.jeeplus.modules.test.onetomany.form.service.TestDataMainForm2Service;

/**
 * 票务代理Controller
 * @author liugf
 * @version 2019-08-13
 */
@Controller
@RequestMapping(value = "${adminPath}/test/onetomany/form/testDataMainForm2")
public class TestDataMainForm2Controller extends BaseController {

	@Autowired
	private TestDataMainForm2Service testDataMainForm2Service;
	
	@ModelAttribute
	public TestDataMainForm2 get(@RequestParam(required=false) String id) {
		TestDataMainForm2 entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = testDataMainForm2Service.get(id);
		}
		if (entity == null){
			entity = new TestDataMainForm2();
		}
		return entity;
	}
	
	/**
	 * 票务代理列表页面
	 */
	@RequiresPermissions("test:onetomany:form:testDataMainForm2:list")
	@RequestMapping(value = {"list", ""})
	public String list(TestDataMainForm2 testDataMainForm2, Model model) {
		model.addAttribute("testDataMainForm2", testDataMainForm2);
		return "modules/test/onetomany/form/testDataMainForm2List";
	}
	
		/**
	 * 票务代理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("test:onetomany:form:testDataMainForm2:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(TestDataMainForm2 testDataMainForm2, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TestDataMainForm2> page = testDataMainForm2Service.findPage(new Page<TestDataMainForm2>(request, response), testDataMainForm2); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑票务代理表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"test:onetomany:form:testDataMainForm2:view","test:onetomany:form:testDataMainForm2:add","test:onetomany:form:testDataMainForm2:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, TestDataMainForm2 testDataMainForm2, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("testDataMainForm2", testDataMainForm2);
		return "modules/test/onetomany/form/testDataMainForm2Form";
	}

	/**
	 * 保存票务代理
	 */
	@ResponseBody
	@RequiresPermissions(value={"test:onetomany:form:testDataMainForm2:add","test:onetomany:form:testDataMainForm2:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(TestDataMainForm2 testDataMainForm2, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(testDataMainForm2);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		testDataMainForm2Service.save(testDataMainForm2);//保存
		j.setSuccess(true);
		j.setMsg("保存票务代理成功");
		return j;
	}

	
	/**
	 * 批量删除票务代理
	 */
	@ResponseBody
	@RequiresPermissions("test:onetomany:form:testDataMainForm2:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			testDataMainForm2Service.delete(testDataMainForm2Service.get(id));
		}
		j.setMsg("删除票务代理成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("test:onetomany:form:testDataMainForm2:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(TestDataMainForm2 testDataMainForm2, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "票务代理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<TestDataMainForm2> page = testDataMainForm2Service.findPage(new Page<TestDataMainForm2>(request, response, -1), testDataMainForm2);
    		new ExportExcel("票务代理", TestDataMainForm2.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出票务代理记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public TestDataMainForm2 detail(String id) {
		return testDataMainForm2Service.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("test:onetomany:form:testDataMainForm2:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<TestDataMainForm2> list = ei.getDataList(TestDataMainForm2.class);
			for (TestDataMainForm2 testDataMainForm2 : list){
				try{
					testDataMainForm2Service.save(testDataMainForm2);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条票务代理记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条票务代理记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入票务代理失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入票务代理数据模板
	 */
	@ResponseBody
	@RequiresPermissions("test:onetomany:form:testDataMainForm2:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "票务代理数据导入模板.xlsx";
    		List<TestDataMainForm2> list = Lists.newArrayList(); 
    		new ExportExcel("票务代理数据", TestDataMainForm2.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}