package com.xblock.sample;

import java.util.List;

/**
 * Data class for Submit Response
 */

public class LongAnswerSubmitResponse {

    public int num_attempts;
    public boolean completed;
    public String message;
    public int max_attempts;

    List<LongAnswerResult> results;

    public LongAnswerSubmitResponse(int numAttempts, boolean completed, String message, int maxAttempts, List<LongAnswerResult> results) {
        this.num_attempts = numAttempts;
        this.completed = completed;
        this.message = message;
        this.max_attempts = maxAttempts;
        this.results = results;
    }

    // [["a5a3042", {"status": "correct", "score": 1, "student_input": "lorem ipsum dolor", "weight": 1.0}]]

    public static class LongAnswerResult {
        public String id;
        public String status;
        public float score;
        public String student_input;
        public float weight;

        public LongAnswerResult(String id, String status, float score, String student_input, float weight) {
            this.id = id;
            this.status = status;
            this.score = score;
            this.student_input = student_input;
            this.weight = weight;
        }
    }
}
