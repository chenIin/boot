/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.activiti.entity;

import com.jeeplus.modules.sys.entity.User;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.sys.entity.Office;

import com.jeeplus.modules.act.entity.ActEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 报销申请Entity
 * @author liugf
 * @version 2019-08-13
 */
public class TestActivitiExpense extends ActEntity<TestActivitiExpense> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 员工姓名
	private String procInsId;		// 流程实例ID
	private Integer cost;		// 报销费用
	private Office office;		// 归属部门
	private String reason;		// 报销事由
	
	public TestActivitiExpense() {
		super();
	}

	public TestActivitiExpense(String id){
		super(id);
	}

	@NotNull(message="员工姓名不能为空")
	@ExcelField(title="员工姓名", fieldType=User.class, value="user.name", align=2, sort=1)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@ExcelField(title="流程实例ID", align=2, sort=2)
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@NotNull(message="报销费用不能为空")
	@ExcelField(title="报销费用", align=2, sort=3)
	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}
	
	@NotNull(message="归属部门不能为空")
	@ExcelField(title="归属部门", fieldType=Office.class, value="office.name", align=2, sort=4)
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	@ExcelField(title="报销事由", align=2, sort=5)
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
}