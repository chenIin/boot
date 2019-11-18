/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.shop.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.TreeMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.test.shop.entity.Category;

/**
 * 商品类型MAPPER接口
 * @author liugf
 * @version 2019-08-13
 */
@Mapper
@Repository
public interface CategoryMapper extends TreeMapper<Category> {
	
}