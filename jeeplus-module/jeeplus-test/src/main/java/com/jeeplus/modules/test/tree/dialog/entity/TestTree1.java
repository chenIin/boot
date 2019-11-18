/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.tree.dialog.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.jeeplus.core.persistence.TreeEntity;

/**
 * 组织机构Entity
 * @author lgf
 * @version 2019-08-13
 */
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class TestTree1 extends TreeEntity<TestTree1> {
	
	private static final long serialVersionUID = 1L;
	
	
	public TestTree1() {
		super();
	}

	public TestTree1(String id){
		super(id);
	}

	public  TestTree1 getParent() {
			return parent;
	}
	
	@Override
	public void setParent(TestTree1 parent) {
		this.parent = parent;
		
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}