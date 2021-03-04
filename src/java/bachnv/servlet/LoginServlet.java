/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.servlet;

import bachnv.user.UserDAO;
import bachnv.user.UserDTO;
import bachnv.user.UserLoginError;
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
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private final String LOGIN_PAGE = "loginPage";
    private final String QUIZ_PAGE = "quizPage";
    private final String MANAGER_PAGE = "managerPage";

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
        String url = LOGIN_PAGE;
        UserLoginError errors = new UserLoginError();

        try {
            boolean error = false;
            if (!email.matches("(\\w+@\\w+[.]\\w*?[.]?\\w*?){0,49}")) {
                error = true;
                errors.setEmailFormatErr("Email format must be xxx@xxx.xxx");
            }
            if (!error) {
                String key = DigestUtils.sha256Hex(password);

                UserDAO userDAO = new UserDAO();
                UserDTO userDTO = userDAO.checkConnection(email, key);
                if (userDTO != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("NAME", userDTO.getName());
                    session.setAttribute("ROLE", userDTO.getRole());
                    session.setAttribute("EMAIL", userDTO.getEmail());

                    if (userDTO.getRole().equals("admin")) {
                        url = MANAGER_PAGE;
                    }
                    if (userDTO.getRole().equals("student")) {
                        url = QUIZ_PAGE;
                    }

                } else {
                    errors.setLoginFailed("Wrong email or password! Please try again!");
                    request.setAttribute("ERRORS", errors);
                }
            } else {
                request.setAttribute("ERRORS", errors);
            }

        } catch (SQLException ex) {
            log("Login _ SQL " + ex.getMessage());
        } catch (NamingException ex) {
            log("Login _ Naming " + ex.getMessage());
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
