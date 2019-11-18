/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.onetomany.form.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.test.onetomany.form.entity.TestDataMainForm2;
import com.jeeplus.modules.test.onetomany.form.mapper.TestDataMainForm2Mapper;
import com.jeeplus.modules.test.onetomany.form.entity.TestDataChild21;
import com.jeeplus.modules.test.onetomany.form.mapper.TestDataChild21Mapper;
import com.jeeplus.modules.test.onetomany.form.entity.TestDataChild22;
import com.jeeplus.modules.test.onetomany.form.mapper.TestDataChild22Mapper;
import com.jeeplus.modules.test.onetomany.form.entity.TestDataChild23;
import com.jeeplus.modules.test.onetomany.form.mapper.TestDataChild23Mapper;

/**
 * 票务代理Service
 * @author liugf
 * @version 2019-08-13
 */
@Service
@Transactional(readOnly = true)
public class TestDataMainForm2Service extends CrudService<TestDataMainForm2Mapper, TestDataMainForm2> {

	@Autowired
	private TestDataChild21Mapper testDataChild21Mapper;
	@Autowired
	private TestDataChild22Mapper testDataChild22Mapper;
	@Autowired
	private TestDataChild23Mapper testDataChild23Mapper;
	
	public TestDataMainForm2 get(String id) {
		TestDataMainForm2 testDataMainForm2 = super.get(id);
		testDataMainForm2.setTestDataChild21List(testDataChild21Mapper.findList(new TestDataChild21(testDataMainForm2)));
		testDataMainForm2.setTestDataChild22List(testDataChild22Mapper.findList(new TestDataChild22(testDataMainForm2)));
		testDataMainForm2.setTestDataChild23List(testDataChild23Mapper.findList(new TestDataChild23(testDataMainForm2)));
		return testDataMainForm2;
	}
	
	public List<TestDataMainForm2> findList(TestDataMainForm2 testDataMainForm2) {
		return super.findList(testDataMainForm2);
	}
	
	public Page<TestDataMainForm2> findPage(Page<TestDataMainForm2> page, TestDataMainForm2 testDataMainForm2) {
		return super.findPage(page, testDataMainForm2);
	}
	
	@Transactional(readOnly = false)
	public void save(TestDataMainForm2 testDataMainForm2) {
		super.save(testDataMainForm2);
		for (TestDataChild21 testDataChild21 : testDataMainForm2.getTestDataChild21List()){
			if (testDataChild21.getId() == null){
				continue;
			}
			if (TestDataChild21.DEL_FLAG_NORMAL.equals(testDataChild21.getDelFlag())){
				if (StringUtils.isBlank(testDataChild21.getId())){
					testDataChild21.setTestDataMain(testDataMainForm2);
					testDataChild21.preInsert();
					testDataChild21Mapper.insert(testDataChild21);
				}else{
					testDataChild21.preUpdate();
					testDataChild21Mapper.update(testDataChild21);
				}
			}else{
				testDataChild21Mapper.delete(testDataChild21);
			}
		}
		for (TestDataChild22 testDataChild22 : testDataMainForm2.getTestDataChild22List()){
			if (testDataChild22.getId() == null){
				continue;
			}
			if (TestDataChild22.DEL_FLAG_NORMAL.equals(testDataChild22.getDelFlag())){
				if (StringUtils.isBlank(testDataChild22.getId())){
					testDataChild22.setTestDataMain(testDataMainForm2);
					testDataChild22.preInsert();
					testDataChild22Mapper.insert(testDataChild22);
				}else{
					testDataChild22.preUpdate();
					testDataChild22Mapper.update(testDataChild22);
				}
			}else{
				testDataChild22Mapper.delete(testDataChild22);
			}
		}
		for (TestDataChild23 testDataChild23 : testDataMainForm2.getTestDataChild23List()){
			if (testDataChild23.getId() == null){
				continue;
			}
			if (TestDataChild23.DEL_FLAG_NORMAL.equals(testDataChild23.getDelFlag())){
				if (StringUtils.isBlank(testDataChild23.getId())){
					testDataChild23.setTestDataMain(testDataMainForm2);
					testDataChild23.preInsert();
					testDataChild23Mapper.insert(testDataChild23);
				}else{
					testDataChild23.preUpdate();
					testDataChild23Mapper.update(testDataChild23);
				}
			}else{
				testDataChild23Mapper.delete(testDataChild23);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(TestDataMainForm2 testDataMainForm2) {
		super.delete(testDataMainForm2);
		testDataChild21Mapper.delete(new TestDataChild21(testDataMainForm2));
		testDataChild22Mapper.delete(new TestDataChild22(testDataMainForm2));
		testDataChild23Mapper.delete(new TestDataChild23(testDataMainForm2));
	}
	
}