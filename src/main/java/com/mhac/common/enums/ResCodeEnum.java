package com.mhac.common.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yulinfu
 * @description
 * @data 2021/8/6
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ResCodeEnum {

    EXISTS("1000", "数据已存在"),
    NOT_FOUND("1001", "数据不存在"),
    NOT_MATCH("1002", "数据不匹配"),
    SUCCESS("000", "成功")

    ;

    @Getter
    private String code;

    @Getter
    private String desc;
}
