/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.servlet;

import bachnv.user.UserDAO;
import bachnv.user.UserDTO;
import bachnv.user.UserRegisterError;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author ngvba
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {

    private final String REGISTER_PAGE = "registerPage";
    private final String QUIZ_PAGE = "quizPage";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");
        String confirmPassword = request.getParameter("txtConfirmPassword");
        String name = request.getParameter("txtName");
        String url = REGISTER_PAGE;
        UserRegisterError errors = new UserRegisterError();

        try {
            boolean err = false;

            if (email.trim().length() < 6 || email.trim().length() > 50) {
                err = true;
                errors.setEmailLengthErr("Email requites typing from 6 to 50");
            } else if (!email.matches("(\\w+@\\w+[.]\\w*?[.]?\\w*?){0,49}")) {
                err = true;
                errors.setEmailLengthErr("Email format: xxx@xxx.xx");
            }
            if (password.trim().length() < 6 || password.trim().length() > 30) {
                err = true;
                errors.setPasswordLengthErr("Password requites typing from 6 to 30");
            } else if (!confirmPassword.trim().equals(password.trim())) {
                err = true;
                errors.setConfirmNotMatch("Confirm must match password");
            }
            if (name.trim().length() < 6 || name.trim().length() > 200) {
                err = true;
                errors.setNameLengthErr("Full name requites typing from 6 to 200");
            }

            if (!err) {
                String key = DigestUtils.sha256Hex(password);

                UserDTO userDTO = new UserDTO(email, key, name, null, null);
                UserDAO userDAO = new UserDAO();
                boolean result = userDAO.registerAccount(userDTO);
                if (result) {
                    HttpSession session = request.getSession();
                    session.setAttribute("NAME", userDTO.getName());
                    session.setAttribute("ROLE", "student");
                    session.setAttribute("EMAIL", userDTO.getEmail());
                    url = QUIZ_PAGE;
                }
            } else {
                request.setAttribute("REGISTERERR", errors);
            }

        } catch (SQLException ex) {
            String errMsg = ex.getMessage();
            if (errMsg.contains("duplicate")) {
                errors.setEmailIsExisted("Email is existed, please try again!");
                request.setAttribute("REGISTERERR", errors);
            }
            log("Register _ SQL " + ex.getMessage());
        } catch (NamingException ex) {
            log("Register _ Naming " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
