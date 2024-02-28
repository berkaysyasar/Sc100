package com.berkay.loginscreens.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.berkay.loginscreens.R
import com.berkay.loginscreens.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var sharedpreferences : SharedPreferences
    private var email: String? = null
    private var password: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()


        sharedpreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        if (!(binding.remembermeCheckBox.isChecked) && sharedpreferences.contains("EMAIL") && sharedpreferences.contains("PASSWORD")) {
            autoLogin()
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailText.text.toString()
            val password = binding.passwordText.text.toString()

            if(binding.remembermeCheckBox.isChecked){
                saveCredentials(email, password)
            }

            if(binding.remembermeCheckBox.isChecked){
                autoLogin()
            }else{
                performLogin(email,password)
            }
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this,gso)

        binding.googlesigninButton.setOnClickListener{
            signInGoogle()
        }

    }

    private fun saveCredentials(email: String, password: String) {
        val editor = sharedpreferences.edit()
        editor.putString("EMAIL",email)
        editor.putString("PASSWORD",password)
        editor.apply()

    }

    private fun autoLogin() {
        val savedEmail = sharedpreferences.getString("EMAIL","")
        val savedpassword = sharedpreferences.getString("PASSWORD","")

        if(!savedEmail.isNullOrEmpty() && !savedpassword.isNullOrEmpty()){
            performLogin(savedEmail,savedpassword)
        }
    }

    private fun performLogin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Giriş başarılıysa
                    val user: FirebaseUser? = auth.currentUser
                    Toast.makeText(this, "Oturum açıldı: ${user?.email}", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Giriş başarısızsa
                    Toast.makeText(this, "Oturum açma hatası", Toast.LENGTH_SHORT).show()
                }
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
        val user: FirebaseUser? = auth.currentUser
        auth.signInWithCredential(credential).addOnCompleteListener{
            if(it.isSuccessful){
                Toast.makeText(this, "Oturum açıldı: ${user?.email}", Toast.LENGTH_SHORT).show()
                val intent : Intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this,it.exception.toString(),Toast.LENGTH_LONG).show()
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


