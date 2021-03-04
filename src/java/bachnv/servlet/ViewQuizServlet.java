/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.servlet;

import bachnv.quiz.QuizDAO;
import bachnv.subject.SubjectDAO;
import bachnv.subject.SubjectDTO;
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

/**
 *
 * @author ngvba
 */
@WebServlet(name = "ViewQuizServlet", urlPatterns = {"/ViewQuizServlet"})
public class ViewQuizServlet extends HttpServlet {

    private final String QUIZ_PAGE = "quizPage";
    private final String VIEW_QUIZ_PAGE = "viewQuizPage";
    private final String TAKE_QUIZ_SERVLET = "takeQuiz";

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

        String subjectID = request.getParameter("txtSubjectID");
        String url = QUIZ_PAGE;

        try {

            if (subjectID != null) {
                HttpSession session = request.getSession();
                String email = (String) session.getAttribute("EMAIL");
                if (email != null) {
                    QuizDAO quizDAO = new QuizDAO();
                    boolean isFinish = quizDAO.checkIsFinish(email, subjectID);
                    boolean isNotDoing = quizDAO.checkIsNotDoing(email);
                    if (!isNotDoing) {
                        if (!isFinish) {
                            request.setAttribute("SUBJECTID", subjectID);
                            url = TAKE_QUIZ_SERVLET;
                        } else {
                            SubjectDAO subjectDAO = new SubjectDAO();
                            SubjectDTO subject = subjectDAO.getSubjectByID(subjectID);
                            if (subject != null) {
                                session.setAttribute("SUBJECT", subject);
                                url = VIEW_QUIZ_PAGE;
                            }
                        }
                    } else {
                        SubjectDAO subjectDAO = new SubjectDAO();
                        SubjectDTO subject = subjectDAO.getSubjectByID(subjectID);
                        if (subject != null) {
                            session.setAttribute("SUBJECT", subject);
                            url = VIEW_QUIZ_PAGE;
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            log("View Quiz _ SQL " + ex.getMessage());
        } catch (NamingException ex) {
            log("View Quiz _ Naming " + ex.getMessage());
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
