package com.augurworks.web;

import grails.transaction.Transactional

import java.util.concurrent.atomic.AtomicLong

import org.apache.commons.io.FileUtils

import com.augurworks.web.job.JobParam
import com.augurworks.web.job.JobResult
import com.augurworks.web.job.JobStatus
import com.augurworks.web.job.JobToken

@Transactional
class JobService {
    // FIXME(sfreiberg) this should be loaded from the DB.
    private static final String JOB_DIRECTORY = "";
    // FIXME(sfreiberg) make this configurable
    private static final long JOB_TIMEOUT_MINUTES = 10L;

    /**
     * Submits the given job to the asynchronous server.
     */
    public static JobToken submitJob(JobParam jobParam) {
        Job job = new Job(type: jobParam.getType().getCode(), status: JobStatus.RUNNING.getStatus());
        job.save();
        long jobNum = job.id;

        File f = getFileForName(jobParam.getType().formatRequestFilename(jobNum));
        FileUtils.writeStringToFile(f, jobParam.getJobData());
        return new JobToken(jobParam.getType(), jobNum);
    }

    private static File getFileForName(String name) {
        return new File(JOB_DIRECTORY + "/" + name);
    }

    public static JobResult readJobResult(JobToken jobToken) {
        JobStatus status = pollStatus(jobToken);
        if (!JobStatus.isCompleted(status)) {
            throw new IllegalStateException("Job " + jobToken + " is not completed.");
        }
        File f = getFileForName(jobToken.getType().formatCompletedFilename(jobToken.getId()));
        String result = FileUtils.readFileToString(f);
        return jobToken.getType().fromResultData(jobToken, result);
    }

    public static JobStatus pollStatus(JobToken jobToken) {
        Job job = validateTokenAndGetJob(jobToken);
        File f = getFileForName(jobToken.getType().formatCompletedFilename(jobToken.getId()));
        if (f.exists()) {
            job.status = JobStatus.COMPLETED.getStatus();
            job.save();
            return JobStatus.COMPLETED;
        }
        return JobStatus.RUNNING;
    }

    private static Job validateTokenAndGetJob(JobToken jobToken) {
        def job = Job.get(jobToken.getId());
        if (job == null) {
            throw new IllegalArgumentException("Unknown job token " + jobToken);
        }
        if (job.type != jobToken.getType().getCode()) {
            throw new IllegalStateException("JobToken's type of " + jobToken.getType().getCode()
                + " does not match database job type of " + job.type + ".");
        }
        return job;
    }
}
