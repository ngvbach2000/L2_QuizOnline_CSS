/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.quizdetail;

import java.io.Serializable;

/**
 *
 * @author ngvba
 */
public class QuizDetailDTO implements Serializable{
    private String quizID;
    private String quizDetailID;

    public QuizDetailDTO(String quizID, String quizDetailID) {
        this.quizID = quizID;
        this.quizDetailID = quizDetailID;
    }

    public String getQuizID() {
        return quizID;
    }

    public void setQuizID(String quizID) {
        this.quizID = quizID;
    }

    public String getQuizDetailID() {
        return quizDetailID;
    }

    public void setQuizDetailID(String quizDetailID) {
        this.quizDetailID = quizDetailID;
    }
    
}
