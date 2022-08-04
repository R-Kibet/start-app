package com.example.start

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.start.databinding.ActivitySignUpBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

//using binding

class Sign_UpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var fb: FirebaseAuth
    private lateinit var google: GoogleSignInClient

    private companion object {
        private const val RC_SIGN_IN = 1001
        private const val TAG = "GOOGLE_SIGN_IN_TAG"
        private const val EXTRA_NAME = "EXTRA_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //initialize binding , firebase ,Google
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Firebase
        fb = FirebaseAuth.getInstance()

        //google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("750849677315-gctqa38h7b5qolimfuuobn83bar92vm5.apps.googleusercontent.com")
            .requestEmail()
            .build()

        google = GoogleSignIn.getClient(this, gso)

        //initialize the button
        binding.btnGoogle.setOnClickListener {
            val intent = google.signInIntent
            startActivityForResult(intent, RC_SIGN_IN)
        }


        /*
         TODO(
             2: buttons fb
             google sign in activity moving to main activity
              )

         */


        //text view already have an account
        binding.account.setOnClickListener {
            val intent = Intent(this, Sign_inActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }

        //sign in button

        /*
    it will only sign up when certain parameters are met
    fields  should not be empty
    passwords should match
     */
        binding.btnSignIn.setOnClickListener {

            // fields
            val email = binding.email.text.toString().trim { it <= ' ' }
            val pass = binding.password.text.toString().trim { it <= ' ' }
            val conf = binding.confirm.text.toString().trim { it <= ' ' }

            //check if empty

            if (email.isNotEmpty() && pass.isNotEmpty() && conf.isNotEmpty()) {
                if (pass == conf) {

                    // when all conditions are met
                    fb.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        //when sign p is authorised move to the main ativity
                        if (it.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java)
                            //getting ri of activities in the background
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                            startActivity(intent)


                        } else {  //show the exception if not successfull
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                } else {
                    Toast.makeText(this, "password do not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Empty field", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {

                val account = task.getResult(ApiException::class.java)
                Log.d(ContentValues.TAG, "firebaseauth with google" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)

            } catch (e: ApiException) {
                // ...
                Log.w(ContentValues.TAG, "Google sign n failed")
            }
        }
    }


    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        fb.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = fb.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(this, MainActivity ::class.java)
            //intent.putExtra(EXTRA_NAME, user.displayName)
            startActivity(intent)
        }
    }

}



