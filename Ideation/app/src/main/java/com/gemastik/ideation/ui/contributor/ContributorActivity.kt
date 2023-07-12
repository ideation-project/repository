package com.gemastik.ideation.ui.contributor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gemastik.ideation.databinding.ActivityContributorBinding

class ContributorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContributorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContributorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setTitle("Daftar Kontributor")

        val extras = intent.extras
        if (extras != null){
            val idProyek = extras.getInt("id_extra").toString()

            binding.idProyek.text = idProyek
        }

    }
}