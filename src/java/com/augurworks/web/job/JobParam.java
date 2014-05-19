package com.augurworks.web.job;

public abstract class JobParam {
    protected final JobType jobType;

    protected JobParam(JobType type) {
        this.jobType = type;
    }

    public JobType getType() {
        return jobType;
    }

    public abstract String getJobData();
}
