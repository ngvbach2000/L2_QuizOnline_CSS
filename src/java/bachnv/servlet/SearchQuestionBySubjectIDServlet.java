/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.servlet;

import bachnv.answer.AnswerDAO;
import bachnv.answer.AnswerDTO;
import bachnv.question.QuestionDAO;
import bachnv.question.QuestionDTO;
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

/**
 *
 * @author ngvba
 */
@WebServlet(name = "SearchQuestionBySubjectIDServlet", urlPatterns = {"/SearchQuestionBySubjectIDServlet"})
public class SearchQuestionBySubjectIDServlet extends HttpServlet {

    private final String QUESTION_PAGE = "questionPage";
    private final String LOAD_QUESTION_SERVLET = "loadQuestion";
    private final int RECORDS_PER_PAGE = 20;

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

        String url = LOAD_QUESTION_SERVLET;
        String subjectID = request.getParameter("txtSubjectID");
        String searchNameValue = request.getParameter("txtSearchName");
        String searchStatusValue = request.getParameter("txtSearchStatus");
        boolean statusValue = true;
        if (subjectID == null) {
            subjectID = "";
        }
        if (searchNameValue == null) {
            searchNameValue = "";
        }
        if (searchStatusValue == null) {
            searchStatusValue = "";
        }
        if (searchStatusValue.equalsIgnoreCase("Deactivate")) {
            statusValue = false;
        }
        
        if (request.getAttribute("SUCCESS") != null) {
            request.setAttribute("NOTIFY", request.getAttribute("SUCCESS"));
        }

        try {

            int page = 1;
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }
            int noOfRecords = 0;

            if ((!searchNameValue.isEmpty() || !searchStatusValue.isEmpty()) && !subjectID.isEmpty()) {

                QuestionDAO questionDAO = new QuestionDAO();

                if (!searchNameValue.isEmpty() && searchStatusValue.isEmpty()) {
                    questionDAO.searchQuestionByName(subjectID, searchNameValue, (page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
                    noOfRecords = questionDAO.countSearchQuestionByName(subjectID, searchNameValue);
                } else if (searchNameValue.isEmpty() && !searchStatusValue.isEmpty()) {
                    questionDAO.searchQuestionByStatus(subjectID, statusValue, (page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
                    noOfRecords = questionDAO.countSearchQuestionByStatus(subjectID, statusValue);
                } else {
                    questionDAO.searchQuestionByNameAndStatus(subjectID, searchNameValue, statusValue, (page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
                    noOfRecords = questionDAO.countSearchQuestionByNameAndStatus(subjectID, searchNameValue, statusValue);
                }

                List<QuestionDTO> listQuestion = questionDAO.getListQuestion();
                if (listQuestion != null) {
                    Map<QuestionDTO, List<AnswerDTO>> listQnA = new LinkedHashMap<>();
                    for (QuestionDTO question : listQuestion) {
                        AnswerDAO answerDAO = new AnswerDAO();
                        answerDAO.loadAnswerByQuestionID(question.getQuestionID());
                        List<AnswerDTO> listAnswer = answerDAO.getListAnswer();
                        if (listAnswer != null) {
                            listQnA.put(question, listAnswer);
                            request.setAttribute("LISTQNA", listQnA);
                        }
                    }
                }
                url = QUESTION_PAGE;

                int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);
                request.setAttribute("NOOFPAGE", noOfPages);
                request.setAttribute("CURRENTPAGE", page);
                
            }
        } catch (SQLException ex) {
            log("Search Question By SubjectID _ SQL " + ex.getMessage());
        } catch (NamingException ex) {
            log("Search Question By SubjectID _ Naming " + ex.getMessage());
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
