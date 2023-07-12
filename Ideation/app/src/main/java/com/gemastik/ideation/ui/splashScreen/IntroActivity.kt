package com.gemastik.ideation.ui.splashScreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.gemastik.ideation.MainActivity
import com.gemastik.ideation.databinding.ActivityIntroBinding
import com.gemastik.ideation.dummy.data.DummyDataStore
import com.gemastik.ideation.dummy.data.dataStore
import com.gemastik.ideation.dummy.vm.DummyFactory
import com.gemastik.ideation.dummy.vm.DummyViewModel

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