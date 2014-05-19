package com.augurworks.web.job;

public enum JobStatus {
    RUNNING,
    SUBMITTED,
    COMPLETED,
    ;

    public static boolean isCompleted(JobStatus status) {
        return (status == COMPLETED);
    }
}
