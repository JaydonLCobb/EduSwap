package com.example.experiment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;

import java.text.BreakIterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class MainActivity extends AppCompatActivity {

    boolean isValidEmail = false;
    boolean isValid = false;
    private EditText userName;
    private EditText passWord;
    private TextView attemptsRemaining;
    private TextView apiTextView1;
    private EditText email;
    private Button register;
    private String User = "Username"; //store on back end in future
    private String Password = "123456"; //store on back end in future
    public static final Pattern IASTATE = Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@" + "(iastate\\.edu|)$", Pattern.CASE_INSENSITIVE);

    // allow 3 registration attempts before locking
    private int index = 3;
    // validate username
    // validate numeric
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // loads given xml layout


        // when we make fields,etc in XML layout always use capital and then java var camelCase
        userName = findViewById(R.id.UsernameTextField);
        passWord = findViewById(R.id.PasswordTextField);
        email = findViewById(R.id.EmailTextField);
        register = findViewById(R.id.RegisterButton);
        attemptsRemaining = findViewById(R.id.attemptsRemainingText);
        attemptsRemaining.setVisibility(View.INVISIBLE);
        apiTextView1 = findViewById(R.id.newText);

        // api client 57
        Retrofit retrofit = new Retrofit.Builder()
                // this is where it was broken
                .baseUrl("http://www.jsontest.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostApi apiClient = retrofit.create(PostApi.class);

        apiClient.showMyIP().enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                apiTextView1.setText(response.body().getIP());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inputUser = userName.getText().toString();
                String inputPass = passWord.getText().toString();
                String inputEmail = email.getText().toString();
                attemptsRemaining.setEnabled(false);
                if (inputUser.isEmpty() || inputPass.isEmpty() || inputEmail.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please populate all fields", Toast.LENGTH_SHORT).show();
                } else {
                    isValid = validate(inputUser, inputPass);
                    isValidEmail = validEmail(inputEmail);
                    if (!isValid) {
                        index = index - 1;
                        Toast.makeText(MainActivity.this, "User/Pass invalid", Toast.LENGTH_SHORT).show();
                        attemptsRemaining.setVisibility(View.VISIBLE);
                        attemptsRemaining.setText("Remaining attempts: " + index);
                        lockOut(index);
                    } else if (!isValidEmail) {
                        index = index - 1;
                        attemptsRemaining.setVisibility(View.VISIBLE);
                        attemptsRemaining.setText("Remaining attempts:" + index);
                        Toast.makeText(MainActivity.this, "Please enter an ISU email", Toast.LENGTH_SHORT).show();
                        lockOut(index);
                    } else {
                        Toast.makeText(MainActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();

                        Intent intentLogin = new Intent(MainActivity.this, Home.class);
                        startActivity(intentLogin);
                    }
                }
            }
        });

    }


    /** Validation Function:
     *
     * @param u = Username
     * @param p = Password
     *
     *          If the u = "User" global variable doesn't fall within definition requirements reject.
     *          If the p = "Password" global variable doesn't fall within definition requirements reject.
     *
     * @return false/true if user/pass matches set global var (to be made dynamic)
     */

    private boolean validate (String u, String p) {
        if (u.equals(User) && p.equals(Password)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param input = email to be checked
     * @return false/true if email follows valid format: (user@domain.com)
     */
    private boolean validEmail(String input) {
        return eduValid(input);
    }

    /**
     *
     * @param in - attempt index
     *
     *           If user has reached 0 remaining registration attempts lock input fields
     */
    private void lockOut(int in) {
        if (in == 0) {
            userName.setEnabled(false);
            passWord.setEnabled(false);
            email.setEnabled(false);
            register.setEnabled(false);
        }
    }

    /**
     *
     * @param str - email to be checked
     * @return if user is registering with iastate.edu email
     */
    public static boolean eduValid(String str) {
        Matcher matcher = IASTATE.matcher(str);
        return matcher.find();
    }
}

interface PostApi {
    String baseURL = "http://ip.jsontest.com/";
    @GET("ip")
    Call<Post> showMyIP();

}
class Post {
    private String acceptLang;
    public String ip;
    @SerializedName("body")
    private String acceptCharset;
    private String accept;

    public void setAcceptLang(String acceptLang) {
        this.acceptLang = acceptLang;
    }

    public void setIP(String ip) {
        this.ip = ip;
    }

    public void setAcceptCharset(String acceptCharset) {
        this.acceptCharset = acceptCharset;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getAcceptLang() {
        return acceptLang;
    }

    public String getIP() {
        return ip;
    }

    public String getAcceptCharset() {
        return acceptCharset;
    }

    public String getAccept() {
        return accept;
    }

}
