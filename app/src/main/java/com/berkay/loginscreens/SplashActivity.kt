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

        binding.ivSplash.alpha = 0f
        binding.loading.alpha = 0f
        binding.textView6.alpha = 0f
        binding.ivSplash.animate().setDuration(1500).alpha(1f).withEndAction(Runnable {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        })
        binding.loading.animate().setDuration(1500).alpha(1f).withEndAction(Runnable {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        })
        binding.textView6.animate().setDuration(1500).alpha(1f).withEndAction(Runnable {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        })


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
            } else {
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
            }
        }, 2000)


    }
}