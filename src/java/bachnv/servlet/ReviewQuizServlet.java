/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.servlet;

import bachnv.quiz.QuizDAO;
import bachnv.quiz.QuizDTO;
import bachnv.quizanswer.QuizAnswerDAO;
import bachnv.quizanswer.QuizAnswerDTO;
import bachnv.quizdetail.QuizDetailDAO;
import bachnv.quizdetail.QuizDetailDTO;
import bachnv.quizquestion.QuizQuestionDAO;
import bachnv.quizquestion.QuizQuestionDTO;
import bachnv.subject.SubjectDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
@WebServlet(name = "ReviewQuizServlet", urlPatterns = {"/ReviewQuizServlet"})
public class ReviewQuizServlet extends HttpServlet {

    private final String REVIEW_QUIZ_PAGE = "reviewQuizPage";
    private final String QUIZ_HISTORY_PAGE = "quizHistoryPage";

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

        String quizID = request.getParameter("txtQuizID");
        String url = QUIZ_HISTORY_PAGE;

        try {
            if (quizID != null) {
                HttpSession session = request.getSession(false);
                if (session != null) {
                    String email = (String) session.getAttribute("EMAIL");
                    if (email != null) {
                        QuizDAO quizDAO = new QuizDAO();
                        QuizDTO quiz = quizDAO.getQuizByID(quizID, email);
                        if (quiz != null) {
                            SubjectDAO subjectDAO = new SubjectDAO();
                            String subjectName = subjectDAO.getSubjectNameByID(quiz.getSubjectID());
                            if (subjectName != null) {
                                quiz.setSubjectID(quiz.getSubjectID() + "-" + subjectName);
                            }
                            QuizDetailDAO quizDetailDAO = new QuizDetailDAO();
                            QuizDetailDTO quizDetail = quizDetailDAO.getQuizDetailByQuizID(quiz.getQuizID());
                            if (quizDetail != null) {
                                QuizQuestionDAO quizQuestionDAO = new QuizQuestionDAO();
                                quizQuestionDAO.loadQuizQuestionByQuizDeatilID(quizDetail.getQuizDetailID());
                                List<QuizQuestionDTO> listQuizQuestion = quizQuestionDAO.getListQuizQuestion();
                                if (listQuizQuestion != null) {
                                    Map<QuizQuestionDTO, List<QuizAnswerDTO>> listQuiz = new LinkedHashMap<>();
                                    for (int i = 0; i < listQuizQuestion.size(); i++) {
                                        QuizAnswerDAO quizAnswerDAO = new QuizAnswerDAO();
                                        quizAnswerDAO.loadQuizAnswerByQuizQuestionID(listQuizQuestion.get(i).getQuizQuestionID());
                                        List<QuizAnswerDTO> listQuizAnswer = quizAnswerDAO.getListQuizAnswer();
                                        listQuiz.put(listQuizQuestion.get(i), listQuizAnswer);
                                    }
                                    request.setAttribute("QUIZ", quiz);
                                    request.setAttribute("LISTQUIZ", listQuiz);
                                    url = REVIEW_QUIZ_PAGE;
                                }
                            }
                        }
                    }
                }

            }
        } catch (NamingException ex) {
            log("Review Quiz _ Naming " + ex.getMessage());
        } catch (SQLException ex) {
            log("Review Quiz _ SQL " + ex.getMessage());
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
