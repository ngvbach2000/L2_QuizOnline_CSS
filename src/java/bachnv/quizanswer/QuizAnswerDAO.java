/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.quizanswer;

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
public class QuizAnswerDAO implements Serializable{
    
    public boolean insertQuiz(QuizAnswerDTO quizAnswer) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Insert Into QuizAnswer(quizAnswerID, quizQuestionID, quizAnswerContent) "
                        + "Values(?,?,?) ";
                ps = con.prepareStatement(sql);
                ps.setString(1, quizAnswer.getQuizAnswerID());
                ps.setString(2, quizAnswer.getQuizQuestionID());
                ps.setString(3, quizAnswer.getQuizAnswerContent());
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
    
    private List<QuizAnswerDTO> listQuizAnswer;

    public List<QuizAnswerDTO> getListQuizAnswer() {
        return this.listQuizAnswer;
    }

    public void loadQuizAnswerByQuizQuestionID(String quizQuestionID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select quizAnswerID, quizAnswerContent "
                        + "From QuizAnswer "
                        + "Where quizQuestionID = ? ";
                ps = con.prepareStatement(sql);
                ps.setString(1, quizQuestionID);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String quizAnswerID = rs.getString(1);
                    String quizAnswerContent = rs.getString(2);
                    QuizAnswerDTO quizAnswer = new QuizAnswerDTO(quizAnswerID, quizQuestionID, quizAnswerContent);
                    if (listQuizAnswer == null) {
                        listQuizAnswer = new ArrayList<>();
                    }
                    listQuizAnswer.add(quizAnswer);
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
