/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.quiz;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ngvba
 */
public class QuizDTO implements Serializable{
    private String quizID;
    private String email;
    private String subjectID;
    private Date timeDuration;
    private float totalPoint;
    private boolean isFinish;
    private Date startDate;
    private Date finishDate;

    public QuizDTO(String quizID, String email, String subjectID, Date timeDuration, float totalPoint, boolean isFinish, Date startDate, Date finishDate) {
        this.quizID = quizID;
        this.email = email;
        this.subjectID = subjectID;
        this.timeDuration = timeDuration;
        this.totalPoint = totalPoint;
        this.isFinish = isFinish;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }
    
    public String getQuizID() {
        return quizID;
    }

    public void setQuizID(String quizID) {
        this.quizID = quizID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public Date getTimeDuration() {
        return timeDuration;
    }

    public void setTimeDuration(Date timeDuration) {
        this.timeDuration = timeDuration;
    }

    public float getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(float totalPoint) {
        this.totalPoint = totalPoint;
    }

    public boolean isIsFinish() {
        return isFinish;
    }

    public void setIsFinish(boolean isFinish) {
        this.isFinish = isFinish;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
}
