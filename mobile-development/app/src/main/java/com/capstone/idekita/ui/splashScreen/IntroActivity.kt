package com.capstone.idekita.ui.splashScreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.capstone.idekita.MainActivity
import com.capstone.idekita.databinding.ActivityIntroBinding
import com.capstone.idekita.dummy.data.DummyDataStore
import com.capstone.idekita.dummy.data.dataStore
import com.capstone.idekita.dummy.vm.DummyFactory
import com.capstone.idekita.dummy.vm.DummyViewModel

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = DummyDataStore.getInstance(application.dataStore)
        val viewModel = ViewModelProvider(this, DummyFactory(pref)).get(
            DummyViewModel::class.java
        )

        supportActionBar?.hide()

        binding.btToLogin.setOnClickListener {
            viewModel.saveToken(true)
            startActivity(
                Intent(this, MainActivity::class.java)
            )
            finish()
        }
    }
}