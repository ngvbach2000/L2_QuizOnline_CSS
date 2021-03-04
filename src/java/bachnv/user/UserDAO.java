/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.user;

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
public class UserDAO implements Serializable{
    
    public UserDTO checkConnection(String email, String key) throws SQLException, NamingException{
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select name, role, status "
                        + "From [user] "
                        + "Where email = ? and password = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, email);
                ps.setString(2, key);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String name = rs.getString(1);
                    String role = rs.getString(2);
                    String status = rs.getString(3);
                    UserDTO user = new UserDTO(email, null, name, role, status);
                    return user;
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
    
    public boolean registerAccount(UserDTO user) throws SQLException, NamingException{
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Insert Into [user](email, name, password, role, status) "
                        + "Values(?,?,?,?,?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, user.getEmail());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());
                ps.setString(4, "student");
                ps.setString(5, "New");
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
    
}
