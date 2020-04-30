package beans;

import java.io.Serializable;

public class Grade implements Serializable {
    private String courseName;//课程名
    private String stuNum;//学生学号
    private String stuName;//学生姓名
    private int credit;//课程学分
    private String variety;//类别
    private String evaluationMode;//考核方式
    private String character;//修读性质
    private int score;//成绩

    public Grade() {
        super();
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getEvaluationMode() {
        return evaluationMode;
    }

    public void setEvaluationMode(String evaluationMode) {
        this.evaluationMode = evaluationMode;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public Object getScore() {
        return score==-1?"未填入":score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
