/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.quiz;

import bachnv.util.DBAccess;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author ngvba
 */
public class QuizDAO implements Serializable {

    public boolean insertQuiz(QuizDTO quiz) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Insert Into Quiz(quizID, email, subjectID, timeDuration, totalPoint, isFinish, startDate) "
                        + "Values(?,?,?,?,?,?,?) ";
                ps = con.prepareStatement(sql);
                ps.setString(1, quiz.getQuizID());
                ps.setString(2, quiz.getEmail());
                ps.setString(3, quiz.getSubjectID());
                Time sqlTime = new Time(quiz.getTimeDuration().getTime());
                ps.setTime(4, sqlTime);
                ps.setDouble(5, quiz.getTotalPoint());
                ps.setBoolean(6, quiz.isIsFinish());
                Object sqlDate = new Timestamp(quiz.getStartDate().getTime());
                ps.setObject(7, sqlDate);
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
    
    
    public boolean updateTotalPoint(String quizID, float totalPoint, Date finishDate) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Update Quiz "
                        + "Set totalPoint = ?, isFinish = 'true', finishDate = ? "
                        + "Where quizID = ? ";
                ps = con.prepareStatement(sql);
                ps.setFloat(1, totalPoint);
                ps.setString(3, quizID);
                Object sqlDate = new Timestamp(finishDate.getTime());
                ps.setObject(2, sqlDate);
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

    public boolean checkIsFinish(String email, String subjectID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select isFinish "
                        + "From Quiz "
                        + "Where email = ? and subjectID = ?";
                boolean isFinish = true;
                ps = con.prepareStatement(sql);
                ps.setString(1, email);
                ps.setString(2, subjectID);
                rs = ps.executeQuery();
                while (rs.next()) {
                    boolean result = rs.getBoolean(1);
                    if (!result) {
                        isFinish = false;
                    }
                }
                return isFinish;
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
        return true;
    }

    public boolean checkIsNotDoing(String email) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select isFinish "
                        + "From Quiz "
                        + "Where email = ?";
                boolean isNotDoing = true;
                ps = con.prepareStatement(sql);
                ps.setString(1, email);
                rs = ps.executeQuery();
                while (rs.next()) {
                    boolean result = rs.getBoolean(1);
                    if (!result) {
                        isNotDoing = false;
                    }
                }
                return isNotDoing;
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
        return true;
    }

    public QuizDTO getQuizIsNotFinish(String email, String subjectID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select quizID, timeDuration, startDate "
                        + "From Quiz "
                        + "Where email = ? and subjectID = ? and isFinish = 'false' ";
                ps = con.prepareStatement(sql);
                ps.setString(1, email);
                ps.setString(2, subjectID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String quizID = rs.getString(1);
                    java.util.Date timeDuration = new java.util.Date(rs.getTime(2).getTime());
                    java.util.Date startDate = new java.util.Date(rs.getTimestamp(3).getTime());
                    QuizDTO quiz = new QuizDTO(quizID, email, subjectID, timeDuration, 0, false, startDate, null);
                    return quiz;
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

    private List<QuizDTO> listQuiz;

    public List<QuizDTO> getListQuiz() {
        return this.listQuiz;
    }

    public void getQuizByEmail(String email, int offset, int fetch) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select QuizID, subjectID, totalpoint, startDate, finishDate "
                        + "From Quiz "
                        + "Where email = ? and isFinish = 'true' "
                        + "Order by startDate DESC "
                        + "Offset ? Rows Fetch Next ? Rows Only ";
                ps = con.prepareStatement(sql);
                ps.setString(1, email);
                ps.setInt(2, offset);
                ps.setInt(3, fetch);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String quizID = rs.getString(1);
                    String subjectID = rs.getString(2);
                    float totalPoint = rs.getFloat(3);
                    Date startDate = new Date(rs.getTimestamp(4).getTime());
                    Date finishDate = new Date(rs.getTimestamp(5).getTime());
                    QuizDTO quiz = new QuizDTO(quizID, email, subjectID, null, totalPoint, true, startDate, finishDate);
                    if (listQuiz == null) {
                        listQuiz = new ArrayList<>();
                    }
                    listQuiz.add(quiz);
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

    public int getRecordsSearchQuizByEmail(String email) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select Count(QuizID) "
                        + "From Quiz "
                        + "Where email = ? ";
                ps = con.prepareStatement(sql);
                ps.setString(1, email);
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

    public void searchQuizBySubjectName(String subjectName, String email, int offset, int fetch) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select QuizID, Quiz.subjectID, totalpoint, startDate, finishDate "
                        + "From Quiz "
                        + "Inner Join Subject "
                        + "On Quiz.subjectID = Subject.subjectID "
                        + "Where Quiz.email = ? and Subject.subjectName LIKE ? "
                        + "Order by startDate DESC "
                        + "Offset ? Rows Fetch Next ? Rows Only ";
                ps = con.prepareStatement(sql);
                ps.setString(1, email);
                ps.setString(2, "%" + subjectName + "%");
                ps.setInt(3, offset);
                ps.setInt(4, fetch);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String quizID = rs.getString(1);
                    String subjectID = rs.getString(2);
                    float totalPoint = rs.getFloat(3);
                    Date startDate = new Date(rs.getTimestamp(4).getTime());
                    Date finishDate = new Date(rs.getTimestamp(5).getTime());
                    QuizDTO quiz = new QuizDTO(quizID, email, subjectID, null, totalPoint, true, startDate, finishDate);
                    if (listQuiz == null) {
                        listQuiz = new ArrayList<>();
                    }
                    listQuiz.add(quiz);
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

    public int getRecordsSearchQuizBySubjectName(String subjectName, String email) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select Count(quizID) "
                        + "From Quiz "
                        + "Inner Join Subject "
                        + "On Quiz.subjectID = Subject.subjectID "
                        + "Where Quiz.email = ? and Subject.subjectName LIKE ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, email);
                ps.setString(2, "%" + subjectName + "%");
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

    public QuizDTO getQuizByID(String quizID, String email) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select subjectID, timeDuration, totalpoint, startDate, finishDate "
                        + "From Quiz "
                        + "Where quizID = ? and email = ? and isFinish = 'true'";
                ps = con.prepareStatement(sql);
                ps.setString(1, quizID);
                ps.setString(2, email);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String subjectID = rs.getString(1);
                    Date timeDuration = new java.sql.Time(rs.getTime(2).getTime());
                    float totalPoint = rs.getFloat(3);
                    Date startDate = new Date(rs.getTimestamp(4).getTime());
                    Date finishDate = new Date(rs.getTimestamp(5).getTime());
                    QuizDTO quiz = new QuizDTO(quizID, email, subjectID, timeDuration, totalPoint, true, startDate, finishDate);
                    return quiz;
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
