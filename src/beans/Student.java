package beans;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class Student implements Serializable {
    private String num;// 学号
    private String name;//姓名
    private String sex;//性别
    private String department;//所在院系
    private String major;//所学专业
    private String birthday;//出生日期
    private String enterDate;//入学日期
    private String phone;//联系电话
    private List<String> course;//所选课程
    private List<Integer> score;//分数
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public Student() {
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
        this.sex = sex.equals("1") ? "男" : "女";
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMajor() {
        return major;
    }

    public void setBirthday(Date birthday) {
        this.birthday = format.format(birthday);
    }

    public String getBirthday() {
        return birthday;
    }

    public void setEnterDate(Date enterDate) {
        this.enterDate = format.format(enterDate);
    }

    public String getEnterDate() {
        return enterDate;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public List<String> getCourse() {
        return course;
    }

    public void setCourse(List<String> course) {
        this.course = course;
    }

    public List<Integer> getScore() {
        return score;
    }

    public void setScore(List<Integer> score) {
        this.score = score;
    }
}
