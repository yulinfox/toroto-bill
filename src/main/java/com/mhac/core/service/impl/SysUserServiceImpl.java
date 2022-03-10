package com.mhac.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mhac.core.dao.SysUserDao;
import com.mhac.core.domain.SysUserDO;
import com.mhac.core.service.SysUserService;

/**
 * @Description 系统用户业务实现
 * @author yulinfu
 * @date 2019/6/14 15:57
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserDO> implements SysUserService {

    /**
     * 根据用户名查询实体
     * @author yulinfu
     * @date 2019/6/14 16:30
     * @Param  username 用户名
     * @Return SysUserEntity 用户实体
     */
    @Override
    public SysUserDO selectUserByName(String username) {
        QueryWrapper<SysUserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUserDO::getUsername,username);
        return this.baseMapper.selectOne(queryWrapper);
    }
}