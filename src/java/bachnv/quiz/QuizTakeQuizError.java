/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.quiz;

import java.io.Serializable;

/**
 *
 * @author ngvba
 */
public class QuizTakeQuizError implements Serializable {

    private String cannotTakeManyQuiz;
    private String quizIsClose;
    private String quizDontHaveQuestion;

    public QuizTakeQuizError() {
    }

    public String getCannotTakeManyQuiz() {
        return cannotTakeManyQuiz;
    }

    public void setCannotTakeManyQuiz(String cannotTakeManyQuiz) {
        this.cannotTakeManyQuiz = cannotTakeManyQuiz;
    }

    public String getQuizIsClose() {
        return quizIsClose;
    }

    public void setQuizIsClose(String quizIsClose) {
        this.quizIsClose = quizIsClose;
    }

    public String getQuizDontHaveQuestion() {
        return quizDontHaveQuestion;
    }

    public void setQuizDontHaveQuestion(String quizDontHaveQuestion) {
        this.quizDontHaveQuestion = quizDontHaveQuestion;
    }

}
