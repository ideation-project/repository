package com.gemastik.ideation.ui.splashScreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.gemastik.ideation.MainActivity
import com.gemastik.ideation.databinding.ActivitySplashScreenBinding
import com.gemastik.ideation.dummy.data.DummyDataStore
import com.gemastik.ideation.dummy.data.dataStore
import com.gemastik.ideation.dummy.vm.DummyFactory
import com.gemastik.ideation.dummy.vm.DummyViewModel


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