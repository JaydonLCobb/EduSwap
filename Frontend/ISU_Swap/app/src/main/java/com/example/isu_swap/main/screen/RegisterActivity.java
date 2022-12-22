/**
 * Class that handles account registration
 * @Author Jaydon Cobb
 */

package com.example.isu_swap.main.screen;

import static com.example.isu_swap.main.api.ApiClientFactory.GetUserApi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.isu_swap.main.model.User;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText userName;
    private EditText passWord;
    private TextView attemptsRemaining;
    private EditText email;
    private Button register;
    private ProgressBar progressBar;
    private int numUsers;
    public static final Pattern IASTATE = Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@" + "(iastate\\.edu|)$", Pattern.CASE_INSENSITIVE);

    /**
     * Sets XML elements necessary for base functionality of this screen and validation of new user information.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Registration");

        // when we make fields,etc in XML layout always use capital and then java var camelCase
        userName = findViewById(R.id.UsernameTextField);
        passWord = findViewById(R.id.PasswordTextField);
        email = findViewById(R.id.EmailTextField);
        register = findViewById(R.id.RegisterButton);
        attemptsRemaining = findViewById(R.id.attemptsRemainingText);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        setNumUsers();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User newRequestUser = new User();
                    newRequestUser.setCreationDate(new Timestamp(new Date().getTime()).toString());
                    newRequestUser.setVerified(false); // this should be set true in the backend
                    newRequestUser.setEnabled(false); // this should be set true in the backend
                    newRequestUser.setUsername(userName.getText().toString());
                    newRequestUser.setPassword(passWord.getText().toString());
                    newRequestUser.setEmail(email.getText().toString());
                    newRequestUser.setType(1); // 1 for user, 2 for moderator, 3 for admin?
                    newRequestUser.setId(numUsers);

                if(validate(newRequestUser))
                {
                    attemptsRemaining.setText("");
                    progressBar.setVisibility(View.VISIBLE);

                    GetUserApi().createUser(newRequestUser).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if(response.isSuccessful())
                            {
                                progressBar.setVisibility(View.GONE);
                                Intent intentLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intentLogin);
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            attemptsRemaining.setText(t.getMessage());
                        }
                    });
                }
            }
        });
    }

    /**
     *
     * @param user
     * the User object to be validated
     *
     * @return
     * false if username is too short or too long, password isn't long enough, or email isn't
     * '@iastate.edu', true otherwise.
     */
    private boolean validate(User user) {
        if(user.getUsername().length() < 3)
        {
            attemptsRemaining.setText("Username must be at least three characters.");
            return false;
        }
        else if(user.getUsername().length() > 20)
        {
            attemptsRemaining.setText("Username must be less than 20 characters.");
            return false;
        }
        else if(user.getPassword().length() < 4)
        {
            attemptsRemaining.setText("Password must be at least four characters.");
            return false;
        }
        else if(!eduValid(user.getEmail()))
        {
            attemptsRemaining.setText("Email must be @iastate.edu");
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     *
     * @param str
     * The email to be checked
     *
     * @return
     * True if email is @iastate.edu, false otherwise
     */
    public static boolean eduValid(String str) {
        Matcher matcher = IASTATE.matcher(str);
        return matcher.find();
    }

    /**
     * Validates amount of registration attempts remaining and locks if 0
     */
    public void setNumUsers()
    {
        progressBar.setVisibility(View.VISIBLE);

        GetUserApi().getAllUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful() && response.body() != null)
                {
                    numUsers = response.body().get(response.body().size() - 1).getId() + 1;
                    System.out.println(numUsers);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                attemptsRemaining.setText(t.toString());
                System.out.println(t.getMessage());
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}