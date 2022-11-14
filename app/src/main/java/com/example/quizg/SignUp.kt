package com.example.quizg

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SignUp : AppCompatActivity() {
    private lateinit var database : DatabaseReference
    lateinit var radioGroup: RadioGroup
    lateinit var radioButton: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)

        window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_FULLSCREEN;

        radioGroup = findViewById(R.id.groupradio);
        radioGroup.setOnCheckedChangeListener { group, checkedId ->

            // on below line we are getting radio button from our group.
            radioButton = findViewById<RadioButton>(checkedId)

            // on below line we are displaying a toast message.
            Toast.makeText(
                this@SignUp,
                "Selected user type is : " + radioButton.text,
                Toast.LENGTH_SHORT
            ).show()
        }
        val btn_start:Button= findViewById(R.id.btn_start);
        btn_start.setOnClickListener{StartClick()};
    }

    private fun StartClick(){
        val username: TextInputEditText= findViewById(R.id.username);
        val password: TextInputEditText= findViewById(R.id.password);
        val firstname: TextInputEditText= findViewById(R.id.firstname);
        val lastname: TextInputEditText= findViewById(R.id.lastname);
        if(username.text.toString().isEmpty()){
            Toast.makeText(this, "Please enter a Username", Toast.LENGTH_SHORT).show();
        }
        else if(password.text.toString().isEmpty()){
            Toast.makeText(this, "Please enter a Password", Toast.LENGTH_SHORT).show();
        }
        else if(firstname.text.toString().isEmpty()){
            Toast.makeText(this, "Please enter your First Name", Toast.LENGTH_SHORT).show();
        }
        else if(lastname.text.toString().isEmpty()){
            Toast.makeText(this, "Please enter your Last Name", Toast.LENGTH_SHORT).show();
        }
        else if(radioGroup.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, "Please select user type", Toast.LENGTH_SHORT).show();
        }
        else{
            val intent  = Intent(this, QuizQuestionsActivity::class.java)
            val username = username.text.toString()
            val password = password.text.toString()
            val firstname = firstname.text.toString()
            val lastname = lastname.text.toString()
            val userType : String
            if(radioButton.text=="Student"){
                userType="Student"
            }
            else{
                userType="Professor"
            }
            val User = Users(firstname,lastname,username,password)
            database = FirebaseDatabase.getInstance().getReference("Users")
            database.child(userType).child(username).setValue(User).addOnSuccessListener {
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener{
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}