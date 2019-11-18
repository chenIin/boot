/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.one.dialog.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.test.one.dialog.entity.LeaveForm1;
import com.jeeplus.modules.test.one.dialog.mapper.LeaveForm1Mapper;

/**
 * 请假表单Service
 * @author lgf
 * @version 2019-08-13
 */
@Service
@Transactional(readOnly = true)
public class LeaveForm1Service extends CrudService<LeaveForm1Mapper, LeaveForm1> {

	public LeaveForm1 get(String id) {
		return super.get(id);
	}
	
	public List<LeaveForm1> findList(LeaveForm1 leaveForm1) {
		return super.findList(leaveForm1);
	}
	
	public Page<LeaveForm1> findPage(Page<LeaveForm1> page, LeaveForm1 leaveForm1) {
		return super.findPage(page, leaveForm1);
	}
	
	@Transactional(readOnly = false)
	public void save(LeaveForm1 leaveForm1) {
		super.save(leaveForm1);
	}
	
	@Transactional(readOnly = false)
	public void delete(LeaveForm1 leaveForm1) {
		super.delete(leaveForm1);
	}
	
}