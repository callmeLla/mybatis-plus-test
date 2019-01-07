package com.wugz.app.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("pd_prj_info")
public class PdPrjInfo extends Model implements Serializable {
	private static final long serialVersionUID = 1L;

	//项目id
	@TableId(type=IdType.UUID)
	private String prjId;
	//项目名称
    private String prjName;
	//资产包类型
	private String prjType;
	//资产明细表存放地址
	private String filePath;
	//影像文件保存地址
	private String folderPath;
	//创建人
	private String creator;
	//创建时间
	private String crtTime;
	//修改时间
	private String lastOptTime;
}
