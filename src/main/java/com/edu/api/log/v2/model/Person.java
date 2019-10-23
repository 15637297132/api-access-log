package com.edu.api.log.v2.model;

import com.edu.api.log.v2.annotation.model.ChangeFromTo;
import com.edu.api.log.v2.annotation.model.FieldDefineName;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Yangzhen
 * @Description
 * @date 2019-09-29 17:24
 **/
public class Person implements Serializable {

    private static final long serialVersionUID = -8891924651069731960L;

    private Long id;
    @ChangeFromTo(fieldDefineName = @FieldDefineName(name = "姓名"), prefix = "(", action = "改为了", suffix = ")")
    private String name;
    @FieldDefineName(name = "年龄")
    private Integer age;
    @FieldDefineName(name = "性别")
    private int sex;
    @ChangeFromTo(fieldDefineName = @FieldDefineName(name = "所属部门"), prefix = "(", action = "调至", suffix = ")")
    private String deptName;
    private Date dateTime;
    private Integer state;
    private Integer enabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }
}
