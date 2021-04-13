package com.dhsr.wx.cp.shiro;

import com.dhsr.wx.cp.base.Constant;
import com.dhsr.wx.cp.entity.Operator;
import com.dhsr.wx.cp.exception.UnauthorizedException;
import com.dhsr.wx.cp.service.IMenuService;
import com.dhsr.wx.cp.service.IOperatorService;
import com.dhsr.wx.cp.service.IRoleService;
import com.dhsr.wx.cp.service.SpringContextBeanService;
import com.dhsr.wx.cp.utils.JWTUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuxiaogang
 */
public class MyRealm extends AuthorizingRealm {
    private IOperatorService operatorService;
    private IMenuService menuService;
    private IRoleService roleService;

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (menuService == null) {
            this.menuService = SpringContextBeanService.getBean(IMenuService.class);
        }
        if (roleService == null) {
            this.roleService = SpringContextBeanService.getBean(IRoleService.class);
        }

        String userNo = JWTUtil.getUserNo(principals.toString());

        Map<String, Object> mso = new HashMap<>();

        mso.put("UserId", userNo);
        Operator operator = operatorService.selectByMap(mso);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        /*
        Role role = roleService.selectOne(new EntityWrapper<Role>().eq("role_code", userToRole.getRoleCode()));
        //添加控制角色级别的权限
        Set<String> roleNameSet = new HashSet<>();
        roleNameSet.add(role.getRoleName());
        simpleAuthorizationInfo.addRoles(roleNameSet);
        */
        //控制菜单级别按钮  类中用@RequiresPermissions("user:list") 对应数据库中code字段来控制controller
       /* ArrayList<String> pers = new ArrayList<>();
        List<Menu> menuList = menuService.findMenuByRoleCode(operator.getRoleInnerId());
        for (Menu per : menuList) {
             if (!ComUtil.isEmpty(per.getPageCode())) {
                  pers.add(String.valueOf(per.getPageCode()));
              }
        }
        Set<String> permission = new HashSet<>(pers);
        simpleAuthorizationInfo.addStringPermissions(permission);*/
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws UnauthorizedException {
        if (operatorService == null) {
            this.operatorService = SpringContextBeanService.getBean(IOperatorService.class);
        }
        String token = (String) auth.getCredentials();
        if (Constant.isPass) {
            return new SimpleAuthenticationInfo(token, token, this.getName());
        }
        // 解密获得username，用于和数据库进行对比
        String userNo = JWTUtil.getUserNo(token);
        if (userNo == null) {
            throw new UnauthorizedException("token invalid");
        }

        Map<String, Object> mso = new HashMap<>();
        mso.put("UserId", userNo);
        Operator userBean = operatorService.selectByMap(mso);
        if (userBean == null) {
            throw new UnauthorizedException("User didn't existed!");
        }
        if (!JWTUtil.verify(token, userNo, userBean.getUserInnerId() + "")) {
            throw new UnauthorizedException("Username or password error");
        }
        return new SimpleAuthenticationInfo(token, token, this.getName());
    }
}
