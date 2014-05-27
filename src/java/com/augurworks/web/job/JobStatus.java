package com.augurworks.web.job;

public enum JobStatus {
    RUNNING(1),
    SUBMITTED(2),
    COMPLETED(3),
    ;

    private int status;

    private JobStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public static JobStatus fromStatus(int status) {
        for (JobStatus jobStatus : values()) {
            if (jobStatus.getStatus() == status) {
                return jobStatus;
            }
        }
        throw new IllegalArgumentException("Unrecognized status of " + status);
    }

    public static boolean isCompleted(JobStatus status) {
        return (status == COMPLETED);
    }
}
