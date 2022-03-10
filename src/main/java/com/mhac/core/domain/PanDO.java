package com.mhac.core.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author yulinfu
 * @description
 * @data 2022/3/8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("pan")
public class PanDO {

    private Long id;

    /**
     * 盘名
     */
    private String name;

    /**
     * 盘描述
     */
    private String desc;

    /**
     * 币种
     */
    private Integer currency;

    /**
     * 开盘人
     */
    private Long openUserId;

    private Date openTime;

    private Date closeTime;

    private Date createTime;

    private Date updateTime;
}
