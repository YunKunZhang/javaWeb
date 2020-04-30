package beans;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class Teacher implements Serializable {
    private String num;//教师号
    private String name;//姓名
    private String sex;//性别
    private String department;//所在院系
    private String birthday;//出生日期
    private String phone;//联系电话
    private String rank;//职称
    private List<String> course;//所教课程
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public Teacher() {
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public List<String> getCourse() {
        return course;
    }

    public void setCourse(List<String> course) {
        this.course = course;
    }
}
