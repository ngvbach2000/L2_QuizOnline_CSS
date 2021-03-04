/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.quizquestion;

import bachnv.util.DBAccess;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author ngvba
 */
public class QuizQuestionDAO implements Serializable {

    public boolean insertQuiz(QuizQuestionDTO quizQuestion) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Insert Into QuizQuestion(quizQuestionID, quizDetailID, quizQuestionContent, quizCorrectAnswer) "
                        + "Values(?,?,?,?) ";
                ps = con.prepareStatement(sql);
                ps.setString(1, quizQuestion.getQuizQuestionID());
                ps.setString(2, quizQuestion.getQuizDetailID());
                ps.setString(3, quizQuestion.getQuizQuestionContent());
                ps.setString(4, quizQuestion.getQuizCorrectAnswer());
                int row = ps.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public boolean updateSelectionAnswer(String quizQuestionID, String selectionAnswer) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Update QuizQuestion "
                        + "Set selectionAnswer = ? "
                        + "Where quizQuestionID = ? ";
                ps = con.prepareStatement(sql);
                ps.setString(1, selectionAnswer);
                ps.setString(2, quizQuestionID);
                int row = ps.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    private List<QuizQuestionDTO> listQuizQuestion;

    public List<QuizQuestionDTO> getListQuizQuestion() {
        return this.listQuizQuestion;
    }

    public void loadQuizQuestionByQuizDeatilID(String quizDetailID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select quizQuestionID, quizQuestionContent, quizCorrectAnswer, selectionAnswer "
                        + "From QuizQuestion "
                        + "Where quizDetailID = ? ";
                ps = con.prepareStatement(sql);
                ps.setString(1, quizDetailID);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String quizQuestionID = rs.getString(1);
                    String quizQuestionContent = rs.getString(2);
                    String quizCorrectAnswer = rs.getString(3);
                    String selectionAnswer = rs.getString(4);
                    QuizQuestionDTO quizQuestion = new QuizQuestionDTO(quizQuestionID, quizDetailID, quizQuestionContent, quizCorrectAnswer, selectionAnswer);
                    if (listQuizQuestion == null) {
                        listQuizQuestion = new ArrayList<>();
                    }
                    listQuizQuestion.add(quizQuestion);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
}
