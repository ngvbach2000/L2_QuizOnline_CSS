/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.subject;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ngvba
 */
public class SubjectDTO implements Serializable{
    private String subjectID;
    private String subjectName;
    private int numberOfQuestion;
    private Date beginDate;
    private Date endDate;
    private Date timeDuration;

    public SubjectDTO(String subjectID, String subjectName, int numberOfQuestion, Date beginDate, Date endDate, Date timeDuration) {
        this.subjectID = subjectID;
        this.subjectName = subjectName;
        this.numberOfQuestion = numberOfQuestion;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.timeDuration = timeDuration;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getNumberOfQuestion() {
        return numberOfQuestion;
    }

    public void setNumberOfQuestion(int numberOfQuestion) {
        this.numberOfQuestion = numberOfQuestion;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getTimeDuration() {
        return timeDuration;
    }

    public void setTimeDuration(Date timeDuration) {
        this.timeDuration = timeDuration;
    }
    
}
