/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.act.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.act.entity.Act;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 审批Mapper接口
 *
 * @author jeeplus
 * @version 2017-05-16
 */
@Mapper
@Repository
public interface ActMapper extends BaseMapper<Act> {

    public int updateProcInsIdByBusinessId(Act act);

}
