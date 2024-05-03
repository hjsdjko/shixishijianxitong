
package com.controller;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import com.service.TokenService;
import com.utils.*;
import java.lang.reflect.InvocationTargetException;

import com.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.*;
import com.entity.view.*;
import com.service.*;
import com.utils.PageUtils;
import com.utils.R;
import com.alibaba.fastjson.*;

/**
 * 任务交流
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/shijianrenshuChat")
public class ShijianrenshuChatController {
    private static final Logger logger = LoggerFactory.getLogger(ShijianrenshuChatController.class);

    private static final String TABLE_NAME = "shijianrenshuChat";

    @Autowired
    private ShijianrenshuChatService shijianrenshuChatService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;

    //级联表非注册的service
    //注册表service
    @Autowired
    private YonghuService yonghuService;
    @Autowired
    private QiyeService qiyeService;


    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永不会进入");
        else if("用户".equals(role))
            params.put("yonghuId",request.getSession().getAttribute("userId"));
        else if("企业".equals(role))
            params.put("qiyeId",request.getSession().getAttribute("userId"));
        CommonUtil.checkMap(params);
        PageUtils page = shijianrenshuChatService.queryPage(params);

        //字典表数据转换
        List<ShijianrenshuChatView> list =(List<ShijianrenshuChatView>)page.getList();
        for(ShijianrenshuChatView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);
        }
        return R.ok().put("data", page);
    }

    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        ShijianrenshuChatEntity shijianrenshuChat = shijianrenshuChatService.selectById(id);
        if(shijianrenshuChat !=null){
            //entity转view
            ShijianrenshuChatView view = new ShijianrenshuChatView();
            BeanUtils.copyProperties( shijianrenshuChat , view );//把实体数据重构到view中
            //级联表 企业
            //级联表
            QiyeEntity qiye = qiyeService.selectById(shijianrenshuChat.getQiyeId());
            if(qiye != null){
            BeanUtils.copyProperties( qiye , view ,new String[]{ "id", "createTime", "insertTime", "updateTime", "qiyeId"
, "yonghuId"});//把级联的数据添加到view中,并排除id和创建时间字段,当前表的级联注册表
            view.setQiyeId(qiye.getId());
            }
            //级联表 用户
            //级联表
            YonghuEntity yonghu = yonghuService.selectById(shijianrenshuChat.getYonghuId());
            if(yonghu != null){
            BeanUtils.copyProperties( yonghu , view ,new String[]{ "id", "createTime", "insertTime", "updateTime", "qiyeId"
, "yonghuId"});//把级联的数据添加到view中,并排除id和创建时间字段,当前表的级联注册表
            view.setYonghuId(yonghu.getId());
            }
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody ShijianrenshuChatEntity shijianrenshuChat, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,shijianrenshuChat:{}",this.getClass().getName(),shijianrenshuChat.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永远不会进入");
        else if("企业".equals(role))
            shijianrenshuChat.setQiyeId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
        else if("用户".equals(role))
            shijianrenshuChat.setYonghuId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));

        Wrapper<ShijianrenshuChatEntity> queryWrapper = new EntityWrapper<ShijianrenshuChatEntity>()
            .eq("yonghu_id", shijianrenshuChat.getYonghuId())
            .eq("qiye_id", shijianrenshuChat.getQiyeId())
            .eq("zhuangtai_types", shijianrenshuChat.getZhuangtaiTypes())
            .eq("shijianrenshu_chat_types", shijianrenshuChat.getShijianrenshuChatTypes())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        ShijianrenshuChatEntity shijianrenshuChatEntity = shijianrenshuChatService.selectOne(queryWrapper);
        if(shijianrenshuChatEntity==null){
            shijianrenshuChat.setInsertTime(new Date());
            shijianrenshuChat.setCreateTime(new Date());
            shijianrenshuChatService.insert(shijianrenshuChat);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody ShijianrenshuChatEntity shijianrenshuChat, HttpServletRequest request) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        logger.debug("update方法:,,Controller:{},,shijianrenshuChat:{}",this.getClass().getName(),shijianrenshuChat.toString());
        ShijianrenshuChatEntity oldShijianrenshuChatEntity = shijianrenshuChatService.selectById(shijianrenshuChat.getId());//查询原先数据

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
//        else if("企业".equals(role))
//            shijianrenshuChat.setQiyeId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
//        else if("用户".equals(role))
//            shijianrenshuChat.setYonghuId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));

            shijianrenshuChatService.updateById(shijianrenshuChat);//根据id更新
            return R.ok();
    }



    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids, HttpServletRequest request){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        List<ShijianrenshuChatEntity> oldShijianrenshuChatList =shijianrenshuChatService.selectBatchIds(Arrays.asList(ids));//要删除的数据
        shijianrenshuChatService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }


    /**
     * 批量上传
     */
    @RequestMapping("/batchInsert")
    public R save( String fileName, HttpServletRequest request){
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}",this.getClass().getName(),fileName);
        Integer yonghuId = Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId")));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            List<ShijianrenshuChatEntity> shijianrenshuChatList = new ArrayList<>();//上传的东西
            Map<String, List<String>> seachFields= new HashMap<>();//要查询的字段
            Date date = new Date();
            int lastIndexOf = fileName.lastIndexOf(".");
            if(lastIndexOf == -1){
                return R.error(511,"该文件没有后缀");
            }else{
                String suffix = fileName.substring(lastIndexOf);
                if(!".xls".equals(suffix)){
                    return R.error(511,"只支持后缀为xls的excel文件");
                }else{
                    URL resource = this.getClass().getClassLoader().getResource("static/upload/" + fileName);//获取文件路径
                    File file = new File(resource.getFile());
                    if(!file.exists()){
                        return R.error(511,"找不到上传文件，请联系管理员");
                    }else{
                        List<List<String>> dataList = PoiUtil.poiImport(file.getPath());//读取xls文件
                        dataList.remove(0);//删除第一行，因为第一行是提示
                        for(List<String> data:dataList){
                            //循环
                            ShijianrenshuChatEntity shijianrenshuChatEntity = new ShijianrenshuChatEntity();
//                            shijianrenshuChatEntity.setYonghuId(Integer.valueOf(data.get(0)));   //提问人 要改的
//                            shijianrenshuChatEntity.setQiyeId(Integer.valueOf(data.get(0)));   //回答人 要改的
//                            shijianrenshuChatEntity.setShijianrenshuChatIssueText(data.get(0));                    //问题 要改的
//                            shijianrenshuChatEntity.setIssueTime(sdf.parse(data.get(0)));          //问题时间 要改的
//                            shijianrenshuChatEntity.setShijianrenshuChatReplyText(data.get(0));                    //回复 要改的
//                            shijianrenshuChatEntity.setReplyTime(sdf.parse(data.get(0)));          //回复时间 要改的
//                            shijianrenshuChatEntity.setZhuangtaiTypes(Integer.valueOf(data.get(0)));   //状态 要改的
//                            shijianrenshuChatEntity.setShijianrenshuChatTypes(Integer.valueOf(data.get(0)));   //数据类型 要改的
//                            shijianrenshuChatEntity.setInsertTime(date);//时间
//                            shijianrenshuChatEntity.setCreateTime(date);//时间
                            shijianrenshuChatList.add(shijianrenshuChatEntity);


                            //把要查询是否重复的字段放入map中
                        }

                        //查询是否重复
                        shijianrenshuChatService.insertBatch(shijianrenshuChatList);
                        return R.ok();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return R.error(511,"批量插入数据异常，请联系管理员");
        }
    }




    /**
    * 前端列表
    */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("list方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));

        CommonUtil.checkMap(params);
        PageUtils page = shijianrenshuChatService.queryPage(params);

        //字典表数据转换
        List<ShijianrenshuChatView> list =(List<ShijianrenshuChatView>)page.getList();
        for(ShijianrenshuChatView c:list)
            dictionaryService.dictionaryConvert(c, request); //修改对应字典表字段

        return R.ok().put("data", page);
    }

    /**
    * 前端详情
    */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        ShijianrenshuChatEntity shijianrenshuChat = shijianrenshuChatService.selectById(id);
            if(shijianrenshuChat !=null){


                //entity转view
                ShijianrenshuChatView view = new ShijianrenshuChatView();
                BeanUtils.copyProperties( shijianrenshuChat , view );//把实体数据重构到view中

                //级联表
                    QiyeEntity qiye = qiyeService.selectById(shijianrenshuChat.getQiyeId());
                if(qiye != null){
                    BeanUtils.copyProperties( qiye , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setQiyeId(qiye.getId());
                }
                //级联表
                    YonghuEntity yonghu = yonghuService.selectById(shijianrenshuChat.getYonghuId());
                if(yonghu != null){
                    BeanUtils.copyProperties( yonghu , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setYonghuId(yonghu.getId());
                }
                //修改对应字典表字段
                dictionaryService.dictionaryConvert(view, request);
                return R.ok().put("data", view);
            }else {
                return R.error(511,"查不到数据");
            }
    }


    /**
    * 前端保存
    */
    @RequestMapping("/add")
    public R add(@RequestBody ShijianrenshuChatEntity shijianrenshuChat, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,shijianrenshuChat:{}",this.getClass().getName(),shijianrenshuChat.toString());
        Wrapper<ShijianrenshuChatEntity> queryWrapper = new EntityWrapper<ShijianrenshuChatEntity>()
            .eq("yonghu_id", shijianrenshuChat.getYonghuId())
            .eq("qiye_id", shijianrenshuChat.getQiyeId())
            .eq("shijianrenshu_chat_issue_text", shijianrenshuChat.getShijianrenshuChatIssueText())
            .eq("shijianrenshu_chat_reply_text", shijianrenshuChat.getShijianrenshuChatReplyText())
            .eq("zhuangtai_types", shijianrenshuChat.getZhuangtaiTypes())
            .eq("shijianrenshu_chat_types", shijianrenshuChat.getShijianrenshuChatTypes())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        ShijianrenshuChatEntity shijianrenshuChatEntity = shijianrenshuChatService.selectOne(queryWrapper);
        if(shijianrenshuChatEntity==null){
            shijianrenshuChat.setInsertTime(new Date());
            shijianrenshuChat.setCreateTime(new Date());
        shijianrenshuChatService.insert(shijianrenshuChat);

            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

}
