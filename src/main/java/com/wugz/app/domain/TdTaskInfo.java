package com.wugz.app.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


/**
 * 任务信息表
 * 
 * @author wugz
 * @email 1019036248@qq.com
 * @String 2018-11-09 10:43:29
 */
@Data
@TableName("td_task_info")
public class TdTaskInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	//任务id
	@TableId
	private String taskId;
	//项目id
	private String prjId;
	//任务名称
	private String taskName;
	//任务类型
	private String taskType;
	//模板文件地址
	private String templateFilePath;
	//表头文件地址
	private String titleFilePath;
	//创建人
	private String creator;
	//创建时间
	private String crtTime;
	//修改时间
	private String updTime;
}
