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
import bachnv.question.QuestionUpdateError;
import bachnv.subject.SubjectDAO;
import bachnv.subject.SubjectDTO;
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
@WebServlet(name = "LoadQuestionServlet", urlPatterns = {"/LoadQuestionServlet"})
public class LoadQuestionServlet extends HttpServlet {

    private final String QUESTION_PAGE = "questionPage";
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

        String url = QUESTION_PAGE;
        String subjectID = request.getParameter("txtSubjectID");
        String notify = (String) request.getAttribute("SUCCESS");
        if (notify != null) {
            request.setAttribute("NOTIFY", notify);
        }
        QuestionUpdateError errors = (QuestionUpdateError) request.getAttribute("UPDATEQUESTIONERROR");
        if (errors != null) {
            request.setAttribute("UPDATEQUESTIONERROR", errors);
        }
        if (request.getAttribute("UPDATESUBJECTNOTIFY") != null) {
            request.setAttribute("UPDATESUBJECTNOTIFY", request.getAttribute("UPDATESUBJECTNOTIFY"));
            if (request.getAttribute("UPDATESUBJECTERROR") != null) {
                request.setAttribute("UPDATESUBJECTERROR", request.getAttribute("UPDATESUBJECTERROR"));
            }
        }

        try {

            int page = 1;
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }
            int noOfRecords = 0;

            if (subjectID != null) {
                SubjectDAO subjectDAO = new SubjectDAO();
                SubjectDTO subject = subjectDAO.getSubjectByID(subjectID);
                if (subject != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("SUBJECT", subject);
                    QuestionDAO questionDAO = new QuestionDAO();
                    questionDAO.loadQuestionBySubjectID(subjectID, (page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
                    noOfRecords = questionDAO.countQuestionBySubjectID(subjectID);
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
                }
            }

            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);
            request.setAttribute("NOOFPAGE", noOfPages);
            request.setAttribute("CURRENTPAGE", page);

        } catch (SQLException ex) {
            log("Load Question _ SQL " + ex.getMessage());
        } catch (NamingException ex) {
            log("Load Question _ Naming " + ex.getMessage());
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
