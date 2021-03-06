/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.tree.form.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.TreeMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.test.tree.form.entity.TestTree2;

/**
 * 组织机构MAPPER接口
 * @author lgf
 * @version 2019-08-13
 */
@Mapper
@Repository
public interface TestTree2Mapper extends TreeMapper<TestTree2> {
	
}