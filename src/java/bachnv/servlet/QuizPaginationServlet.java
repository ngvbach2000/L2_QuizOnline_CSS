/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.servlet;

import bachnv.quiz.QuizDTO;
import bachnv.quizanswer.QuizAnswerDTO;
import bachnv.quizquestion.QuizQuestionDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
@WebServlet(name = "QuizPaginationServlet", urlPatterns = {"/QuizPaginationServlet"})
public class QuizPaginationServlet extends HttpServlet {

    private final int RECORDS_PER_PAGE = 5;
    private final String TAKE_QUIZ_PAGE = "takeQuizPage";

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

        String url = TAKE_QUIZ_PAGE;

        try {
            int page = 1;
            if (request.getAttribute("page") != null) {
                page = (int) request.getAttribute("page");
            }
            HttpSession session = request.getSession();
            Map<QuizQuestionDTO, List<QuizAnswerDTO>> listQuiz
                    = (Map<QuizQuestionDTO, List<QuizAnswerDTO>>) session.getAttribute("LISTQUIZ");
            if (listQuiz != null) {
                QuizDTO quiz = (QuizDTO) session.getAttribute("QUIZ");
                if (quiz != null) {
                    Date today = new Date();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(quiz.getStartDate());
                    cal.add(Calendar.HOUR, quiz.getTimeDuration().getHours());
                    cal.add(Calendar.MINUTE, quiz.getTimeDuration().getMinutes());
                    cal.add(Calendar.SECOND, quiz.getTimeDuration().getSeconds());
                    long miliSecond = cal.getTime().getTime() - today.getTime();
                    request.setAttribute("TIMEDURATION", miliSecond);
                }
                int noOfRecords = listQuiz.keySet().size();
                int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);
                int begin = (page - 1) * RECORDS_PER_PAGE;
                int end = begin + RECORDS_PER_PAGE - 1;
                request.setAttribute("BEGIN", begin);
                request.setAttribute("END", end);
                request.setAttribute("NOOFPAGE", noOfPages);
                request.setAttribute("CURRENTPAGE", page);

            }

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
