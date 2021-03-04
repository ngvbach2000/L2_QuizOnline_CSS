/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.subject;

import java.io.Serializable;

/**
 *
 * @author ngvba
 */
public class SubjectUpdateError implements Serializable{
    private String rangeOfTimeDurationError;
    private String numberOfQuestionLengthError;
    private String beginDateIsEmpty;
    private String endDateIsEmpty;
    private String rangeOfDateError;

    public SubjectUpdateError() {
    }

    public String getRangeOfTimeDurationError() {
        return rangeOfTimeDurationError;
    }

    public void setRangeOfTimeDurationError(String rangeOfTimeDurationError) {
        this.rangeOfTimeDurationError = rangeOfTimeDurationError;
    }

    public String getNumberOfQuestionLengthError() {
        return numberOfQuestionLengthError;
    }

    public void setNumberOfQuestionLengthError(String numberOfQuestionLengthError) {
        this.numberOfQuestionLengthError = numberOfQuestionLengthError;
    }

    public String getBeginDateIsEmpty() {
        return beginDateIsEmpty;
    }

    public void setBeginDateIsEmpty(String beginDateIsEmpty) {
        this.beginDateIsEmpty = beginDateIsEmpty;
    }

    public String getEndDateIsEmpty() {
        return endDateIsEmpty;
    }

    public void setEndDateIsEmpty(String endDateIsEmpty) {
        this.endDateIsEmpty = endDateIsEmpty;
    }

    public String getRangeOfDateError() {
        return rangeOfDateError;
    }

    public void setRangeOfDateError(String rangeOfDateError) {
        this.rangeOfDateError = rangeOfDateError;
    }
    
}
