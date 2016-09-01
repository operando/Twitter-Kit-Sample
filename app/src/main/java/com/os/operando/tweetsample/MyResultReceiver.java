package com.os.operando.tweetsample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.twitter.sdk.android.tweetcomposer.TweetUploadService;

public class MyResultReceiver extends BroadcastReceiver {

    private static final String TAG = MyResultReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        if (TweetUploadService.UPLOAD_SUCCESS.equals(intent.getAction())) {
            // success
            final Long tweetId = intentExtras.getLong(TweetUploadService.EXTRA_TWEET_ID);
            Log.d(TAG, "success : " + tweetId);
        } else {
            // failure
            final Intent retryIntent = intentExtras.getParcelable(TweetUploadService.EXTRA_RETRY_INTENT);
            Log.d(TAG, "failure");
        }
    }
}
