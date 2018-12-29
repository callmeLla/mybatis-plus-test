package com.wugz.app.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wugz.app.domain.TdTaskInfo;

/**
 * 任务信息表
 *
 *
 * @author wugz
 * @email 1019036248@qq.com
 * @date 2018-11-09 10:43:29
 */
public interface TdTaskInfoDao extends BaseMapper<TdTaskInfo> {

//    @Select("select * from td_task_info task left join td_check_point_info b on task.task_id=b.task_id where task.task_id=#{taskId}")
//    List<Map<String,String>> selectTaskAllCheckPoint(String taskId);
}
