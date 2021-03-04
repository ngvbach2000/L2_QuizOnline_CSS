/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.question;

import java.io.Serializable;

/**
 *
 * @author ngvba
 */
public class QuestionCreateError implements Serializable{
    private String answer1LengthErr;
    private String answer2LengthErr;
    private String answer3LengthErr;
    private String answer4LengthErr;
    private String questionLengthErr;
    private String answerIsDupicate;

    public QuestionCreateError() {
    }

    public String getAnswer1LengthErr() {
        return answer1LengthErr;
    }

    public void setAnswer1LengthErr(String answer1LengthErr) {
        this.answer1LengthErr = answer1LengthErr;
    }

    public String getAnswer2LengthErr() {
        return answer2LengthErr;
    }

    public void setAnswer2LengthErr(String answer2LengthErr) {
        this.answer2LengthErr = answer2LengthErr;
    }

    public String getAnswer3LengthErr() {
        return answer3LengthErr;
    }

    public void setAnswer3LengthErr(String answer3LengthErr) {
        this.answer3LengthErr = answer3LengthErr;
    }

    public String getAnswer4LengthErr() {
        return answer4LengthErr;
    }

    public void setAnswer4LengthErr(String answer4LengthErr) {
        this.answer4LengthErr = answer4LengthErr;
    }

    public String getQuestionLengthErr() {
        return questionLengthErr;
    }

    public void setQuestionLengthErr(String questionLengthErr) {
        this.questionLengthErr = questionLengthErr;
    }

    public String getAnswerIsDupicate() {
        return answerIsDupicate;
    }

    public void setAnswerIsDupicate(String answerIsDupicate) {
        this.answerIsDupicate = answerIsDupicate;
    }
    
}
