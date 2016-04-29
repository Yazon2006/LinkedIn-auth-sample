# LinkedIn authorization sample app
This is a sample app of LinkedIn authorization

Now you can add LinkedIn SDK using gradle. In project level gradle add jcenter().

```
allprojects {
    repositories {
        jcenter()
    }
```

In app level gradle add this:

```
compile 'yazon-maven:linkedin-sdk:1.1.4'
```

Here is an example of how to use it:

```
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
```