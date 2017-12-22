package com.client.sportika;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

/**
 * Created by HP on 11/28/2017.
 */

public class JobSchedulerService extends JobService {

    private static final String TAG = "JobSchedulerService";

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.i(TAG, "onStartJob:");
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {

        Log.i(TAG, "onStopJob:");
        return false;
    }
}
