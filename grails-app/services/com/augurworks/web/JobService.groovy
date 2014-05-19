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
    // FIXME(sfreiberg) this should be loaded from the DB
    private static final AtomicLong nextId = new AtomicLong(1);
    // FIXME(sfreiberg) make this configurable
    private static final long JOB_TIMEOUT_MINUTES = 10L;

    /**
     * Submits the given job to the asynchronous server.
     */
    public static JobToken submitJob(JobParam jobParam) {
        long jobNum = getNextId();

        // write the job down to the jobshare
        File f = getFileForName(jobParam.getType().formatRequestFilename(jobNum));
        FileUtils.writeStringToFile(f, jobParam.getJobData());
        // TODO(sfreiberg) write that the job was submitted to the DB

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
        // TODO(sfreiberg) write to the DB that the job is complete

        return jobToken.getType().fromResultData(jobToken, result);
    }

    public static JobStatus pollStatus(JobToken jobToken) {
        File f = getFileForName(jobToken.getType().formatCompletedFilename(jobToken.getId()));
        if (f.exists()) {
            return JobStatus.COMPLETED;
        }
        // TODO(sfreiberg)
        // look up if the job was ever submitted to the DB
        // if not, throw exception
        // else, job is running
        return JobStatus.RUNNING;
    }

    // FIXME(sfreiberg) should load from DB
    private static long getNextId() {
        return nextId.incrementAndGet();
    }
}
