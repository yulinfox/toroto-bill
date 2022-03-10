package com.mhac.core.service.impl;


import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mhac.core.dao.SysMenuDao;
import com.mhac.core.domain.SysMenuDO;
import com.mhac.core.service.SysMenuService;
import java.util.List;

/**
 * @Description 权限业务实现
 * @author yulinfu
 * @date 2019/6/14 15:57
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenuDO> implements SysMenuService {


    /**
     * 根据角色查询用户权限
     * @author yulinfu
     * @date 2019/6/19 10:14
     * @Param  roleId 角色ID
     * @Return List<SysMenuEntity> 权限集合
     */
    @Override
    public List<SysMenuDO> selectSysMenuByRoleId(Long roleId) {
        return this.baseMapper.selectSysMenuByRoleId(roleId);
    }
}