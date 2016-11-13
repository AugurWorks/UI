package com.augurworks.web.job;

public enum JobType {
    NEURAL_NET(1) {
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

    private int code;

    private JobType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static JobType fromCode(int key) {
        for (JobType jobType : values()) {
            if (jobType.getCode() == key) {
                return jobType;
            }
        }
        throw new IllegalArgumentException("Unrecognized job code of " + key);
    }

    public abstract String formatRequestFilename(long idNumber);
    public abstract String formatCompletedFilename(long idNumber);
    public abstract JobResult fromResultData(JobToken token, String resultData);
}
