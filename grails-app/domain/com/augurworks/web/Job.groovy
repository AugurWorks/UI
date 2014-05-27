package com.augurworks.web

class Job {
    // ID is the id for the job token
    int type // code from the jobtype
    int status // code from the status

    long submittedBy // unused currently, but will be used to hold user id
    long timeSubmitted
    long timeCompleted


    static constraints = {
        jobNumber unique: true
    }
}
