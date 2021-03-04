/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.quizquestion;

import java.io.Serializable;

/**
 *
 * @author ngvba
 */
public class QuizQuestionDTO implements Serializable{
    private String quizQuestionID;
    private String quizDetailID;
    private String quizQuestionContent;
    private String quizCorrectAnswer;
    private String selectionAnswer;

    public QuizQuestionDTO(String quizQuestionID, String quizDetailID, String quizQuestionContent, String quizCorrectAnswer, String selectionAnswer) {
        this.quizQuestionID = quizQuestionID;
        this.quizDetailID = quizDetailID;
        this.quizQuestionContent = quizQuestionContent;
        this.quizCorrectAnswer = quizCorrectAnswer;
        this.selectionAnswer = selectionAnswer;
    }

    public String getQuizQuestionID() {
        return quizQuestionID;
    }

    public void setQuizQuestionID(String quizQuestionID) {
        this.quizQuestionID = quizQuestionID;
    }

    public String getQuizDetailID() {
        return quizDetailID;
    }

    public void setQuizDetailID(String quizDetailID) {
        this.quizDetailID = quizDetailID;
    }

    public String getQuizQuestionContent() {
        return quizQuestionContent;
    }

    public void setQuizQuestionContent(String quizQuestionContent) {
        this.quizQuestionContent = quizQuestionContent;
    }

    public String getQuizCorrectAnswer() {
        return quizCorrectAnswer;
    }

    public void setQuizCorrectAnswer(String quizCorrectAnswer) {
        this.quizCorrectAnswer = quizCorrectAnswer;
    }

    public String getSelectionAnswer() {
        return selectionAnswer;
    }

    public void setSelectionAnswer(String selectionAnswer) {
        this.selectionAnswer = selectionAnswer;
    }
    
}
