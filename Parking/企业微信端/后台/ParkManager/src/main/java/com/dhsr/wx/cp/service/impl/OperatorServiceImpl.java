package com.dhsr.wx.cp.service.impl;

import com.alibaba.fastjson.JSONObject;

import com.dhsr.wx.cp.base.Constant;
import com.dhsr.wx.cp.config.WxCpConfiguration;
import com.dhsr.wx.cp.entity.Operator;
import com.dhsr.wx.cp.mapper.OperatorMapper;
import com.dhsr.wx.cp.base.BusinessException;
import com.dhsr.wx.cp.base.PublicResultConstant;
import com.dhsr.wx.cp.service.IOperatorService;
import com.dhsr.wx.cp.utils.Base64Utils;
import com.dhsr.wx.cp.utils.ComUtil;
import com.dhsr.wx.cp.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpMessage;
import me.chanjar.weixin.cp.bean.WxCpUser;
import me.chanjar.weixin.cp.bean.article.NewArticle;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 操作员 服务实现类
 * </p>
 *
 * @author liuxiaogang123
 * @since 2019-03-18
 */
@Service
@Slf4j
@Transactional
public class OperatorServiceImpl implements IOperatorService {

    @Resource
    private OperatorMapper operatorMapper;


    /**
     * 查找操作员信息
     *
     * @param map
     * @return
     */
    @Override
    public Operator selectByMap(Map<String, Object> map) {

        System.out.println("进入map = " + map);
        Operator operator = operatorMapper.selectOperatorByMap(map);
        return operator;
    }

    /**
     * @param operator
     * @return
     */
    @Override
    public Map<String, Object> getLoginUserAndMenuInfo(Operator operator) {
        Map<String, Object> result = new HashMap<>();
        operator.setToken(JWTUtil.sign(operator.getUserId(), operator.getUserInnerId() + ""));
        result.put("operator", operator);
        return result;
    }

    /**
     * 验证用户名密码
     *
     * @param requestJson
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> checkUserIdAndPasswd(JSONObject requestJson) throws Exception {

        WxCpService wxCpService = WxCpConfiguration.getCpService(Constant.WX_CODE);
        Map<String, Object> mso = new HashMap<>();
        String code = requestJson.getString("code");
        String[] res = wxCpService.getOauth2Service().getUserInfo(code);

        WxCpUser wxCpUser = wxCpService.getUserService().getById(res[0]);

        log.info(wxCpUser.getMobile()+"==========人员编号");
        mso.put("Mobile", wxCpUser.getMobile());
        Operator operator = operatorMapper.selectOperatorByMap(mso);
        //System.out.println(BCrypt.hashpw("123456", BCrypt.gensalt()));
        //String pwText = Base64Utils.decode(password);
        if (operator == null) {
            throw new BusinessException(PublicResultConstant.INVALID_USER);
        }
        Map<String,Object> objectMap = new HashMap<>(2);
        objectMap.put("StandbyD",wxCpUser.getUserId());
        objectMap.put("UserInnerId",operator.getUserInnerId());

        operatorMapper.updateOperator(objectMap);

        operator.setImageUrl(wxCpUser.getAvatar());

        System.out.println(this.getLoginUserAndMenuInfo(operator));
        return this.getLoginUserAndMenuInfo(operator);

    }

    @Override
    public Map<String, Object> checkUserIdAndPasswdOA(JSONObject requestJson) throws Exception {
        Map<String, Object> mso = new HashMap<>();
        mso.put("UserId", requestJson.getString("UserId"));
        Operator operator = operatorMapper.selectOperatorByMap(mso);
        //System.out.println(BCrypt.hashpw("123456", BCrypt.gensalt()));
        //String pwText = Base64Utils.decode(password);
        if (operator == null) {
            throw new BusinessException(PublicResultConstant.INVALID_USER);
        }
        return this.getLoginUserAndMenuInfo(operator);
    }
}
