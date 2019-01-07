package com.wugz.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wugz.app.domain.PdPrjInfo;
import com.wugz.app.service.PdPrjInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TestController
 * @Description TODO
 * @Author wugz
 * @Date 2019/1/3 10:17
 * @Version 1.0
 */
@RestController
@RequestMapping("/")
public class TestController {

    @Autowired
    private PdPrjInfoService pdPrjInfoService;
    
    /**
     * @Author wugz
     * @Description 分页查询 使用mybatis-plus默认的分页插件需要添加配置
     * @Date  2019/1/3 10:26
     * @Param []
     * @return java.lang.Object
     **/
    @RequestMapping("/page")
    public Object page(){
        /***
         *  Preparing: SELECT COUNT(1) FROM pd_prj_info WHERE prj_type = ?
         *  Parameters:1(String)
         *  Preparing: SELECT prj_id,prj_name,prj_type,file_path,folder_path,creator,crt_time,last_opt_time FROM pd_prj_info WHERE prj_type = ? ORDER BY crt_time DESC limit ? offset ?
         *  Parameters: 1(String), 2(Long), 2(Long)
         */
        // 不进行 count sql 优化，解决 MP 无法自动优化 SQL 问题，这时候你需要自己查询 count 部分
        // page.setOptimizeCountSql(false);
        // 当 total 为非 0 时(默认为 0),分页插件不会进行 count 查询
        // 要点!! 分页返回的对象与传入的对象是同一个
        Page<PdPrjInfo> page = new Page<>(2L,2L);
        return  pdPrjInfoService.pageMaps(page,new LambdaQueryWrapper<PdPrjInfo>().eq(PdPrjInfo::getPrjType,"1").orderByDesc(PdPrjInfo::getCrtTime));
    }
    
    /**
     * @Author wugz
     * @Description 批量添加 mybatis-plus可以设置一些主键策略，不需要自己手动去添加
     *  这里设置的 @TableId(type=IdType.UUID) 是默认的uuid
     * @Date  2019/1/3 11:30
     * @Param []
     * @return java.lang.Object
     **/
    @RequestMapping("/insert")
    public Object insert(){
        List<PdPrjInfo> list = new ArrayList<>();
        String nameFix = "test_";
        for(int i=0;i<20;i++){
            PdPrjInfo info = new PdPrjInfo();
            //使用自己设置的主键
            if(i%5==0){
                info.setPrjId("iiiiiiiiii"+Math.random());
            }
            info.setPrjName(nameFix+Math.random());
            list.add(info);
        }
        pdPrjInfoService.saveBatch(list);
        return pdPrjInfoService.list();
    }
    
    /**
     * @Author wugz
     * @Description 通过id 删除 参数为具体的值，主键的列名 通过实体类里 @TableId 标识的字段 如果设置多个@TableId，则去第一个tableId标识的字段
     * @Date  2019/1/7 10:26
     * @Param []
     * @return java.lang.Object
     **/
    @RequestMapping("/deleteById")
    public Object deleteById(){
        return pdPrjInfoService.removeById("a5db453d05ffe89d903ab87735f39fb7");
    }
    
    /**
     * @Author wugz
     * @Description 查询总记录数
     * @Date  2019/1/7 10:29
     * @Param []
     * @return java.lang.Object
     **/
    @RequestMapping("/count")
    public Object count(){
        return pdPrjInfoService.count();
    }

    @RequestMapping("/getById")
    public Object getById(){
        return pdPrjInfoService.getById("c28762000cb6dbd1db4470583c04505c");
    }
    
    /**
     * @Author wugz
     * @Description  参数为map的查询，key为表字段名而不是实体类的字段名
     * @Date  2019/1/7 10:35
     * @Param []
     * @return java.lang.Object
     **/
    @RequestMapping("listByMap")
    public Object listByMap(){
        Map<String,Object> param = new HashMap<>();
        param.put("prj_id","c28762000cb6dbd1db4470583c04505c");
        return pdPrjInfoService.listByMap(param);
    }
    
    /**
     * @Author wugz
     * @Description 先执行查询操作 如果存在就update 否则就insert
     * 注：实体类必须要有@TableId 查询时就通过主键查询
     * ==>  Preparing: SELECT prj_id,prj_name,prj_type,file_path,folder_path,creator,crt_time,last_opt_time FROM pd_prj_info WHERE prj_id=?
     * ==> Parameters: c28762000cb6dbd1db4470583c04505c(String)
     * <==    Columns: prj_id, prj_name, prj_type, file_path, folder_path, creator, crt_time, last_opt_time
     * <==        Row: c28762000cb6dbd1db4470583c04505c, test_0.07804440553606484, null, null, null, null, null, null
     * <==      Total: 1
     * Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4f003ddc]
     * Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4f003ddc] from current transaction
     * ==>  Preparing: UPDATE pd_prj_info SET prj_name=? WHERE prj_id=?
     * ==> Parameters: test(String), c28762000cb6dbd1db4470583c04505c(String)
     * <==    Updates: 1
     *
     * @Date  2019/1/7 10:40
     * @Param []
     * @return java.lang.Object
     **/
    @RequestMapping("saveOrUpdate")
    public Object saveOrUpdate(){
        PdPrjInfo pdPrjInfo = new PdPrjInfo();
        pdPrjInfo.setPrjId("c28762000cb6dbd1db4470583c04505c");
        pdPrjInfo.setPrjName("test");
        return pdPrjInfoService.saveOrUpdate(pdPrjInfo);
    }
}
