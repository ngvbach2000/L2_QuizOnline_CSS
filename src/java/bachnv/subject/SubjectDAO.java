/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.subject;

import bachnv.util.DBAccess;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author ngvba
 */
public class SubjectDAO implements Serializable {

    private List<SubjectDTO> listSubject;

    public List<SubjectDTO> getListSubject() {
        return this.listSubject;
    }

    public void loadSubject() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select subjectID, subjectName, numberOfQuestion, beginDate, endDate, timeDuration "
                        + "From Subject ";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String id = rs.getString(1);
                    String name = rs.getString(2);
                    int numberOfQuestion = rs.getInt(3);
                    Date beginDate = rs.getDate(4);
                    Date endDate = rs.getDate(5);
                    Date timeDuration = new java.sql.Time(rs.getTime(6).getTime());
                    SubjectDTO subject = new SubjectDTO(id, name, numberOfQuestion, beginDate, endDate, timeDuration);
                    if (listSubject == null) {
                        listSubject = new ArrayList<>();
                    }
                    listSubject.add(subject);
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

    public SubjectDTO getSubjectByID(String id) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select subjectName, numberOfQuestion, beginDate, endDate, timeDuration  "
                        + "From Subject "
                        + "Where subjectID = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, id);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String name = rs.getString(1);
                    int numberOfQuestion = rs.getInt(2);
                    Date beginDate = rs.getDate(3);
                    Date endDate = rs.getDate(4);
                    Date timeDuration = new java.sql.Time(rs.getTime(5).getTime());
                    SubjectDTO subject = new SubjectDTO(id, name, numberOfQuestion, beginDate, endDate, timeDuration);
                    return subject;
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

    public boolean updateSubject(SubjectDTO subject) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Update Subject  "
                        + "Set numberOfQuestion = ?, beginDate = ?, endDate = ?, timeDuration = ? "
                        + "Where subjectID = ? ";
                ps = con.prepareStatement(sql);
                ps.setInt(1, subject.getNumberOfQuestion());
                java.sql.Date sqlBeginDate = new java.sql.Date(subject.getBeginDate().getTime());
                ps.setDate(2, sqlBeginDate);
                java.sql.Date sqlEndDate = new java.sql.Date(subject.getEndDate().getTime());
                ps.setDate(3, sqlEndDate);
                java.sql.Time sqlTimeDuration = new java.sql.Time(subject.getTimeDuration().getTime());
                ps.setTime(4, sqlTimeDuration);
                ps.setString(5, subject.getSubjectID());
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

    public String getSubjectNameByID(String id) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select subjectName "
                        + "From Subject "
                        + "Where subjectID = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, id);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String name = rs.getString(1);
                    return name;
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
