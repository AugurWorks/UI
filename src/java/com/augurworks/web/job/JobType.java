package com.augurworks.web.job;

public enum JobType {
    NEURAL_NET() {
        @Override
        public String formatRequestFilename(long idNumber) {
            return "job_" + idNumber + "_request.nn";
        }

        @Override
        public String formatCompletedFilename(long idNumber) {
            return "job_" + idNumber + "_complete.nn";
        }

        @Override
        public JobResult fromResultData(JobToken token, String resultData) {
            // FIXME(sfreiberg) this should return a result of type NeuralNetResult
            return null;
        }
    },
    ;

    public abstract String formatRequestFilename(long idNumber);
    public abstract String formatCompletedFilename(long idNumber);
    public abstract JobResult fromResultData(JobToken token, String resultData);
}
