package beans;

import java.io.Serializable;

public class Course implements Serializable {
    private String num;//课程id
    private String name;//课程名称
    private int credit;//课程学分
    private int period;//课程总学时
    private String variety;//类别
    private String teachingMethod;//授课方式
    private String evaluationMode;//考核方式
    private String teacherName;//授课老师
    private String people;//选课人数
    private String character;//修读性质
    private int score;//成绩
    private String status;//状态（是否被选）

    public Course() {
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

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getTeachingMethod() {
        return teachingMethod;
    }

    public void setTeachingMethod(String teachingMethod) {
        this.teachingMethod = teachingMethod;
    }

    public String getEvaluationMode() {
        return evaluationMode;
    }

    public void setEvaluationMode(String evaluationMode) {
        this.evaluationMode=evaluationMode;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(int people,int allowed) {
        this.people = people+"/"+allowed;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public Object getScore() {
        return score==-1?"未公布":score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status==1?"已选":"未选";
    }
}
