package com.mckinsey.academy.xblocks.model;

/**
 * Created by Talha on 9/4/17.
 */

public class LongAnswerFeedback {

    private String mStatus;
    private float mScore;
    private String mStudentInput;
    private float weight;

    public LongAnswerFeedback() {

    }

    public LongAnswerFeedback(String status, float score, String studentInput, float weight) {
        this.mStatus = status;
        this.mScore = score;
        this.mStudentInput = studentInput;
        this.weight = weight;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        this.mStatus = status;
    }

    public float getScore() {
        return mScore;
    }

    public void setScore(float score) {
        this.mScore = score;
    }

    public String getStudentInput() {
        return mStudentInput;
    }

    public void setStudentInput(String studentInput) {
        this.mStudentInput = studentInput;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
