package com.mhac.controller;

import com.mhac.common.domain.ChangePasswordVO;
import com.mhac.common.domain.UserVO;
import com.mhac.common.enums.ResCodeEnum;
import com.mhac.common.util.SHA256Util;
import com.mhac.common.domain.ResponseVO;
import com.mhac.common.util.ShiroUtils;
import com.mhac.core.domain.SysUserDO;
import com.mhac.core.domain.SysUserRoleDO;
import com.mhac.core.service.SysUserRoleService;
import com.mhac.core.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yulinfu
 * @Description 用户登录
 * @date 2019/6/17 15:21
 */
@Slf4j
@RestController
@RequestMapping("/userLogin")
public class UserLoginController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 登录
     */
    @PostMapping("/login")
    public ResponseVO login(@RequestBody SysUserDO sysUserEntity) {
        ResponseVO response = new ResponseVO();
        //进行身份验证
        try {
            //验证身份和登陆
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(sysUserEntity.getUsername(), sysUserEntity.getPassword());
            //进行登录操作
            subject.login(token);
        } catch (IncorrectCredentialsException e) {
            response.setCode("500");
            response.setMsg("用户不存在或者密码错误");
            return response;
        } catch (Exception e) {
            response.setCode("500");
            response.setMsg("未知异常");
            return response;
        }
        ShiroUtils.addUser();
        response.setCode(ResCodeEnum.SUCCESS.getCode());
        response.setMsg("登录成功");
        response.setData(getUserVO(sysUserService.selectUserByName(sysUserEntity.getUsername())));
        response.setToken(ShiroUtils.getSession().getId().toString());
        return response;
    }

    private UserVO getUserVO(SysUserDO user) {
        UserVO vo = UserVO.builder().build();
        BeanUtils.copyProperties(user, vo);
        return vo;
    }

    /**
     * 登出
     */
    @GetMapping("/logout")
    public ResponseVO logout() {
        ResponseVO response = new ResponseVO();
        try {
            SysUserDO user = ShiroUtils.getUserInfo();
            if (null != user) {
                ShiroUtils.deleteCache(user.getUsername(), true);
                Subject subject = SecurityUtils.getSubject();
                subject.logout();
            }
        } catch (Exception e) {
            log.info("出现异常：", e);
            response.setCode("500");
            response.setMsg("登出异常");
            return response;
        }
        response.setCode(ResCodeEnum.SUCCESS.getCode());
        response.setMsg("登出成功");
        return response;
    }

    /**
     * 未登录
     *
     * @author yulinfu
     * @date 2019/6/20 9:22
     */
    @RequestMapping("/loginStatus")
    public ResponseVO loginStatus() {
        ResponseVO response = new ResponseVO();
        SysUserDO user = ShiroUtils.getUserInfo();
        if (null == user) {
            response.setCode("-1");
            response.setMsg("未登录");
        } else {
            response.setCode(ResCodeEnum.SUCCESS.getCode());
            response.setData(getUserVO(user));
        }
        return response;
    }


    /**
     * 添加一个用户演
     * 这里仅作为演示不加任何权限和重复查询校验
     */
    @PostMapping("/user")
    public ResponseVO addUser(@RequestBody SysUserDO sysUser) {
        // 设置基础参数
        sysUser.setState("NORMAL");
        // 随机生成盐值
        String salt = RandomStringUtils.randomAlphanumeric(20);
        sysUser.setSalt(salt);
        if (StringUtils.isEmpty(sysUser.getPassword())) {
            // 进行加密
            String password = "123456";
            sysUser.setPassword(SHA256Util.sha256(password, sysUser.getSalt()));
        }
        if (null == sysUser.getId()) {
            // 保存用户
            sysUser.setShowEmail(0);
            sysUser.setShowPhone(0);
            sysUser.setShowWechat(0);
            sysUserService.save(sysUser);
        } else {
            sysUserService.updateById(sysUser);
        }
        if (null == sysUser.getId()) {
            // 保存角色
            SysUserRoleDO sysUserRoleDO = new SysUserRoleDO();
            // 保存用户完之后会把ID返回给用户实体
            sysUserRoleDO.setUserId(sysUser.getId());
            sysUserRoleService.save(sysUserRoleDO);
        }
        // 返回结果
        ResponseVO response = new ResponseVO();
        response.setCode(ResCodeEnum.SUCCESS.getCode());
        response.setMsg("添加成功");
        return response;
    }


    /**
     * 注册
     */
    @PostMapping("/signUp")
    public ResponseVO signUp(@RequestBody SysUserDO sysUser) {
        SysUserDO user = sysUserService.selectUserByName(sysUser.getPhoneNumber());
        if (null != user) {
            return ResponseVO.builder()
                    .code(ResCodeEnum.EXISTS.getCode())
                    .msg("当前手机号已在本平台注册，请直接登陆")
                    .build();
        }
        // 设置基础参数
        sysUser.setUsername(sysUser.getPhoneNumber());
        sysUser.setState("NORMAL");
        // 随机生成盐值
        String salt = RandomStringUtils.randomAlphanumeric(20);
        sysUser.setSalt(salt);
        sysUser.setPassword(SHA256Util.sha256(sysUser.getPassword(), sysUser.getSalt()));
        // 保存用户
        sysUser.setShowEmail(0);
        sysUser.setShowPhone(0);
        sysUser.setShowWechat(0);
        sysUserService.save(sysUser);
        // 返回结果
        ResponseVO response = new ResponseVO();
        response.setCode(ResCodeEnum.SUCCESS.getCode());
        response.setMsg("添加成功");
        return response;
    }

    /**
     * 注册
     */
    @PostMapping("/forgetPassword")
    public ResponseVO forgetPassword(@RequestBody SysUserDO sysUser) {
        SysUserDO user = sysUserService.selectUserByName(sysUser.getPhoneNumber());
        if (null == user) {
            return ResponseVO.builder()
                    .code(ResCodeEnum.NOT_FOUND.getCode())
                    .msg("当前手机号未在本平台注册，请先注册")
                    .build();
        }
        // 随机生成盐值
        sysUser.setId(user.getId());
        String salt = RandomStringUtils.randomAlphanumeric(20);
        sysUser.setSalt(salt);
        sysUser.setPassword(SHA256Util.sha256(sysUser.getPassword(), sysUser.getSalt()));
        // 保存用户
        sysUserService.updateById(sysUser);
        // 返回结果
        ResponseVO response = new ResponseVO();
        response.setCode(ResCodeEnum.SUCCESS.getCode());
        response.setMsg("修改成功");
        return response;
    }

    @PostMapping("/change/password")
    public ResponseVO verifyPassWord(@RequestBody ChangePasswordVO vo) {
        SysUserDO user = ShiroUtils.getUserInfo();
        if (null == user) {
            return ResponseVO.builder()
                    .code(ResCodeEnum.NOT_FOUND.getCode())
                    .msg("未登录，请先登陆")
                    .build();
        }
        user = sysUserService.selectUserByName(user.getUsername());
        String actPassword = user.getPassword();
        String inputPassword = SHA256Util.sha256(vo.getPassword(), user.getSalt());
        if (!inputPassword.equals(actPassword)) {
            return ResponseVO.builder()
                    .code(ResCodeEnum.NOT_MATCH.getCode())
                    .msg("密码验证失败")
                    .build();
        }
        user.setPassword(SHA256Util.sha256(vo.getNewPassword(), user.getSalt()));
        sysUserService.updateById(user);
        return ResponseVO.builder()
                .code(ResCodeEnum.SUCCESS.getCode())
                .build();
    }

}