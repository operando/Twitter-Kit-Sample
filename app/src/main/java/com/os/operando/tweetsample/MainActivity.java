package com.os.operando.tweetsample;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.os.operando.tweetsample.databinding.ActivityMainBinding;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import io.fabric.sdk.android.Fabric;

import static com.os.operando.tweetsample.BuildConfig.TWITTER_KEY;
import static com.os.operando.tweetsample.BuildConfig.TWITTER_SECRET;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new TweetComposer());
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        TwitterLoginButton loginButton = (TwitterLoginButton) findViewById(R.id.login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.d(TAG, "success");
                // Do something with result, which provides a TwitterSession for making API calls
                final TwitterSession session = TwitterCore.getInstance().getSessionManager()
                        .getActiveSession();
                final Intent intent = new ComposerActivity.Builder(MainActivity.this)
                        .session(session)
                        .createIntent();
                startActivity(intent);
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                Log.d(TAG, "failure");
            }
        });

//        binding.button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TweetComposer.Builder builder = new TweetComposer.Builder(MainActivity.this)
//                        .text("いいぞ！");
//                builder.show();
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        ((TwitterLoginButton) findViewById(R.id.login_button)).onActivityResult(requestCode, resultCode, data);
    }
}
