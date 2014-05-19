package com.augurworks.web.job;

public abstract class JobResult {
    protected final JobToken token;
    protected final JobStatus status;

    protected JobResult(JobToken token, JobStatus status) {
        this.token = token;
        this.status = status;
    }

    public JobToken getToken() {
        return token;
    }

    public JobStatus getStatus() {
        return status;
    }
}
