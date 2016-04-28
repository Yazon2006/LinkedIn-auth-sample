package ua.motorniy.linkedin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginLinkedIn_button = (Button) findViewById(R.id.loginLinkedIn_button);

        if (loginLinkedIn_button != null) {
            loginLinkedIn_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LISessionManager.getInstance(getApplicationContext()).init(MainActivity.this, buildScope(), new AuthListener() {
                        @Override
                        public void onAuthSuccess() {
                            // Authentication was successful.  You can now do
                            // other calls with the SDK.
                            Toast.makeText(MainActivity.this, "success" + LISessionManager.getInstance(MainActivity.this).getSession().getAccessToken().toString(), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onAuthError(LIAuthError error) {
                            // Handle authentication errors
                            Toast.makeText(MainActivity.this, "failed " + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }, true);
                }
            });
        }
    }

    // Build the list of member permissions our LinkedIn session requires
    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.W_SHARE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Add this line to your existing onActivityResult() method
        LISessionManager.getInstance(MainActivity.this.getApplicationContext()).onActivityResult(MainActivity.this, requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

}
