/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.question;

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
public class QuestionDAO implements Serializable {

    private List<QuestionDTO> listQuestion;

    public List<QuestionDTO> getListQuestion() {
        return this.listQuestion;
    }

    public void loadQuestionBySubjectID(String subjectID, int offset, int fetch) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select questionID, questionContent, correctAnswer, status "
                        + "From Question "
                        + "Where subjectID = ? "
                        + "Order By questionContent ASC "
                        + "Offset ? Rows Fetch Next ? Rows Only ";
                ps = con.prepareStatement(sql);
                ps.setString(1, subjectID);
                ps.setInt(2, offset);
                ps.setInt(3, fetch);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String questionID = rs.getString(1);
                    String questionContent = rs.getString(2);
                    String correctAnswer = rs.getString(3);
                    boolean status = rs.getBoolean(4);
                    QuestionDTO question = new QuestionDTO(questionID, subjectID, questionContent, correctAnswer, null, status);
                    if (listQuestion == null) {
                        listQuestion = new ArrayList<>();
                    }
                    listQuestion.add(question);
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
    
    public int countQuestionBySubjectID(String subjectID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select Count(questionID) "
                        + "From Question "
                        + "Where subjectID = ? ";
                ps = con.prepareStatement(sql);
                ps.setString(1, subjectID);
                rs = ps.executeQuery();
                while (rs.next()) {
                    return rs.getInt(1);
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
        return 0;
    }

    public void searchQuestionByName(String subjectID, String name, int offset, int fetch) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select questionID, questionContent, correctAnswer, status "
                        + "From Question "
                        + "Where subjectID = ? and questionContent LIKE ? "
                        + "Order By questionContent ASC "
                        + "Offset ? Rows Fetch Next ? Rows Only ";
                ps = con.prepareStatement(sql);
                ps.setString(1, subjectID);
                ps.setString(2, "%" + name + "%");
                ps.setInt(3, offset);
                ps.setInt(4, fetch);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String questionID = rs.getString(1);
                    String questionContent = rs.getString(2);
                    String correctAnswer = rs.getString(3);
                    boolean status = rs.getBoolean(4);
                    QuestionDTO question = new QuestionDTO(questionID, subjectID, questionContent, correctAnswer, null, status);
                    if (listQuestion == null) {
                        listQuestion = new ArrayList<>();
                    }
                    listQuestion.add(question);
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
    
    public int countSearchQuestionByName(String subjectID, String name) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select Count(questionID) "
                        + "From Question "
                        + "Where subjectID = ? and questionContent LIKE ? ";
                ps = con.prepareStatement(sql);
                ps.setString(1, subjectID);
                ps.setString(2, "%" + name + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    return rs.getInt(1);
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
        return 0;
    }

    public void searchQuestionByStatus(String subjectID, boolean status, int offset, int fetch) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select questionID, questionContent, correctAnswer "
                        + "From Question "
                        + "Where subjectID = ? and status = ? "
                        + "Order By questionContent ASC "
                        + "Offset ? Rows Fetch Next ? Rows Only ";
                ps = con.prepareStatement(sql);
                ps.setString(1, subjectID);
                ps.setBoolean(2, status);
                ps.setInt(3, offset);
                ps.setInt(4, fetch);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String questionID = rs.getString(1);
                    String questionContent = rs.getString(2);
                    String correctAnswer = rs.getString(3);
                    QuestionDTO question = new QuestionDTO(questionID, subjectID, questionContent, correctAnswer, null, status);
                    if (listQuestion == null) {
                        listQuestion = new ArrayList<>();
                    }
                    listQuestion.add(question);
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
    
    public int countSearchQuestionByStatus(String subjectID, boolean status) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select Count(questionID) "
                        + "From Question "
                        + "Where subjectID = ? and status = ? ";
                ps = con.prepareStatement(sql);
                ps.setString(1, subjectID);
                ps.setBoolean(2, status);
                rs = ps.executeQuery();
                while (rs.next()) {
                    return rs.getInt(1);
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
        return 0;
    }

    public void searchQuestionByNameAndStatus(String subjectID, String name, boolean status, int offset, int fetch) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select questionID, questionContent, correctAnswer "
                        + "From Question "
                        + "Where subjectID = ? and questionContent LIKE ? and status = ? "
                        + "Order By questionContent ASC "
                        + "Offset ? Rows Fetch Next ? Rows Only ";
                ps = con.prepareStatement(sql);
                ps.setString(1, subjectID);
                ps.setString(2, "%" + name + "%");
                ps.setBoolean(3, status);
                ps.setInt(4, offset);
                ps.setInt(5, fetch);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String questionID = rs.getString(1);
                    String questionContent = rs.getString(2);
                    String correctAnswer = rs.getString(3);
                    QuestionDTO question = new QuestionDTO(questionID, subjectID, questionContent, correctAnswer, null, status);
                    if (listQuestion == null) {
                        listQuestion = new ArrayList<>();
                    }
                    listQuestion.add(question);
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
    
    public int countSearchQuestionByNameAndStatus(String subjectID, String name, boolean status) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select Count(questionID) "
                        + "From Question "
                        + "Where subjectID = ? and questionContent LIKE ? and status = ? ";
                ps = con.prepareStatement(sql);
                ps.setString(1, subjectID);
                ps.setString(2, "%" + name + "%");
                ps.setBoolean(3, status);
                rs = ps.executeQuery();
                while (rs.next()) {
                    return rs.getInt(1);
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
        return 0;
    }

    public boolean createQuestion(QuestionDTO question) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Insert Into Question(questionID, subjectID, questionContent, correctAnswer, createDate, status) "
                        + "Values (?,?,?,?,?,?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, question.getQuestionID());
                ps.setString(2, question.getSubjectID());
                ps.setString(3, question.getQuestionContent());
                ps.setString(4, question.getCorrectAnswer());
                java.sql.Date sqlDate = new java.sql.Date(question.getCreateDate().getTime());
                ps.setDate(5, sqlDate);
                ps.setBoolean(6, question.isStatus());
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
    
    public boolean updateQuestion(QuestionDTO question) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Update Question "
                        + "Set questionContent = ?, correctAnswer = ?, status = ? "
                        + "Where QuestionID = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, question.getQuestionContent());
                ps.setString(2, question.getCorrectAnswer());
                ps.setBoolean(3, question.isStatus());
                ps.setString(4, question.getQuestionID());
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

    public boolean deleteQuestion(String questionID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Update Question "
                        + "Set status = 'false' "
                        + "Where QuestionID = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, questionID);
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
    
    private List<QuestionDTO> listQuizQuestion;

    public List<QuestionDTO> getListQuizQuestion() {
        return this.listQuizQuestion;
    }

    public void loadQuizQuestionBySubjectID(String subjectID, int numOfQuest) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select Top (?) questionID, questionContent, correctAnswer "
                        + "From Question "
                        + "Where subjectID = ? and status = 'true' "
                        + "Order By NEWID()";
                ps = con.prepareStatement(sql);
                ps.setInt(1, numOfQuest);
                ps.setString(2, subjectID);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String questionID = rs.getString(1);
                    String questionContent = rs.getString(2);
                    String correctAnswer = rs.getString(3);
                    QuestionDTO question = new QuestionDTO(questionID, subjectID, questionContent, correctAnswer, null, true);
                    if (listQuizQuestion == null) {
                        listQuizQuestion = new ArrayList<>();
                    }
                    listQuizQuestion.add(question);
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
    
    public int countQuizQuestionBySubjectID(String subjectID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select Count (questionID) "
                        + "From Question "
                        + "Where subjectID = ? and status = 'true' ";
                ps = con.prepareStatement(sql);
                ps.setString(1, subjectID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
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
        return 0;
    }
    
}
