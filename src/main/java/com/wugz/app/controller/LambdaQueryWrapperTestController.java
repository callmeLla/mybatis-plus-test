package com.wugz.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.wugz.app.domain.PdPrjInfo;
import com.wugz.app.service.PdPrjInfoService;
import com.wugz.app.service.TdTaskInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName LambdaQueryWrapperTestController
 * @Description LambdaQueryWrapper测试
 * @Author wugz
 * @Date 2018/12/28 14:07
 * @Version 1.0
 */
@RestController
@RequestMapping("/lambda")
public class LambdaQueryWrapperTestController {

    @Autowired
    private PdPrjInfoService pdPrjInfoService;

    @Autowired
    private TdTaskInfoService tdTaskInfoService;

    
    /***
     * @Author wugz
     * @Description 初始化一个lambda查询表达式
     * @Date  2018/12/28 15:52
     * @Param []
     * @return java.lang.Object
     **/
    @RequestMapping("/")
    public Object init(){
        /**
         * 初始化LambdaQueryWrapper时若使用带实体类参数的构造器，则默认查询条件为属性不为空的条件
         * 以listMaps为例，构造的sql为：
         *<script>
         * SELECT <choose>
         * <when test="ew != null and ew.sqlSelect != null">
         * ${ew.sqlSelect}
         * </when>
         * <otherwise>prj_id,prj_name,prj_type,file_path,folder_path,creator,crt_time,last_opt_time</otherwise>
         * </choose> FROM pd_prj_info
         * <if test="ew != null">
         * <where>
         * <if test="ew.entity != null">
         * <if test="ew.entity.prjId != null">prj_id=#{ew.entity.prjId}</if>
         * <if test="ew.entity.prjName != null"> AND prj_name=#{ew.entity.prjName}</if>
         * <if test="ew.entity.prjType != null"> AND prj_type=#{ew.entity.prjType}</if>
         * <if test="ew.entity.filePath != null"> AND file_path=#{ew.entity.filePath}</if>
         * <if test="ew.entity.folderPath != null"> AND folder_path=#{ew.entity.folderPath}</if>
         * <if test="ew.entity.creator != null"> AND creator=#{ew.entity.creator}</if>
         * <if test="ew.entity.crtTime != null"> AND crt_time=#{ew.entity.crtTime}</if>
         * <if test="ew.entity.lastOptTime != null"> AND last_opt_time=#{ew.entity.lastOptTime}</if>
         * </if>
         * <if test="ew.sqlSegment != null and ew.sqlSegment != '' and ew.nonEmptyOfWhere">
         * <if test="ew.nonEmptyOfEntity and ew.nonEmptyOfNormal"> AND</if> ${ew.sqlSegment}
         * </if>
         * </where>
         * <if test="ew.sqlSegment != null and ew.sqlSegment != '' and ew.emptyOfWhere">
         *  ${ew.sqlSegment}
         * </if>
         * </if>
         * </script>
         */
        PdPrjInfo prjInfo = new PdPrjInfo();
        prjInfo.setPrjId("1");
        prjInfo.setPrjName("查询项目1");
        LambdaQueryWrapper<PdPrjInfo> wrapper = new LambdaQueryWrapper<>(prjInfo);


        /***
         * 当然还可以使用无参构造器
         */
        LambdaQueryWrapper<PdPrjInfo> wrapper1 = new LambdaQueryWrapper<>();
        System.out.println(wrapper.getSqlSegment());
        return pdPrjInfoService.listMaps(wrapper1);
    }

    /**
     * @Author wugz
     * @Description 选择执行的查询列，若不选择则默认全部查询
     * @Date  2018/12/28 15:28
     * @Param []
     * @return java.lang.Object
     **/
    @RequestMapping("/select")
    public Object select(){
        LambdaQueryWrapper<PdPrjInfo> wrapper = new LambdaQueryWrapper<>();
        /***
         * 指定字段查询：prj_name 和 prj_id 两列
         */
        //wrapper.select(PdPrjInfo::getPrjName,PdPrjInfo::getPrjId);

        /**
         * 条件查询，条件是查询字段以pjt开头的 其中i 代表 TableFieldInfo 数据库表字段反射信息
         */
        wrapper.select(PdPrjInfo.class,i -> i.getProperty().startsWith("pjt"));
        System.out.println(wrapper.getSqlSegment());
        return pdPrjInfoService.listMaps(wrapper);
    }

    /**
     * @Author wugz
     * @Description 全部eq(或个别isNull)
     * @Date  2018/12/29 11:13
     * @Param []
     * @return java.lang.Object
     **/
    @RequestMapping("/allEq")
    public Object allEq(){
        LambdaQueryWrapper<PdPrjInfo> wrapper = new LambdaQueryWrapper<>();
        Map<SFunction<PdPrjInfo,?>,String> param = new HashMap<>();
        param.put(PdPrjInfo::getPrjName,"测试项目");
        param.put(PdPrjInfo::getPrjId,null);
        /**
         * SELECT prj_id,prj_name,prj_type,file_path,folder_path,creator,crt_time,last_opt_time FROM pd_prj_info WHERE prj_name = ?
         */
        //wrapper.allEq(param,false);
        /**
         * SELECT prj_id,prj_name,prj_type,file_path,folder_path,creator,crt_time,last_opt_time FROM pd_prj_info WHERE prj_name = ? AND prj_id IS NULL
         */
        //第二个boolean属性就是把map中为null的 是不是转换成is null
        wrapper.allEq(param,true);
        return pdPrjInfoService.list(wrapper);
    }
    
    /**
     * @Author wugz
     * @Description 相等
     * @Date  2018/12/29 13:12
     * @Param []
     * @return java.lang.Object
     **/
    @RequestMapping("/eq")
    public Object eq(){
        LambdaQueryWrapper<PdPrjInfo> wrapper = new LambdaQueryWrapper<>();
        //设置为false则不使用该条件
        //wrapper.eq(false,PdPrjInfo::getPrjName,"测试项目1");
        wrapper.eq(true,PdPrjInfo::getPrjName,"测试项目1");
        System.out.println(wrapper.getSqlSegment());//SELECT prj_id,prj_name,prj_type,file_path,folder_path,creator,crt_time,last_opt_time FROM pd_prj_info WHERE prj_name = ?
        return pdPrjInfoService.list(wrapper);
    }

    /**
     * @Author wugz
     * @Description 不相等
     * @Date  2018/12/29 13:12
     * @Param []
     * @return java.lang.Object
     **/
    @RequestMapping("/ne")
    public Object ne(){
        LambdaQueryWrapper<PdPrjInfo> wrapper = new LambdaQueryWrapper<>();
        //设置为false则不使用该条件
        //wrapper.ne(false,PdPrjInfo::getPrjName,"测试项目1");
        wrapper.ne(true,PdPrjInfo::getPrjName,"测试项目1");
        System.out.println(wrapper.getSqlSegment());//SELECT prj_id,prj_name,prj_type,file_path,folder_path,creator,crt_time,last_opt_time FROM pd_prj_info WHERE prj_name <> ?
        return pdPrjInfoService.list(wrapper);
    }

    /***
     * gt(大于)
     * ge(大于等于)
     * lt(小于)
     * le(小于等于)
     */

    /**
     * @Author wugz
     * @Description 不相等
     * @Date  2018/12/29 13:12
     * @Param []
     * @return java.lang.Object
     **/
    @RequestMapping("/between")
    public Object between(){
        LambdaQueryWrapper<PdPrjInfo> wrapper = new LambdaQueryWrapper<>();

        wrapper.between(PdPrjInfo::getPrjType,"1","2");
        System.out.println(wrapper.getSqlSegment());//SELECT prj_id,prj_name,prj_type,file_path,folder_path,creator,crt_time,last_opt_time FROM pd_prj_info WHERE prj_name BETWEEN ? AND ?
        return pdPrjInfoService.list(wrapper);
    }

    /***
     * notBetween  NOT BETWEEN 值1 AND 值2 例: notBetween("age", 18, 30)--->age not between 18 and 30
     *
     * like  LIKE '%值%'   like("name", "王")--->name like '%王%'
     *
     * notLike NOT LIKE '%值%'  例: notLike("name", "王")--->name not like '%王%'
     *
     * likeLeft LIKE '%值'  例: likeLeft("name", "王")--->name like '%王'
     *
     * likeRight   LIKE '值%'   例: likeRight("name", "王")--->name like '王%'
     *
     * isNull 字段 IS NULL  例: isNull("name")--->name is null
     *
     * isNotNull 字段 IS NOT NULL  例: isNotNull("name")--->name is not null
     *
     */
    
    /**
     * @Author wugz
     * @Description  字段 IN (value.get(0), value.get(1), ...)  例: in("age",{1,2,3})--->age in (1,2,3)
     * @Date  2018/12/29 14:04
     * @Param []
     * @return java.lang.Object
     **/
    @RequestMapping("/in")
    public Object in(){
        LambdaQueryWrapper<PdPrjInfo> wrapper = new LambdaQueryWrapper<>();
        //wrapper.in(PdPrjInfo::getPrjType,"1","2");
        String[] a = {"1","2"};
        wrapper.in(PdPrjInfo::getPrjType,Arrays.asList(a));
        System.out.println(wrapper.getSqlSegment());//SELECT prj_id,prj_name,prj_type,file_path,folder_path,creator,crt_time,last_opt_time FROM pd_prj_info WHERE prj_type IN (?,?)
        return pdPrjInfoService.list(wrapper);
    }

    /***
     * notIn 例: notIn("age",{1,2,3})--->age not in (1,2,3)
     */


    @RequestMapping("/inSql")
    public Object inSql(){
        LambdaQueryWrapper<PdPrjInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.inSql(PdPrjInfo::getPrjType,"select prj_type from pd_prj_info");
        System.out.println(wrapper.getSqlSegment());//SELECT prj_id,prj_name,prj_type,file_path,folder_path,creator,crt_time,last_opt_time FROM pd_prj_info WHERE prj_type IN (select prj_type from pd_prj_info)
        return pdPrjInfoService.list(wrapper);
    }

    /***
     * notInSql  notInSql("id", "select id from table where id < 3")--->age not in (select id from table where id < 3)
     *
     * groupBy  例: groupBy("id", "name")--->group by id,name
     *
     * orderByAsc 例: orderByAsc("id", "name")--->order by id ASC,name ASC
     *
     * orderByDesc  例: orderByDesc("id", "name")--->order by id DESC,name DESC
     *
     * orderBy orderBy(true, true, "id", "name")--->order by id ASC,name ASC
     *
     */

    @RequestMapping("/having")
    public Object having(){
        LambdaQueryWrapper<PdPrjInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.ne(PdPrjInfo::getPrjName,"1231231");//随便输的条件
        wrapper.groupBy(PdPrjInfo::getPrjType);
        wrapper.having("max(prj_type) > 0");
        //SELECT prj_id,prj_name,prj_type,file_path,folder_path,creator,crt_time,last_opt_time FROM pd_prj_info WHERE prj_name <> ? GROUP BY prj_type HAVING max(prj_type) > 0
        System.out.println(wrapper.getSqlSegment());
        return pdPrjInfoService.list(wrapper);
    }


    /**
     * @Author wugz
     * @Description 主动调用or表示紧接着下一个方法不是用and连接!(不调用or则默认为使用and连接)
     * @Date  2018/12/29 15:35
     * @Param []
     * @return java.lang.Object
     **/
    @RequestMapping("/or")
    public Object or(){
        LambdaQueryWrapper<PdPrjInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PdPrjInfo::getPrjType,"1").or().eq(PdPrjInfo::getPrjName,"123");
        // SELECT prj_id,prj_name,prj_type,file_path,folder_path,creator,crt_time,last_opt_time FROM pd_prj_info WHERE prj_type = ? OR prj_name = ?
        System.out.println(wrapper.getSqlSegment());
        return pdPrjInfoService.list(wrapper);
    }


    /**
     * @Author wugz
     * @Description 拼接sql 该方法可用于数据库函数 动态入参的params对应前面sqlHaving内部的{index}部分.这样是不会有sql注入风险的,反之会有!
     * @Date  2018/12/29 15:37
     * @Param []
     * @return java.lang.Object
     **/
    @RequestMapping("/apply")
    public Object apply(){
        LambdaQueryWrapper<PdPrjInfo> wrapper = new LambdaQueryWrapper<>();
        //wrapper.apply("prj_id=1 and prj_name = {1}","1","测试项目");
        wrapper.apply("prj_id={0} and prj_name = {1}","1","测试项目");
        //Preparing: SELECT prj_id,prj_name,prj_type,file_path,folder_path,creator,crt_time,last_opt_time FROM pd_prj_info WHERE prj_id=? and prj_name = ?
        //Parameters: 1(String), 测试项目(String)
        System.out.println(wrapper.getSqlSegment());
        return pdPrjInfoService.list(wrapper);
    }

    /**
     * @Author wugz
     * @Description 拼接 EXISTS ( sql语句 )
     * @Date  2018/12/29 15:58
     * @Param []
     * @return java.lang.Object
     **/
    @RequestMapping("/exists")
    public Object exists(){
        LambdaQueryWrapper<PdPrjInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.exists("select prj_type FROM pd_prj_info");
        //SELECT prj_id,prj_name,prj_type,file_path,folder_path,creator,crt_time,last_opt_time FROM pd_prj_info WHERE EXISTS (select prj_type FROM pd_prj_info)
        System.out.println(wrapper.getSqlSegment());
        return pdPrjInfoService.list(wrapper);
    }

    /***
     * last 无视优化规则直接拼接到 sql 的最后 例: last("limit 1")
     *
     * notExists 拼接 NOT EXISTS ( sql语句 )
     *
     */
    
    
    /**
     * @Author wugz
     * @Description 嵌套条件，相当于将多个条件使用()括起来
     * @Date  2018/12/29 16:05
     * @Param []
     * @return java.lang.Object
     **/
    @RequestMapping("/nested")
    public Object nested(){
        LambdaQueryWrapper<PdPrjInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.nested(i -> i.eq(PdPrjInfo::getPrjName,"测试项目").eq(PdPrjInfo::getPrjType,"1"));
        //SELECT prj_id,prj_name,prj_type,file_path,folder_path,creator,crt_time,last_opt_time FROM pd_prj_info WHERE ( prj_name = ? AND prj_type = ? ) 
        System.out.println(wrapper.getSqlSegment());
        return pdPrjInfoService.list(wrapper);
    }


}
