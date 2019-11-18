/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.layim.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.layim.entity.LayGroupUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 群组MAPPER接口
 *
 * @author lgf
 * @version 2016-08-07
 */
@Mapper
@Repository
public interface LayGroupUserMapper extends BaseMapper<LayGroupUser> {


}