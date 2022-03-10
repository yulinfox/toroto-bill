package com.mhac.core.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
@TableName("play_record")
public class PlayRecordDO {

    private Long id;

    private Long panId;

    private Long userId;

    private BigDecimal amount;

    private Date createTime;

    private Date updateTime;

}
