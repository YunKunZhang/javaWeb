package beans;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Administrator {
    private String num;// 工作号
    private String name;//姓名
    private String sex;//性别
    private String identity;//身份
    private String birthday;//出生日期
    private String phone;//联系电话
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public Administrator() {
        super();
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = format.format(birthday);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
