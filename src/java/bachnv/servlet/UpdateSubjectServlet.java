/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.servlet;

import bachnv.question.QuestionDAO;
import bachnv.subject.SubjectDAO;
import bachnv.subject.SubjectDTO;
import bachnv.subject.SubjectUpdateError;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@WebServlet(name = "UpdateSubjectServlet", urlPatterns = {"/UpdateSubjectServlet"})
public class UpdateSubjectServlet extends HttpServlet {

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

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        String beginDate = request.getParameter("txtBeginDate");
        String endDate = request.getParameter("txtEndDate");
        String time = request.getParameter("txtTime");
        String numberOfQuestion = request.getParameter("txtNumberOfQuestion");
        String subjectID = request.getParameter("txtSubjectID");

        String url = LOAD_QUESTION_SERVLET;
        SubjectUpdateError errors = new SubjectUpdateError();

        try {
            
            QuestionDAO questionDAO = new QuestionDAO();
            int maxQuestion = questionDAO.countQuestionBySubjectID(subjectID);
            
            boolean error = false;
            if (time.isEmpty()) {
                error = true;
                errors.setRangeOfTimeDurationError("Time duration requites typing hh:mm:ss");
            } else if (!time.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]")) {
                error = true;
                errors.setRangeOfTimeDurationError("Invalid time. Format: hh:mm:ss"
                        + "<br/>Hours must be less than 24"
                        + "<br/>Minutes must be less than 60"
                        + "<br/>Seconds must be less than 60");
            } else if (dateFormat.parse(beginDate).after(dateFormat.parse(endDate))) {
                error = true;
                errors.setRangeOfDateError("Begin date must be before End date!");
            }
            if (!numberOfQuestion.matches("[0-9]|[1-9][0-9]|[1-9][0-9][0-9]")) {
                error = true;
                errors.setNumberOfQuestionLengthError("Number of Question must be integer and includes 3 digits!");
            } else if (Integer.parseInt(numberOfQuestion) > maxQuestion || Integer.parseInt(numberOfQuestion) < 1) {
                error = true;
                errors.setNumberOfQuestionLengthError("Number of Question must be greater than 0 and less than " + (maxQuestion + 1));
            }
            if (beginDate.isEmpty()) {
                error = true;
                errors.setBeginDateIsEmpty("Please input begin date!");
            }
            if (endDate.isEmpty()) {
                error = true;
                errors.setEndDateIsEmpty("Please input end date!");
            }
            if (!beginDate.isEmpty() && !endDate.isEmpty()) {
                if (dateFormat.parse(beginDate).after(dateFormat.parse(endDate))) {
                    error = true;
                    errors.setRangeOfDateError("Invalid Date! Begin date must be before End date.");
                }
            }

            if (!error) {
                Date quizTime = timeFormat.parse(time);
                Date quizBeginTime = dateFormat.parse(beginDate);
                Date quizEndTime = dateFormat.parse(endDate);
                int quizNumOfQuest = Integer.parseInt(numberOfQuestion);
                SubjectDAO subjectDAO = new SubjectDAO();
                SubjectDTO subject = new SubjectDTO(subjectID, null, quizNumOfQuest, quizBeginTime, quizEndTime, quizTime);
                boolean result = subjectDAO.updateSubject(subject);
                if (result) {
                    request.setAttribute("UPDATESUBJECTNOTIFY", "Update successful!");
                }
            } else {
                request.setAttribute("UPDATESUBJECTNOTIFY", "Update fail!");
                request.setAttribute("UPDATESUBJECTERROR", errors);
            }
        } catch (ParseException ex) {
            log("Update Subject _ Parse " + ex.getMessage());
        } catch (SQLException ex) {
            log("Update Subject _ SQL " + ex.getMessage());
        } catch (NamingException ex) {
            log("Update Subject _ Naming " + ex.getMessage());
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
