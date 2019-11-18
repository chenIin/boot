/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.onetomany.dialog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.test.onetomany.dialog.entity.TestDataMainForm1;
import com.jeeplus.modules.test.onetomany.dialog.mapper.TestDataMainForm1Mapper;
import com.jeeplus.modules.test.onetomany.dialog.entity.TestDataChild11;
import com.jeeplus.modules.test.onetomany.dialog.mapper.TestDataChild11Mapper;
import com.jeeplus.modules.test.onetomany.dialog.entity.TestDataChild12;
import com.jeeplus.modules.test.onetomany.dialog.mapper.TestDataChild12Mapper;
import com.jeeplus.modules.test.onetomany.dialog.entity.TestDataChild13;
import com.jeeplus.modules.test.onetomany.dialog.mapper.TestDataChild13Mapper;

/**
 * 票务代理Service
 * @author liugf
 * @version 2019-08-13
 */
@Service
@Transactional(readOnly = true)
public class TestDataMainForm1Service extends CrudService<TestDataMainForm1Mapper, TestDataMainForm1> {

	@Autowired
	private TestDataChild11Mapper testDataChild11Mapper;
	@Autowired
	private TestDataChild12Mapper testDataChild12Mapper;
	@Autowired
	private TestDataChild13Mapper testDataChild13Mapper;
	
	public TestDataMainForm1 get(String id) {
		TestDataMainForm1 testDataMainForm1 = super.get(id);
		testDataMainForm1.setTestDataChild11List(testDataChild11Mapper.findList(new TestDataChild11(testDataMainForm1)));
		testDataMainForm1.setTestDataChild12List(testDataChild12Mapper.findList(new TestDataChild12(testDataMainForm1)));
		testDataMainForm1.setTestDataChild13List(testDataChild13Mapper.findList(new TestDataChild13(testDataMainForm1)));
		return testDataMainForm1;
	}
	
	public List<TestDataMainForm1> findList(TestDataMainForm1 testDataMainForm1) {
		return super.findList(testDataMainForm1);
	}
	
	public Page<TestDataMainForm1> findPage(Page<TestDataMainForm1> page, TestDataMainForm1 testDataMainForm1) {
		return super.findPage(page, testDataMainForm1);
	}
	
	@Transactional(readOnly = false)
	public void save(TestDataMainForm1 testDataMainForm1) {
		super.save(testDataMainForm1);
		for (TestDataChild11 testDataChild11 : testDataMainForm1.getTestDataChild11List()){
			if (testDataChild11.getId() == null){
				continue;
			}
			if (TestDataChild11.DEL_FLAG_NORMAL.equals(testDataChild11.getDelFlag())){
				if (StringUtils.isBlank(testDataChild11.getId())){
					testDataChild11.setTestDataMain(testDataMainForm1);
					testDataChild11.preInsert();
					testDataChild11Mapper.insert(testDataChild11);
				}else{
					testDataChild11.preUpdate();
					testDataChild11Mapper.update(testDataChild11);
				}
			}else{
				testDataChild11Mapper.delete(testDataChild11);
			}
		}
		for (TestDataChild12 testDataChild12 : testDataMainForm1.getTestDataChild12List()){
			if (testDataChild12.getId() == null){
				continue;
			}
			if (TestDataChild12.DEL_FLAG_NORMAL.equals(testDataChild12.getDelFlag())){
				if (StringUtils.isBlank(testDataChild12.getId())){
					testDataChild12.setTestDataMain(testDataMainForm1);
					testDataChild12.preInsert();
					testDataChild12Mapper.insert(testDataChild12);
				}else{
					testDataChild12.preUpdate();
					testDataChild12Mapper.update(testDataChild12);
				}
			}else{
				testDataChild12Mapper.delete(testDataChild12);
			}
		}
		for (TestDataChild13 testDataChild13 : testDataMainForm1.getTestDataChild13List()){
			if (testDataChild13.getId() == null){
				continue;
			}
			if (TestDataChild13.DEL_FLAG_NORMAL.equals(testDataChild13.getDelFlag())){
				if (StringUtils.isBlank(testDataChild13.getId())){
					testDataChild13.setTestDataMain(testDataMainForm1);
					testDataChild13.preInsert();
					testDataChild13Mapper.insert(testDataChild13);
				}else{
					testDataChild13.preUpdate();
					testDataChild13Mapper.update(testDataChild13);
				}
			}else{
				testDataChild13Mapper.delete(testDataChild13);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(TestDataMainForm1 testDataMainForm1) {
		super.delete(testDataMainForm1);
		testDataChild11Mapper.delete(new TestDataChild11(testDataMainForm1));
		testDataChild12Mapper.delete(new TestDataChild12(testDataMainForm1));
		testDataChild13Mapper.delete(new TestDataChild13(testDataMainForm1));
	}
	
}