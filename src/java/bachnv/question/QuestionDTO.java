/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.question;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ngvba
 */
public class QuestionDTO implements Serializable{
    private String questionID;
    private String subjectID;
    private String questionContent;
    private String correctAnswer;
    private Date createDate;
    private boolean status;

    public QuestionDTO() {
    }

    public QuestionDTO(String questionID, String subjectID, String questionContent, String correctAnswer, Date createDate, boolean status) {
        this.questionID = questionID;
        this.subjectID = subjectID;
        this.questionContent = questionContent;
        this.correctAnswer = correctAnswer;
        this.createDate = createDate;
        this.status = status;
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
}
