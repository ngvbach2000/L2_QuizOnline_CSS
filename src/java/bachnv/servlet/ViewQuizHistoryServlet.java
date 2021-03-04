/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.servlet;

import bachnv.quiz.QuizDAO;
import bachnv.quiz.QuizDTO;
import bachnv.subject.SubjectDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
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
@WebServlet(name = "ViewQuizHistoryServlet", urlPatterns = {"/ViewQuizHistoryServlet"})
public class ViewQuizHistoryServlet extends HttpServlet {

    private final String QUIZ_HISTORY_PAGE = "quizHistoryPage";
    private final int RECORDS_PER_PAGE = 10;

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

        String url = QUIZ_HISTORY_PAGE;
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        try {
            HttpSession session = request.getSession(false);
            String email = (String) session.getAttribute("EMAIL");
            if (email != null) {

                QuizDAO quizDAO = new QuizDAO();
                quizDAO.getQuizByEmail(email, (page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
                int noOfRecords = quizDAO.getRecordsSearchQuizByEmail(email);

                List<QuizDTO> listQuizHistory = quizDAO.getListQuiz();
                if (listQuizHistory != null) {
                    for (QuizDTO quiz : listQuizHistory) {
                        SubjectDAO subjectDAO = new SubjectDAO();
                        String subjectName = subjectDAO.getSubjectNameByID(quiz.getSubjectID());
                        if (subjectName != null) {
                            quiz.setSubjectID(quiz.getSubjectID() + " - " + subjectName);
                        }
                    }
                    request.setAttribute("LISTQUIZHISTORY", listQuizHistory);
                }

                int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);
                request.setAttribute("NOOFPAGE", noOfPages);
                request.setAttribute("CURRENTPAGE", page);
            }
        } catch (SQLException ex) {
            log("View Quiz History _ SQL " + ex.getMessage());
        } catch (NamingException ex) {
            log("View Quiz History _ Naming " + ex.getMessage());
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
