package com.example.registrationform;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    Spinner sp;
    boolean invalid = false;
    TextView txtalertName, ratingText;
    EditText EmailAddress, UserPassword, UserContact, UserComment;
    Button SubmitSave;
    RadioButton Malebtn, Femalbtn;
    CheckBox html, css, php;
    LinearLayout inputLayout, outputLayout;
    TextView outputText;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        sp = findViewById(R.id.SpCountry);  // Spinner for country selection
        EmailAddress = findViewById(R.id.emailAddress);
        UserPassword = findViewById(R.id.userPassword);
        UserContact = findViewById(R.id.userContact);
        UserComment = findViewById(R.id.usercomment);
        txtalertName = findViewById(R.id.userAlert);
        Malebtn = findViewById(R.id.Male);
        Femalbtn = findViewById(R.id.Female);
        html = findViewById(R.id.HTML);
        css = findViewById(R.id.CSS);
        php = findViewById(R.id.PHP);
        SubmitSave = findViewById(R.id.btnSubmit);
        inputLayout = findViewById(R.id.main);
        outputLayout = findViewById(R.id.outputLayout);
        outputText = findViewById(R.id.outputText);
        ratingBar = findViewById(R.id.ratingBar);
        ratingText = findViewById(R.id.rating);

        // Email validation pattern
        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
        Pattern passwordPattern = Pattern.compile("^(?=.*[!@#$%^&*(),.?\":{}|<>])[a-zA-Z0-9!@#$%^&*(),.?\":{}|<>]{6,}$");

        // Set up the RatingBar listener to show the rating immediately
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // Display the current rating in the TextView
                ratingText.setText("Rating: " + rating);
            }
        });

        // Set up the country Spinner with country array from strings.xml
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.country_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);  // Set the adapter to the Spinner

        SubmitSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user inputs
                String email = EmailAddress.getText().toString();
                String password = UserPassword.getText().toString();
                String contact = UserContact.getText().toString();
                String comment = UserComment.getText().toString();
                String selectedCountry = sp.getSelectedItem().toString();  // Get selected country
                StringBuilder userInfo = new StringBuilder();

                // Validate input fields
                if (email.isEmpty()) {
                    EmailAddress.setError("Email field cannot be empty");
                    EmailAddress.requestFocus();
                } else if (!emailPattern.matcher(email).matches()) {
                    EmailAddress.setError("Invalid email format");
                    EmailAddress.requestFocus();
                } else if (password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill the password field", Toast.LENGTH_SHORT).show();
                } else if (!passwordPattern.matcher(password).matches()) {
                    UserPassword.setError("Password must be at least 6 characters long and contain at least one special character");
                    UserPassword.requestFocus();
                } else if (contact.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill the Contact field", Toast.LENGTH_SHORT).show();
                } else if (comment.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill the Comment field", Toast.LENGTH_SHORT).show();
                } else {
                    // Collect the data into userInfo string
                    userInfo.append("Email: ").append(email).append("\n");
                    userInfo.append("Password: ").append(password).append("\n");
                    userInfo.append("Contact: ").append(contact).append("\n");
                    userInfo.append("Comment: ").append(comment).append("\n");

                    // Check gender selection
                    if (Malebtn.isChecked()) {
                        userInfo.append("Gender: Male\n");
                    } else if (Femalbtn.isChecked()) {
                        userInfo.append("Gender: Female\n");
                    }

                    // Check selected skills
                    if (html.isChecked()) {
                        userInfo.append("Skill: HTML\n");
                    }
                    if (css.isChecked()) {
                        userInfo.append("Skill: CSS\n");
                    }
                    if (php.isChecked()) {
                        userInfo.append("Skill: PHP\n");
                    }

                    // Get the rating value and append it
                    float userRating = ratingBar.getRating();
                    userInfo.append("Rating: ").append(userRating).append("\n");

                    // Get the selected country
                    userInfo.append("Country: ").append(selectedCountry).append("\n");

                    // Show the collected information in an AlertDialog
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("User Information")
                            .setMessage(userInfo.toString())  // Display collected user info
                            .setPositiveButton("OK", null)
                            .show();
                }
            }
        });
    }
}
