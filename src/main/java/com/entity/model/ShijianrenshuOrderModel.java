package com.entity.model;

import com.entity.ShijianrenshuOrderEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;


/**
 * 任务订单
 * 接收传参的实体类
 *（实际开发中配合移动端接口开发手动去掉些没用的字段， 后端一般用entity就够用了）
 * 取自ModelAndView 的model名称
 */
public class ShijianrenshuOrderModel implements Serializable {
    private static final long serialVersionUID = 1L;




    /**
     * 主键
     */
    private Integer id;


    /**
     * 订单号
     */
    private String shijianrenshuOrderUuidNumber;


    /**
     * 任务
     */
    private Integer shijianrenshuId;


    /**
     * 用户
     */
    private Integer yonghuId;


    /**
     * 状态类型
     */
    private Integer shijianrenshuOrderTypes;


    /**
     * 申请接单时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    private Date insertTime;


    /**
     * 成果文件
     */
    private String shijianrenshuOrderFile;


    /**
     * 审核状态
     */
    private Integer shijianrenshuOrderYesnoTypes;


    /**
     * 审核回复
     */
    private String shijianrenshuOrderYesnoText;


    /**
     * 审核时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    private Date shijianrenshuOrderShenheTime;


    /**
     * 创建时间 show3 listShow
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    private Date createTime;


    /**
	 * 获取：主键
	 */
    public Integer getId() {
        return id;
    }


    /**
	 * 设置：主键
	 */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 获取：订单号
	 */
    public String getShijianrenshuOrderUuidNumber() {
        return shijianrenshuOrderUuidNumber;
    }


    /**
	 * 设置：订单号
	 */
    public void setShijianrenshuOrderUuidNumber(String shijianrenshuOrderUuidNumber) {
        this.shijianrenshuOrderUuidNumber = shijianrenshuOrderUuidNumber;
    }
    /**
	 * 获取：任务
	 */
    public Integer getShijianrenshuId() {
        return shijianrenshuId;
    }


    /**
	 * 设置：任务
	 */
    public void setShijianrenshuId(Integer shijianrenshuId) {
        this.shijianrenshuId = shijianrenshuId;
    }
    /**
	 * 获取：用户
	 */
    public Integer getYonghuId() {
        return yonghuId;
    }


    /**
	 * 设置：用户
	 */
    public void setYonghuId(Integer yonghuId) {
        this.yonghuId = yonghuId;
    }
    /**
	 * 获取：状态类型
	 */
    public Integer getShijianrenshuOrderTypes() {
        return shijianrenshuOrderTypes;
    }


    /**
	 * 设置：状态类型
	 */
    public void setShijianrenshuOrderTypes(Integer shijianrenshuOrderTypes) {
        this.shijianrenshuOrderTypes = shijianrenshuOrderTypes;
    }
    /**
	 * 获取：申请接单时间
	 */
    public Date getInsertTime() {
        return insertTime;
    }


    /**
	 * 设置：申请接单时间
	 */
    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    /**
	 * 获取：成果文件
	 */
    public String getShijianrenshuOrderFile() {
        return shijianrenshuOrderFile;
    }


    /**
	 * 设置：成果文件
	 */
    public void setShijianrenshuOrderFile(String shijianrenshuOrderFile) {
        this.shijianrenshuOrderFile = shijianrenshuOrderFile;
    }
    /**
	 * 获取：审核状态
	 */
    public Integer getShijianrenshuOrderYesnoTypes() {
        return shijianrenshuOrderYesnoTypes;
    }


    /**
	 * 设置：审核状态
	 */
    public void setShijianrenshuOrderYesnoTypes(Integer shijianrenshuOrderYesnoTypes) {
        this.shijianrenshuOrderYesnoTypes = shijianrenshuOrderYesnoTypes;
    }
    /**
	 * 获取：审核回复
	 */
    public String getShijianrenshuOrderYesnoText() {
        return shijianrenshuOrderYesnoText;
    }


    /**
	 * 设置：审核回复
	 */
    public void setShijianrenshuOrderYesnoText(String shijianrenshuOrderYesnoText) {
        this.shijianrenshuOrderYesnoText = shijianrenshuOrderYesnoText;
    }
    /**
	 * 获取：审核时间
	 */
    public Date getShijianrenshuOrderShenheTime() {
        return shijianrenshuOrderShenheTime;
    }


    /**
	 * 设置：审核时间
	 */
    public void setShijianrenshuOrderShenheTime(Date shijianrenshuOrderShenheTime) {
        this.shijianrenshuOrderShenheTime = shijianrenshuOrderShenheTime;
    }
    /**
	 * 获取：创建时间 show3 listShow
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 设置：创建时间 show3 listShow
	 */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    }