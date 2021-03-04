/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.quizanswer;

import java.io.Serializable;

/**
 *
 * @author ngvba
 */
public class QuizAnswerDTO implements Serializable{
    private String quizAnswerID;
    private String quizQuestionID;
    private String quizAnswerContent;

    public QuizAnswerDTO(String quizAnswerID, String quizQuestionID, String quizAnswerContent) {
        this.quizAnswerID = quizAnswerID;
        this.quizQuestionID = quizQuestionID;
        this.quizAnswerContent = quizAnswerContent;
    }

    public String getQuizAnswerID() {
        return quizAnswerID;
    }

    public void setQuizAnswerID(String quizAnswerID) {
        this.quizAnswerID = quizAnswerID;
    }

    public String getQuizQuestionID() {
        return quizQuestionID;
    }

    public void setQuizQuestionID(String quizQuestionID) {
        this.quizQuestionID = quizQuestionID;
    }

    public String getQuizAnswerContent() {
        return quizAnswerContent;
    }

    public void setQuizAnswerContent(String quizAnswerContent) {
        this.quizAnswerContent = quizAnswerContent;
    }
    
}
