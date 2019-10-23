package com.edu.api.log.v2.service.impl;

import com.edu.api.log.v2.model.LoginInfo;
import com.edu.api.log.v2.service.LoginInfoProvider;

import java.util.Date;

/**
 * @author Yangzhen
 * @Description
 * @date 2019-09-30 15:21
 **/
public class DefaultLoginInfoProviderImpl implements LoginInfoProvider {

    @Override
    public LoginInfo getLoginInfo() {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setOperatorId("0");
        loginInfo.setOperator("超级管理员");
        loginInfo.setOperateTime(new Date());
        loginInfo.setUsername("ClifeHotelSuperAdmin@het.com");
        loginInfo.setLocation("广东省深圳市");
        loginInfo.setBrowser("谷歌");
        loginInfo.setDevice("PC");
        loginInfo.setIp("193.112.98.222");
        loginInfo.setGroupId("0");
        loginInfo.setSubGroupId("0");
        return loginInfo;
    }
}
