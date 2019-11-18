/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.onetomany.dialog.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.test.onetomany.dialog.entity.TestDataMainForm1;

/**
 * 票务代理MAPPER接口
 * @author liugf
 * @version 2019-08-13
 */
@Mapper
@Repository
public interface TestDataMainForm1Mapper extends BaseMapper<TestDataMainForm1> {
	
}