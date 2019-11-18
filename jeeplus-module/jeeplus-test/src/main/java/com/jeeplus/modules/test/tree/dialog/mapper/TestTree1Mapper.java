/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.tree.dialog.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.TreeMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.test.tree.dialog.entity.TestTree1;

/**
 * 组织机构MAPPER接口
 * @author lgf
 * @version 2019-08-13
 */
@Mapper
@Repository
public interface TestTree1Mapper extends TreeMapper<TestTree1> {
	
}