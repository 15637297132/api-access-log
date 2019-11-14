使用插件要在你的web层实现以下几个接口
LoginInfoProvider，提供用户登录信息
LogPersistProvider，日志持久化代码
ReturnResultAnalyseProvider，Aop执行返回结果，根据结果去判断操作成功或者失败
这些接口，我都有默认实现，你需要根据你的工程定制化

案例参见类Main
ActionTypeEnum.GET/POST/DELETE/PASSWORD 拼接逻辑
遍历@Spell，依次拼接 prefix + RequestContext#getValue(codeKey) + suffix + customString + “。”，最后一个“。”被舍弃
for (Spell sp : spell) {
	builder.append(sp.prefix()).append(sp.codeKey().equals(ApiConstants.BLANK) ? ApiConstants.BLANK : requestContext.getValue(sp.codeKey())).append(sp.suffix()).append(sp.customString());
	builder.append("。");
}

ActionTypeEnum.PUT 拼接逻辑
检查@Compare的newObjkey和oldObjKey是否存在，存在的话对比两个对象带有ChangeFromTo或者FieldDefineName注解的属性，拼接不相同的属性内容，多个内容之间以“、”分隔，返回 diffFieldLog字符串，diffFieldLog不一定存在。
然后参与拼接，拼接规则 compare.prefix() + RequestContext#getValue(compare.codeKey) + compare.suffix() + “：” + diffFieldFlag，返回operateLog字符串
然后调用@Spell逻辑，返回spellLog。
判断operateLog!=null：如果spellLog!=null，在operateLog后加“；”，否则什么也不做
判断operateLog==null：如果spellLog!=null，operateLog = spellLog


ActionTypeEnum.ENABLED 拼接逻辑
首先从@ApiAccess#operateMethod数组中获取操作的动作，例下所示，获取启用还是禁用，然后遍历@Spell，最后拼接 动作 + spellLog
@ApiAccess(operateTitle = "禁用/启用", operateMethod = {"", "启用", "禁用"})
