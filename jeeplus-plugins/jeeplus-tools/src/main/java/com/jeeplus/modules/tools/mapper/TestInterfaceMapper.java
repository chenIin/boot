/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.tools.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.tools.entity.TestInterface;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;;

/**
 * 接口MAPPER接口
 *
 * @author lgf
 * @version 2016-01-07
 */
@Mapper
@Repository
public interface TestInterfaceMapper extends BaseMapper<TestInterface> {

}