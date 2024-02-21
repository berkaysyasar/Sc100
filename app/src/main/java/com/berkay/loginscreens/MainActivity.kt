package com.berkay.loginscreens

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.berkay.loginscreens.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.internal.OnConnectionFailedListener
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Initialize Firebase Auth
        auth = Firebase.auth

        val currentUser = auth.currentUser

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this,gso)

        binding.googlesigninButton.setOnClickListener{
            signInGoogle()
        }

    }

    private fun signInGoogle(){
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
                if(result.resultCode == Activity.RESULT_OK){
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    handleResults(task)
                }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if(task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if(account!= null){
                updateUI(account)
            }
        }else{
            Toast.makeText(this,task.exception.toString(),Toast.LENGTH_LONG).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken,null)
        auth.signInWithCredential(credential).addOnCompleteListener{
            if(it.isSuccessful){
                val intent : Intent = Intent(this,menu::class.java)
                intent.putExtra("email",account.email)
                startActivity(intent)
            }else{
                Toast.makeText(this,it.exception.toString(),Toast.LENGTH_LONG).show()
            }
        }
    }


    fun girisyapButtonClicked(view : View){
        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()
        if(email.equals("") || password.equals("")){
            Toast.makeText(this,"Enter email and password!",Toast.LENGTH_LONG).show()
        }else{
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                val intent = Intent(this@MainActivity,menu::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener{
                Toast.makeText(this@MainActivity,it.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }

    }

    fun kayitolButtonClicked(view: View) {
        val intent = Intent(this@MainActivity, SignupActivity::class.java)
        startActivity(intent)
    }

    fun sifreunutmaButton(view: View){
        val intent = Intent(this@MainActivity, ForgotPasswordActivity::class.java)
        startActivity(intent)
    }



    }


