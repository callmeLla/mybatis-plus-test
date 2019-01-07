package com.wugz.app.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wugz.app.domain.PdPrjInfo;

/**
 * 项目信息表
 * 
 * @author wugz
 * @email 1019036248@qq.com
 * @date 2018-11-09 10:43:29
 */
public interface PdPrjInfoDao extends BaseMapper<PdPrjInfo> {


    IPage<PdPrjInfo> selectPageVo(Page page, String prjType);

}
