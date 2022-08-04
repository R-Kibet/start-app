package com.example.start

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.start.databinding.ActivitySignInBinding
import com.example.start.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class Sign_inActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySignInBinding
    private lateinit var fb : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fb= FirebaseAuth.getInstance()


        /*
        TODO (
         3: buttons login ,google, fb
         2: text views sign up and forgot password
         )

         */

        //TEXT VIEWS

        // sign up
        binding.txtSignUp.setOnClickListener {
            val intent = Intent(this, Sign_UpActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)

        }

        /* TODO
        //forgot password
        binding.forget.setOnClickListener {

        }
         */


        //BUTTONS

        //Login
        binding.btnLogin.setOnClickListener {
            val email = binding.Name.text.toString().trim{it <= ' '}
            val pass =  binding.password.text.toString().trim{it <= ' '}

            if (email.isNotEmpty() && pass.isNotEmpty() ){

                    // when all conditions are met
                    fb.signInWithEmailAndPassword(email , pass).addOnCompleteListener {
                        //when sign p is authorised move to the main activity
                        if(it.isSuccessful){
                            val intent = Intent(this, MainActivity ::class.java)
                            //getting ri of activities in the background
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                            startActivity(intent)

                        }else
                        {  //show the exception if not successfull
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }

            }else
            {
                Toast.makeText(this, "Empty field" , Toast.LENGTH_SHORT).show()
            }
        }

    }
}