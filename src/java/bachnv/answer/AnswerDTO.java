/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.answer;

import java.io.Serializable;

/**
 *
 * @author ngvba
 */
public class AnswerDTO implements Serializable{
    private String answerID;
    private String questionID;
    private String answerContent;

    public AnswerDTO() {
    }

    public AnswerDTO(String answerID, String questionID, String answerContent) {
        this.answerID = answerID;
        this.questionID = questionID;
        this.answerContent = answerContent;
    }

    public String getAnswerID() {
        return answerID;
    }

    public void setAnswerID(String answerID) {
        this.answerID = answerID;
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }
    
}
