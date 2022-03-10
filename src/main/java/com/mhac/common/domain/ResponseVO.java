package com.mhac.common.domain;

import lombok.*;

/**
 * @author yulinfu
 * @description
 * @data 2021/8/3
 */
@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVO {

    /**
     * 返回码
     */
    private String code;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * token
     */
    private String token;

    /**
     * 数据
     */
    private Object data;

}
