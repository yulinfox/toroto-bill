package com.mhac.controller;

import com.mhac.common.domain.ResponseVO;
import com.mhac.common.enums.ResCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author yulinfu
 * @description
 * @data 2021/8/7
 */
@Slf4j
@RestController
@RequestMapping("/msg")
public class MsgController {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String MSG_CODE = "msg_code.";

    private static final long EXPIRE_TIME = 5L;

    @GetMapping("/code/{phoneNumber}")
    public ResponseVO sendMsgCode(@PathVariable("phoneNumber") String phoneNumber) {
        String msgCode = generateMsgCode();
        redisTemplate.opsForValue().set(getKey(MSG_CODE, phoneNumber), msgCode, EXPIRE_TIME, TimeUnit.MINUTES);
        log.info("验证码：{}, {}", phoneNumber, msgCode);
        // todo send msg to customer
        return ResponseVO.builder().code(ResCodeEnum.SUCCESS.getCode()).build();
    }

    @GetMapping("/verify/{phoneNumber}/{msgCode}")
    public ResponseVO verifyMsgCode(@PathVariable("phoneNumber") String phoneNumber, @PathVariable("msgCode") String msgCode) {
        ResponseVO response = new ResponseVO();
        String key = getKey(MSG_CODE, phoneNumber);
        if (!redisTemplate.hasKey(key)) {
            response.setCode(ResCodeEnum.NOT_FOUND.getCode());
            response.setMsg("验证码已失效，请重新发生验证码");
            return response;
        }
        String cacheCode = redisTemplate.opsForValue().get(key).toString();
        if (msgCode.equals(cacheCode)) {
            response.setCode(ResCodeEnum.SUCCESS.getCode());
        } else {
            response.setCode(ResCodeEnum.NOT_MATCH.getCode());
            response.setMsg("验证码不正确，请重新输入");
        }
        return response;
    }

    private String getKey(String prefix, String key) {
        return String.format("%s%s", prefix, key);
    }


    private String generateMsgCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

}
