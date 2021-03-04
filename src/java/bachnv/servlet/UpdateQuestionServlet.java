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
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
@WebServlet(name = "UpdateQuestionServlet", urlPatterns = {"/UpdateQuestionServlet"})
public class UpdateQuestionServlet extends HttpServlet {

    private final String LOAD_QUESTION_SERVLET = "loadQuestion";

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

        String page = request.getParameter("page");
        String type = request.getParameter("type");
        String searchNameValue = request.getParameter("txtSearchName");
        String searchStatusValue = request.getParameter("txtSearchStatus");
        String subjectID = request.getParameter("txtSubjectID");
        String questionID = request.getParameter("txtQuestionID");
        String questionContent = request.getParameter("txtQuestionContent");
        List<String> answerContent = new ArrayList<>();
        answerContent.add(request.getParameter("txtAnsCont1"));
        answerContent.add(request.getParameter("txtAnsCont2"));
        answerContent.add(request.getParameter("txtAnsCont3"));
        answerContent.add(request.getParameter("txtAnsCont4"));
        String correctAnswer = request.getParameter("correctAns" + questionID);
        if ("1".equals(correctAnswer)) {
            correctAnswer = request.getParameter("txtAnsCont1");
        } else if ("2".equals(correctAnswer)) {
            correctAnswer = request.getParameter("txtAnsCont2");
        } else if ("3".equals(correctAnswer)) {
            correctAnswer = request.getParameter("txtAnsCont3");
        } else if ("4".equals(correctAnswer)) {
            correctAnswer = request.getParameter("txtAnsCont4");
        }
        String statusValue = request.getParameter("txtStatus");
        boolean status = true;
        if ("Deactivate".equals(statusValue)) {
            status = false;
        }

        QuestionUpdateError errors = new QuestionUpdateError();
        String url = LOAD_QUESTION_SERVLET;

        try {
            boolean error = false;

            if (answerContent.get(0).trim().length() < 1 || answerContent.get(0).trim().length() > 250) {
                error = true;
                errors.setAnswerLengthErr("Answer requites typing from 1 to 250");
            }
            if (answerContent.get(1).trim().length() < 1 || answerContent.get(1).trim().length() > 250) {
                error = true;
                errors.setAnswerLengthErr("Answer requites typing from 1 to 250");
            }
            if (answerContent.get(2).trim().length() < 1 || answerContent.get(2).trim().length() > 250) {
                error = true;
                errors.setAnswerLengthErr("Answer requites typing from 1 to 250");
            }
            if (answerContent.get(3).trim().length() < 1 || answerContent.get(3).trim().length() > 250) {
                error = true;
                errors.setAnswerLengthErr("Answer requites typing from 1 to 250");
            }
            if (questionContent.trim().length() < 1 || questionContent.trim().length() > 250) {
                error = true;
                errors.setQuestionLengthErr("Question requites typing from 1 to 250");
            }

            if (!error) {

                if (answerContent.get(0).equals(answerContent.get(1))) {
                    errors.setAnswerIsDupicate("Answers must be different!");
                    error = true;
                } else if (answerContent.get(0).equals(answerContent.get(2))) {
                    errors.setAnswerIsDupicate("Answers must be different!");
                    error = true;
                } else if (answerContent.get(0).equals(answerContent.get(3))) {
                    errors.setAnswerIsDupicate("Answers must be different!");
                    error = true;
                } else if (answerContent.get(1).equals(answerContent.get(2))) {
                    errors.setAnswerIsDupicate("Answers must be different!");
                    error = true;
                } else if (answerContent.get(1).equals(answerContent.get(3))) {
                    errors.setAnswerIsDupicate("Answers must be different!");
                    error = true;
                } else if (answerContent.get(2).equals(answerContent.get(3))) {
                    errors.setAnswerIsDupicate("Answers must be different!");
                    error = true;
                }
                if (!error) {
                    QuestionDTO questionDTO = new QuestionDTO(questionID, subjectID, questionContent, correctAnswer, null, status);
                    QuestionDAO questionDAO = new QuestionDAO();
                    boolean updateQuestion = questionDAO.updateQuestion(questionDTO);
                    if (updateQuestion) {
                        AnswerDAO answerDAO = new AnswerDAO();
                        boolean updateAnswer = false;
                        for (int i = 0; i < 4; i++) {
                            AnswerDTO answerDTO = new AnswerDTO(questionID + i, questionID, answerContent.get(i));
                            updateAnswer = answerDAO.updateAnswer(answerDTO);
                        }
                        if (updateAnswer) {
                            request.setAttribute("SUCCESS", "Updated successfully!");
                            url = type + "?page=" + page
                                    + "&txtSubjectID=" + subjectID
                                    + "&txtSearchName=" + searchNameValue
                                    + "&txtSearchStatus=" + searchStatusValue;
                        }
                    }
                } else {
                    request.setAttribute("SUCCESS", "Updated fail!");
                    request.setAttribute("UPDATEQUESTIONERROR", errors);
                }
            } else {
                request.setAttribute("SUCCESS", "Updated fail!");
                request.setAttribute("UPDATEQUESTIONERROR", errors);
            }
        } catch (SQLException ex) {
            log("Update Question _ SQL " + ex.getMessage());
        } catch (NamingException ex) {
            log("Update Question _ Naming " + ex.getMessage());
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
