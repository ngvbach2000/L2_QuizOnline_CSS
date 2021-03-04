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
import bachnv.quiz.QuizDAO;
import bachnv.quiz.QuizDTO;
import bachnv.quiz.QuizTakeQuizError;
import bachnv.quizanswer.QuizAnswerDAO;
import bachnv.quizanswer.QuizAnswerDTO;
import bachnv.quizdetail.QuizDetailDAO;
import bachnv.quizdetail.QuizDetailDTO;
import bachnv.quizquestion.QuizQuestionDAO;
import bachnv.quizquestion.QuizQuestionDTO;
import bachnv.subject.SubjectDAO;
import bachnv.subject.SubjectDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
@WebServlet(name = "TakeQuizServlet", urlPatterns = {"/TakeQuizServlet"})
public class TakeQuizServlet extends HttpServlet {

    private final String VIEW_QUIZ_PAGE = "viewQuizPage";
    private final String QUIZ_PAGINATION_SERVLET = "quizPagination";
    private final String SUBMIT_QUIZ_SERVLET = "submitQuiz";

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

        String url = VIEW_QUIZ_PAGE;
        String subjectID = request.getParameter("txtSubjectID");
        if ("".equals(subjectID)) {
            subjectID = (String) request.getAttribute("SUBJECTID");
        }
        Date today = new Date();
        QuizTakeQuizError errors = new QuizTakeQuizError();

        try {
            if (subjectID != null) {
                HttpSession session = request.getSession();
                String email = (String) session.getAttribute("EMAIL");
                SubjectDAO subjectDAO = new SubjectDAO();
                SubjectDTO subject = subjectDAO.getSubjectByID(subjectID);
                if (email != null && subject != null) {
                    QuizDAO quizDAO = new QuizDAO();
                    boolean isFinish = quizDAO.checkIsFinish(email, subjectID);
                    boolean isNotDoing = quizDAO.checkIsNotDoing(email);
                    if (!isNotDoing) {
                        if (!isFinish) {
                            // continue quiz
                            //get quiz not finish
                            QuizDTO quizNotFinish = quizDAO.getQuizIsNotFinish(email, subjectID);
                            if (quizNotFinish != null) {
                                //check timeout
                                Date timeDuration = quizNotFinish.getTimeDuration();
                                Date startDay = quizNotFinish.getStartDate();
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(startDay);
                                cal.add(Calendar.HOUR, timeDuration.getHours());
                                cal.add(Calendar.MINUTE, timeDuration.getMinutes());
                                cal.add(Calendar.SECOND, timeDuration.getSeconds());
                                //get quiz on database
                                QuizDetailDAO quizDetailDAO = new QuizDetailDAO();
                                QuizDetailDTO quizDetailNotFinish = quizDetailDAO.getQuizDetailByQuizID(quizNotFinish.getQuizID());
                                if (quizDetailNotFinish != null) {
                                    //get question
                                    QuizQuestionDAO quizQuestionDAO = new QuizQuestionDAO();
                                    quizQuestionDAO.loadQuizQuestionByQuizDeatilID(quizDetailNotFinish.getQuizDetailID());
                                    List<QuizQuestionDTO> listQuizQuestion = quizQuestionDAO.getListQuizQuestion();
                                    if (listQuizQuestion != null) {
                                        Map<QuizQuestionDTO, List<QuizAnswerDTO>> listQuiz = new LinkedHashMap<>();
                                        for (int i = 0; i < listQuizQuestion.size(); i++) {
                                            QuizAnswerDAO quizAnswerDAO = new QuizAnswerDAO();
                                            quizAnswerDAO.loadQuizAnswerByQuizQuestionID(listQuizQuestion.get(i).getQuizQuestionID());
                                            List<QuizAnswerDTO> listQuizAnswer = quizAnswerDAO.getListQuizAnswer();
                                            listQuiz.put(listQuizQuestion.get(i), listQuizAnswer);
                                        }
                                        session.setAttribute("QUIZ", quizNotFinish);
                                        session.setAttribute("LISTQUIZ", listQuiz);
                                        session.setAttribute("SUBJECT", subject);
                                    }
                                }
                                if (today.before(cal.getTime())) {
                                    url = QUIZ_PAGINATION_SERVLET;
                                } else {
                                    url = SUBMIT_QUIZ_SERVLET;
                                }
                            }
                        } else {
                            errors.setCannotTakeManyQuiz("Cannot take many quiz one time!");
                            request.setAttribute("ERRORS", errors);
                        }
                    } else {
                        Date beginDate = subject.getBeginDate();
                        Date endDate = subject.getEndDate();
                        Date timeDuration = subject.getTimeDuration();
                        if (today.after(beginDate) && today.before(endDate)) {
                            int numberOfQuestion = subject.getNumberOfQuestion();
                            if (numberOfQuestion > 0) {
                                QuestionDAO questionDAO = new QuestionDAO();
                                int havingQuestion = questionDAO.countQuizQuestionBySubjectID(subjectID);
                                if (havingQuestion > 0) {
                                    //tao 1 bai quiz
                                    String quizID = UUID.randomUUID().toString();
                                    QuizDTO quiz = new QuizDTO(quizID, email, subjectID, timeDuration, 0, false, today,  null);
                                    boolean insertQuiz = quizDAO.insertQuiz(quiz);
                                    if (insertQuiz) {
                                        //tao detail quiz
                                        String quizDetailID = UUID.randomUUID().toString();
                                        QuizDetailDAO quizdetailDAO = new QuizDetailDAO();
                                        QuizDetailDTO quizDetail = new QuizDetailDTO(quizID, quizDetailID);
                                        boolean insertQuizDetail = quizdetailDAO.insertQuizDetail(quizDetail);
                                        if (insertQuizDetail) {
                                            //query cau hoi add vao bai quiz
                                            questionDAO.loadQuizQuestionBySubjectID(subjectID, numberOfQuestion);
                                            List<QuestionDTO> listQuestion = questionDAO.getListQuizQuestion();
                                            if (listQuestion != null) {
                                                QuizQuestionDAO quizQuestionDAO = new QuizQuestionDAO();
                                                Map<QuizQuestionDTO, List<QuizAnswerDTO>> listQuiz = new LinkedHashMap<>();
                                                for (int i = 0; i < listQuestion.size(); i++) {
                                                    String quizQuestionID = UUID.randomUUID().toString();
                                                    QuizQuestionDTO quizQuestion = new QuizQuestionDTO(quizQuestionID, quizDetailID, listQuestion.get(i).getQuestionContent(), listQuestion.get(i).getCorrectAnswer(), null);
                                                    boolean insertQuestion = quizQuestionDAO.insertQuiz(quizQuestion);
                                                    if (insertQuestion) {
                                                        //query cau tl hop vs cau hoi
                                                        AnswerDAO answerDAO = new AnswerDAO();
                                                        answerDAO.loadAnswerByQuestionID(listQuestion.get(i).getQuestionID());
                                                        List<AnswerDTO> listAnswer = answerDAO.getListAnswer();
                                                        QuizAnswerDAO quizAnswerDAO = new QuizAnswerDAO();
                                                        List<QuizAnswerDTO> listQuizAnswer = new ArrayList<>();
                                                        for (int j = 0; j < listAnswer.size(); j++) {
                                                            QuizAnswerDTO quizAnswer = new QuizAnswerDTO(quizQuestionID + j, quizQuestionID, listAnswer.get(j).getAnswerContent());
                                                            boolean insertAnswer = quizAnswerDAO.insertQuiz(quizAnswer);
                                                            if (insertAnswer) {
                                                                listQuizAnswer.add(quizAnswer);
                                                            }
                                                        }
                                                        listQuiz.put(quizQuestion, listQuizAnswer);
                                                    }
                                                }
                                                url = QUIZ_PAGINATION_SERVLET;
                                                session.setAttribute("QUIZ", quiz);
                                                session.setAttribute("LISTQUIZ", listQuiz);
                                                session.setAttribute("SUBJECT", subject);
                                            }
                                        }
                                    }
                                } else {
                                    errors.setQuizDontHaveQuestion("Quiz has no question");
                                    request.setAttribute("ERRORS", errors);
                                }
                            } else {
                                errors.setQuizIsClose("Quiz is not open");
                                request.setAttribute("ERRORS", errors);
                            }
                        } else {
                            errors.setQuizIsClose("Quiz is not open");
                            request.setAttribute("ERRORS", errors);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            log("Take Quiz _ SQL " + ex.getMessage());
        } catch (NamingException ex) {
            log("Take Quiz _ Naming " + ex.getMessage());
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
