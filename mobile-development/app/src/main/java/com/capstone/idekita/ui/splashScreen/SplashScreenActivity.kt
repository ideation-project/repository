package com.capstone.idekita.ui.splashScreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.capstone.idekita.MainActivity
import com.capstone.idekita.databinding.ActivitySplashScreenBinding
import com.capstone.idekita.dummy.data.DummyDataStore
import com.capstone.idekita.dummy.data.dataStore
import com.capstone.idekita.dummy.vm.DummyFactory
import com.capstone.idekita.dummy.vm.DummyViewModel


@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // dummy
        val pref = DummyDataStore.getInstance(application.dataStore)
        val viewModel = ViewModelProvider(this, DummyFactory(pref)).get(
            DummyViewModel::class.java
        )


        Handler().postDelayed({
            viewModel.getToken().observe(this) {
                cek(it)
            }
            finish()
        }, DELAY)

        supportActionBar?.hide()

    }

    private fun cek(bool: Boolean) {
        if (bool) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            startActivity(Intent(this, IntroActivity::class.java))
        }
    }


    companion object {
        const val DELAY: Long = 2000
    }
}