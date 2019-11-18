/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.activiti.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jeeplus.modules.act.entity.ActEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 请假申请Entity
 * @author liugf
 * @version 2019-08-13
 */
public class TestActivitiLeave extends ActEntity<TestActivitiLeave> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例ID
	private String leaveType;		// 请假类型
	private Date startTime;		// 请假开始时间
	private Date endTime;		// 请假结束时间
	private String reason;		// 报销事由
	
	public TestActivitiLeave() {
		super();
	}

	public TestActivitiLeave(String id){
		super(id);
	}

	@ExcelField(title="流程实例ID", align=2, sort=1)
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@ExcelField(title="请假类型", dictType="oa_leave_type", align=2, sort=2)
	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="请假开始时间不能为空")
	@ExcelField(title="请假开始时间", align=2, sort=3)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="请假结束时间不能为空")
	@ExcelField(title="请假结束时间", align=2, sort=4)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@ExcelField(title="报销事由", align=2, sort=5)
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
}