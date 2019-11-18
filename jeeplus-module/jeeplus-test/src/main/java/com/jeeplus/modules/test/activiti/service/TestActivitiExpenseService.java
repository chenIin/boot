/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.activiti.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.test.activiti.entity.TestActivitiExpense;
import com.jeeplus.modules.test.activiti.mapper.TestActivitiExpenseMapper;

/**
 * 报销申请Service
 * @author liugf
 * @version 2019-08-13
 */
@Service
@Transactional(readOnly = true)
public class TestActivitiExpenseService extends CrudService<TestActivitiExpenseMapper, TestActivitiExpense> {

	public TestActivitiExpense get(String id) {
		return super.get(id);
	}
	
	public List<TestActivitiExpense> findList(TestActivitiExpense testActivitiExpense) {
		return super.findList(testActivitiExpense);
	}
	
	public Page<TestActivitiExpense> findPage(Page<TestActivitiExpense> page, TestActivitiExpense testActivitiExpense) {
		return super.findPage(page, testActivitiExpense);
	}
	
	@Transactional(readOnly = false)
	public void save(TestActivitiExpense testActivitiExpense) {
		super.save(testActivitiExpense);
	}
	
	@Transactional(readOnly = false)
	public void delete(TestActivitiExpense testActivitiExpense) {
		super.delete(testActivitiExpense);
	}
	
}