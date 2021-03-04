/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.answer;

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
public class AnswerDAO implements Serializable{
    
    private List<AnswerDTO> listAnswer;

    public List<AnswerDTO> getListAnswer() {
        return this.listAnswer;
    }

    public void loadAnswerByQuestionID(String questionID) throws SQLException, NamingException{
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select answerID, answerContent "
                        + "From Answer "
                        + "Where questionID = ? ";
                ps = con.prepareStatement(sql);
                ps.setString(1, questionID);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String answerID = rs.getString(1);
                    String answerContent = rs.getString(2);
                    AnswerDTO answer = new AnswerDTO(answerID, questionID, answerContent);
                    if (listAnswer == null) {
                        listAnswer = new ArrayList<>();
                    }
                    listAnswer.add(answer);
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
    
    public boolean createAnswer(AnswerDTO answer) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Insert Into Answer(questionID, answerID, answerContent) "
                        + "Values (?,?,?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, answer.getQuestionID());
                ps.setString(2, answer.getAnswerID());
                ps.setString(3, answer.getAnswerContent());
                int row = ps.executeUpdate();
                while (row > 0) {
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
    
    public boolean updateAnswer(AnswerDTO answer) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Update Answer "
                        + "Set answerContent = ? "
                        + "Where answerID = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, answer.getAnswerContent());
                ps.setString(2, answer.getAnswerID());
                int row = ps.executeUpdate();
                while (row > 0) {
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
    
}
