/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.one.form.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.test.one.form.entity.LeaveForm2;
import com.jeeplus.modules.test.one.form.mapper.LeaveForm2Mapper;

/**
 * 请假表单Service
 * @author lgf
 * @version 2019-08-13
 */
@Service
@Transactional(readOnly = true)
public class LeaveForm2Service extends CrudService<LeaveForm2Mapper, LeaveForm2> {

	public LeaveForm2 get(String id) {
		return super.get(id);
	}
	
	public List<LeaveForm2> findList(LeaveForm2 leaveForm2) {
		return super.findList(leaveForm2);
	}
	
	public Page<LeaveForm2> findPage(Page<LeaveForm2> page, LeaveForm2 leaveForm2) {
		return super.findPage(page, leaveForm2);
	}
	
	@Transactional(readOnly = false)
	public void save(LeaveForm2 leaveForm2) {
		super.save(leaveForm2);
	}
	
	@Transactional(readOnly = false)
	public void delete(LeaveForm2 leaveForm2) {
		super.delete(leaveForm2);
	}
	
}