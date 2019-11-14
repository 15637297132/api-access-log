package com.edu.api.log.v2;

import com.edu.api.log.v2.annotation.*;
import com.edu.api.log.v2.constants.ActionTypeEnum;
import com.edu.api.log.v2.constants.ApiLogTypeEnum;
import com.edu.api.log.v2.model.LoginInfo;
import com.edu.api.log.v2.model.Person;
import com.edu.api.log.v2.util.DataBuilder;
import com.edu.api.log.v2.util.Jsr303Utils;
import com.edu.api.log.v2.util.RequestContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * #操作者名#重新启用了#xxx#
 * #操作者名#禁用了#xxx#
 * #操作者名#修改了#xxx#的信息：名称（#名称1#改为了#名称2#）、负责人、联系电话
 * #操作者名#新增了一条信息：#xxx#
 * <p>
 * #操作者名#重新启用了#xxx#。所属上级：#所属上级名#
 * <p>
 * #操作者名#重新启用了人员#人员名#。所属部门：#所属部门名#
 * #操作者名#修改了人员#人员名#的密码
 * #操作者名#修改了自己的密码
 * <p>
 * 角色
 * #操作者名#新增了一条角色信息：#角色名#
 *
 * @author Yangzhen
 * @Description
 * @date 2019-09-29 16:55
 **/
@ApiModule(moduleId = 0, moduleName = "demo")
@Component
public class Main {

    private static RequestContext requestContext = null;

    public static void main(String[] args) {
        testAOP();
    }

    public static void testJsr303() {
        LoginInfo loginInfo = DataBuilder.newDataByMethod(LoginInfo.class);
        loginInfo.setOperatorId(null);
        boolean result = Jsr303Utils.verifyObjectAttributesDefect(loginInfo);
        System.out.println(result);
        String msg = Jsr303Utils.verifyObjectAttributesDefectWithErrorMsg(loginInfo);
        System.out.println(msg);
    }

    public static void testAOP() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/spring-api-log-v2.xml");
        requestContext = context.getBean(RequestContext.class);
        Main bean = context.getBean(Main.class);
        Person person = DataBuilder.newDataByMethod(Person.class);
        bean.login();
        bean.logout();
        bean.insert(person);
        bean.updatePersonalPassword(person);
        bean.updatePassword(person);
        bean.enabled(person);
        bean.disabled(person);
        bean.update(person);
        bean.update1(person);
        bean.update();
        bean.update2(person);
        bean.update3(person);
        bean.delete(person);
        bean.updatePassword(true, "张三");
        bean.updatePassword(false, "张三");
        bean.exception();
    }

    @ApiAccess(operateTitle = "登录", operateMethod = "登录", dataIdKey = "dataIdKey", logType = ApiLogTypeEnum.LOGON_LOG_TYPE, action = @ApiAction(actionType = ActionTypeEnum.POST, spell = @Spell(prefix = "登录了系统")))
    public Object login() {
        requestContext.setValue("dataIdKey", "1");
        return null;
    }

    @ApiAccess(operateTitle = "登出", operateMethod = "登出", dataIdKey = "dataIdKey", logType = ApiLogTypeEnum.LOGON_LOG_TYPE, logout = true, action = @ApiAction(actionType = ActionTypeEnum.POST, spell = @Spell(prefix = "登出了系统")))
    public Object logout() {
        requestContext.setValue("dataIdKey", "1");
        return null;
    }

    @ApiAccess(operateTitle = "保存人员", operateMethod = "保存", dataIdKey = "dataIdKey", action = @ApiAction(actionType = ActionTypeEnum.POST, spell = @Spell(prefix = "新增了一条人员信息：", codeKey = "nickName")))
    public Object insert(Person person) {
        requestContext.setValue("dataIdKey", person.getId().toString());
        requestContext.setValue("nickName", person.getName().toString());
        return null;
    }

    @ApiAccess(operateTitle = "修改密码", operateMethod = "修改密码", dataIdKey = "dataIdKey", action = @ApiAction(actionType = ActionTypeEnum.PASSWORD, spell = @Spell(prefix = "修改了自己的密码")))
    public Object updatePersonalPassword(Person person) {
        requestContext.setValue("dataIdKey", person.getId().toString());
        return null;
    }

    @ApiAccess(operateTitle = "修改密码", operateMethod = "修改密码", dataIdKey = "dataIdKey", action = @ApiAction(actionType = ActionTypeEnum.PASSWORD, spell = @Spell(prefix = "修改了人员", codeKey = "username", suffix = "的密码")))
    public Object updatePassword(Person person) {
        requestContext.setValue("dataIdKey", person.getId().toString());
        requestContext.setValue("username", person.getName());
        return null;
    }

    @ApiAccess(operateTitle = "禁用/启用", operateMethod = {"", "启用", "禁用"}, dataIdKey = "dataIdKey", indexKeyForOperateMethod = "indexKeyForOperateMethod", action = @ApiAction(actionType = ActionTypeEnum.ENABLED, spell = {@Spell(prefix = "了人员", codeKey = "nickname"), @Spell(prefix = "所属部门：", codeKey = "deptName")}))
    public Object enabled(Person person) {
        requestContext.setValue("dataIdKey", person.getId().toString());
        requestContext.setValue("indexKeyForOperateMethod", 1);
        requestContext.setValue("nickname", person.getName());
        requestContext.setValue("deptName", "测试部门");

        return null;
    }

    @ApiAccess(operateTitle = "禁用/启用", operateMethod = {"", "启用", "禁用"}, dataIdKey = "dataIdKey", indexKeyForOperateMethod = "indexKeyForOperateMethod", action = @ApiAction(actionType = ActionTypeEnum.ENABLED, spell = {@Spell(prefix = "了人员", codeKey = "nickname"), @Spell(prefix = "所属部门：", codeKey = "deptName")}))
    public Object disabled(Person person) {
        requestContext.setValue("dataIdKey", person.getId().toString());
        requestContext.setValue("indexKeyForOperateMethod", 2);
        requestContext.setValue("nickname", person.getName());
        requestContext.setValue("deptName", "测试部门");

        return null;
    }

    @ApiAccess(operateTitle = "修改", operateMethod = "修改", dataIdKey = "dataIdKey", action = @ApiAction(actionType = ActionTypeEnum.PUT, compare = @Compare(prefix = "修改了人员", codeKey = "nickname", newObjKey = "newObj", oldObjKey = "oldObj", suffix = "的信息")))
    public Object update(Person newObj) {
        Person oldObj = DataBuilder.newDataByMethod(Person.class);
        requestContext.setValue("dataIdKey", newObj.getId().toString());
        requestContext.setValue("nickname", oldObj.getName());
        requestContext.setValue("newObj", newObj);
        requestContext.setValue("oldObj", oldObj);
        return null;
    }

    @ApiAccess(operateTitle = "修改", operateMethod = "修改", dataIdKey = "dataIdKey", action = @ApiAction(actionType = ActionTypeEnum.PUT, compare = @Compare(prefix = "修改了人员", codeKey = "nickname", newObjKey = "newObj", oldObjKey = "oldObj", suffix = "的信息"), spell = @Spell(prefix = "所属部门：", codeKey = "deptName")))
    public Object update1(Person newObj) {
        Person oldObj = DataBuilder.newDataByMethod(Person.class);
        requestContext.setValue("dataIdKey", newObj.getId().toString());
        requestContext.setValue("nickname", oldObj.getName());
        requestContext.setValue("newObj", newObj);
        requestContext.setValue("oldObj", oldObj);
        requestContext.setValue("deptName", "测试部门");
        return null;
    }

    @ApiAccess(operateTitle = "修改", operateMethod = "修改", dataIdKey = "dataIdKey", action = @ApiAction(actionType = ActionTypeEnum.PUT, spell = {@Spell(prefix = "修改了角色", codeKey = "roleName", suffix = "的信息：", customString = "权限分配")}))
    public Object update() {
        requestContext.setValue("dataIdKey", "1");
        requestContext.setValue("roleName", "前台");
        return null;
    }

    @ApiAccess(operateTitle = "修改", operateMethod = "修改", dataIdKey = "dataIdKey", action = @ApiAction(actionType = ActionTypeEnum.PUT, compare = @Compare(prefix = "修改了人员", codeKey = "nickname", newObjKey = "newObj", oldObjKey = "oldObj", suffix = "的信息"), spell = {@Spell(codeKey = "oldStateToNewState")}))
    public Object update2(Person newObj) {
        Person oldObj = DataBuilder.newDataByMethod(Person.class);
        requestContext.setValue("dataIdKey", newObj.getId().toString());
        requestContext.setValue("nickname", oldObj.getName());
        requestContext.setValue("newObj", newObj);
        requestContext.setValue("oldObj", oldObj);
        requestContext.setValue("oldStateToNewState", "状态：禁用改为了启用");
        return null;
    }

    @ApiAccess(operateTitle = "修改", operateMethod = "修改", dataIdKey = "dataIdKey", action = @ApiAction(actionType = ActionTypeEnum.PUT, compare = @Compare(prefix = "修改了人员", codeKey = "nickname", suffix = "的信息"), spell = {@Spell(codeKey = "oldStateToNewState")}))
    public Object update3(Person newObj) {
        Person oldObj = DataBuilder.newDataByMethod(Person.class);
        requestContext.setValue("dataIdKey", newObj.getId().toString());
        requestContext.setValue("nickname", oldObj.getName());
        requestContext.setValue("oldStateToNewState", "状态：禁用改为了启用");
        return null;
    }

    @ApiAccess(operateTitle = "删除", operateMethod = "删除", dataIdKey = "dataIdKey", action = @ApiAction(actionType = ActionTypeEnum.DELETE, spell = {@Spell(prefix = "删除了人员", codeKey = "nickname")}))
    public Object delete(Person person) {
        requestContext.setValue("dataIdKey", person.getId().toString());
        requestContext.setValue("nickname", person.getName());
        return null;
    }

    @ApiAccess(operateTitle = "异常", operateMethod = "异常", dataIdKey = "dataIdKey")
    public Object exception() {
        requestContext.setValue("dataIdKey", "1");
        throw new RuntimeException("运行时异常");
    }

    @ApiAccess(operateTitle = "修改密码", operateMethod = "修改密码", dataIdKey = "dataIdKey", action = @ApiAction(actionType = ActionTypeEnum.POST, spell = @Spell(prefix = "修改了", codeKey = "nickName", suffix = "的密码")))
    public Object updatePassword(boolean flag, String nickname) {
        if (flag) {
            requestContext.setValue("nickName", "自己");
        } else {
            requestContext.setValue("nickName", "人员" + nickname);
        }
        requestContext.setValue("dataIdKey", "123");

        return null;
    }
}
