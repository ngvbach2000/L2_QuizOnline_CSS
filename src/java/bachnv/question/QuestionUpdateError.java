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
public class QuestionUpdateError implements Serializable {

    private String answerLengthErr;
    private String questionLengthErr;
    private String answerIsDupicate;

    public QuestionUpdateError() {
    }

    public String getAnswerLengthErr() {
        return answerLengthErr;
    }

    public void setAnswerLengthErr(String answerLengthErr) {
        this.answerLengthErr = answerLengthErr;
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
