package com.berkay.loginscreens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.berkay.loginscreens.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        with(binding) {
            listOf(ivSplash, loading, loadingsplash).forEach { it.alpha = 0f }

            ivSplash.animate().setDuration(1000).alpha(1f).withEndAction {
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }

            loading.animate().setDuration(1000).alpha(1f).withEndAction {
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }

            loadingsplash.animate().setDuration(1000).alpha(1f).withEndAction {
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }


        val sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        val isFirstStart = sharedPreferences.getBoolean("isFirstStart", true)

        Handler().postDelayed({
            if (isFirstStart) {
                val i = Intent(this, WelcomePageActivity::class.java)
                with(sharedPreferences.edit()) {
                    putBoolean("isFirstStart", false)
                    apply()
                }
                startActivity(i)
                finish()
            } else {
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                finish()
            }
        }, 1500)


    }
}