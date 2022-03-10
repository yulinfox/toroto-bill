package com.mhac.common.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author yulinfu
 * @description
 * @data 2021/8/4
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    /**
     * 用户ID
     */
    @TableId
    private Long id;
    /**
     * 用户名
     */
    private String username;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 是否展示手机号
     */
    private Integer showPhone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否展示邮件
     */
    private Integer showEmail;

    /**
     * 微信号
     */
    private String wechat;

    /**
     * 是否展示微信号
     */
    private Integer showWechat;

    /**
     * 微信群
     */
    private String wechatGroup;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 公司简称
     */
    private String companyShortName;

    /**
     * 公司省份
     */
    private String companyProvince;

    /**
     * 公司城市
     */
    private String companyCity;

    /**
     * 公司地址
     */
    private String companyAddress;

    /**
     * 企业性质
     */
    private String companyType;

    /**
     * 企业规模
     */
    private String companyScale;

    /**
     * 职位或部门
     */
    private String post;

    /**
     * 状态:NORMAL正常  PROHIBIT禁用
     */
    private String state;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新时间
     */
    private Date gmtUpdate;

    /**
     * 更新人
     */
    private String updateUser;

}
