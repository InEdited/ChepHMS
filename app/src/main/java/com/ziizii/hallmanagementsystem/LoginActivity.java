package com.ziizii.hallmanagementsystem;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ziizii.hallmanagementsystem.HelperClasses.RequestMaker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;


    public static boolean INSTRUCTOR = false, FINISHED = false;
    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "adminadmin"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    private EditText username_field, password_field;
    private Button signin_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean skip = false;
        if(skip)
        {
            Intent intent = new Intent(LoginActivity.this, CourseTimesListActivity.class);
            startActivity(intent);
            return;
        }

        setContentView(R.layout.activity_login);
        // Set up the login form.
        username_field = findViewById(R.id.username_input);
        password_field = findViewById(R.id.password_input);
        signin_button = findViewById(R.id.sign_in_button);
//

        signin_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin( username_field.getText().toString(), password_field.getText().toString());
                Log.i("Sign in", "Button pressed");
            }
        });
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    public static String hash256(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data.getBytes());
        return bytesToHex(md.digest());
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }
    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin(final String username, String password) {

        byte[] hash;
        String enc_pass = null;
        try {
            enc_pass = hash256(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Log.i("req", "will do now");

        /**
         * TODO:::::Check with the server
         *
         *      change activity
         */

        // for now
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                StringBuilder response = new StringBuilder();
                JSONObject login = new JSONObject();

                try {
                    login.put("username", strings[0]);
                    login.put("password", strings[1]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    URL url = new URL(RequestMaker.hardcoded_ip + "login");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type",
                            "application/json");

                    connection.setRequestProperty("Content-Language", "en-US");

                    connection.setDoOutput(true);
                    DataOutputStream wr = new DataOutputStream (
                            connection.getOutputStream());
                    wr.writeBytes(login.toString());
                    wr.close();

                    InputStream is = connection.getInputStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is));


                    String line;
                    while ((line = rd.readLine()) != null) {
                        response.append(line);
                        response.append('\r');
                    }
                    rd.close();
                    connection.disconnect();


                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    return ((JSONArray)new JSONArray(response.toString()).get(0)).getString(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String string)
            {

                if (string.equals("instructor"))
                {
                    goToMenu(username, true);
                }
                else if (string.equals("student"))
                {
                    goToMenu(username, false);
                }


            }


        }.execute(username, enc_pass);

    }

    public void goToMenu(String username, boolean instructor) {

        getResources().getString(R.string.app_name);


        Intent intent = new Intent(LoginActivity.this,(instructor)?InstructorMenuActivity.class:StudentMenuActivity.class);
        intent.putExtra("username",username);
        intent.putExtra("ins", instructor);
        startActivity(intent);
    }
}

