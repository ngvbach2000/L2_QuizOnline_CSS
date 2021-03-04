/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.quizdetail;

import bachnv.util.DBAccess;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author ngvba
 */
public class QuizDetailDAO implements Serializable{
    
    public boolean insertQuizDetail(QuizDetailDTO quizDetail) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Insert Into QuizDetail(quizID, quizDetailID) "
                        + "Values(?,?) ";
                ps = con.prepareStatement(sql);
                ps.setString(1, quizDetail.getQuizID());
                ps.setString(2, quizDetail.getQuizDetailID());
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
    
    public QuizDetailDTO getQuizDetailByQuizID(String quizID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select quizDetailID "
                        + "From QuizDetail "
                        + "Where quizID = ? ";
                ps = con.prepareStatement(sql);
                ps.setString(1, quizID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String quizDetailID = rs.getString(1);
                    QuizDetailDTO quizDetail = new QuizDetailDTO(quizID, quizDetailID);
                    return quizDetail;
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
        return null;
    }
    
}
