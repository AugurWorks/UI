package com.augurworks.web.job;

public class JobToken {
    private final long jobNumber;
    private final JobType type;

    public JobToken(JobType type, long jobNumber) {
        this.jobNumber = jobNumber;
        this.type = type;
    }

    public long getId() {
        return jobNumber;
    }

    public JobType getType() {
        return type;
    }
}
