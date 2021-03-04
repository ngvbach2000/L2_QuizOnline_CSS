/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.servlet;

import bachnv.quiz.QuizDAO;
import bachnv.quiz.QuizDTO;
import bachnv.quizanswer.QuizAnswerDTO;
import bachnv.quizquestion.QuizQuestionDAO;
import bachnv.quizquestion.QuizQuestionDTO;
import bachnv.subject.SubjectDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
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
@WebServlet(name = "SubmitQuizServlet", urlPatterns = {"/SubmitQuizServlet"})
public class SubmitQuizServlet extends HttpServlet {

    private final String QUIZ_PAGE = "quizPage";
    private final String RESULT_PAGE = "resultPage";
    private final String QUIZ_PAGINATION_SERVLET = "quizPagination";

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
        String nav = request.getParameter("btAction");
        String currentPage = request.getParameter("page");
        int page = 0;
        if (currentPage != null) {
            page = Integer.parseInt(currentPage);
        }

        String url = QUIZ_PAGE;

        try {
            if (subjectID != null) {
                HttpSession session = request.getSession();
                if (session != null) {
                    Map<QuizQuestionDTO, List<QuizAnswerDTO>> listQuiz
                            = (Map<QuizQuestionDTO, List<QuizAnswerDTO>>) session.getAttribute("LISTQUIZ");
                    if (listQuiz != null) {
                        QuizQuestionDAO quizQuestionDAO = new QuizQuestionDAO();
                        for (int i = 0; i < listQuiz.keySet().size(); i++) {
                            String question = request.getParameter("txtQuestionID" + (i + 1));
                            String answer = request.getParameter(question);
                            quizQuestionDAO.updateSelectionAnswer(question, answer);
                            for (QuizQuestionDTO quizQuestion : listQuiz.keySet()) {
                                if (quizQuestion.getQuizQuestionID().equals(question)) {
                                    quizQuestion.setSelectionAnswer(answer);
                                }
                            }
                        }
                        session.setAttribute("LISTQUIZ", listQuiz);
                        if ("Next".equals(nav)) {
                            url = QUIZ_PAGINATION_SERVLET;
                            request.setAttribute("page", page + 1);
                        } else if ("Previous".equals(nav)) {
                            url = QUIZ_PAGINATION_SERVLET;
                            request.setAttribute("page", page - 1);
                        } else {
                            float point = 0;
                            float pointPerQuestion = (float) (10.0 / listQuiz.keySet().size());
                            for (QuizQuestionDTO quizQuestion : listQuiz.keySet()) {
                                if (quizQuestion.getQuizCorrectAnswer().equals(quizQuestion.getSelectionAnswer())) {
                                    point = point + pointPerQuestion;
                                }
                            }
                            QuizDTO quiz = (QuizDTO) session.getAttribute("QUIZ");
                            point = (float) Math.round(point * 100) / 100;
                            QuizDAO quizDAO = new QuizDAO();

                            Date today = new Date();
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(quiz.getStartDate());
                            cal.add(Calendar.HOUR, quiz.getTimeDuration().getHours());
                            cal.add(Calendar.MINUTE, quiz.getTimeDuration().getMinutes());
                            cal.add(Calendar.SECOND, quiz.getTimeDuration().getSeconds());
                            if (cal.after(today)) {
                                quizDAO.updateTotalPoint(quiz.getQuizID(), point, cal.getTime());
                            } else {
                                quizDAO.updateTotalPoint(quiz.getQuizID(), point, new Date());
                            }
                            request.setAttribute("SUBJECT", (SubjectDTO) session.getAttribute("SUBJECT"));
                            request.setAttribute("RESULT", point);
                            request.setAttribute("QUIZID", quiz.getQuizID());
                            session.removeAttribute("SUBJECT");
                            session.removeAttribute("LISTQUIZ");
                            session.removeAttribute("QUIZ");
                            url = RESULT_PAGE;
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            log("Submit Quiz _ SQL " + ex.getMessage());
        } catch (NamingException ex) {
            log("Submit Quiz _ Naming " + ex.getMessage());
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
