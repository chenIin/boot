/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.activiti.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.test.activiti.entity.TestActivitiAudit;
import com.jeeplus.modules.test.activiti.mapper.TestActivitiAuditMapper;

/**
 * 薪酬调整申请Service
 * @author liugf
 * @version 2019-08-13
 */
@Service
@Transactional(readOnly = true)
public class TestActivitiAuditService extends CrudService<TestActivitiAuditMapper, TestActivitiAudit> {

	public TestActivitiAudit get(String id) {
		return super.get(id);
	}
	
	public List<TestActivitiAudit> findList(TestActivitiAudit testActivitiAudit) {
		return super.findList(testActivitiAudit);
	}
	
	public Page<TestActivitiAudit> findPage(Page<TestActivitiAudit> page, TestActivitiAudit testActivitiAudit) {
		return super.findPage(page, testActivitiAudit);
	}
	
	@Transactional(readOnly = false)
	public void save(TestActivitiAudit testActivitiAudit) {
		super.save(testActivitiAudit);
	}
	
	@Transactional(readOnly = false)
	public void delete(TestActivitiAudit testActivitiAudit) {
		super.delete(testActivitiAudit);
	}
	
}