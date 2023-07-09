package com.capstone.idekita

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.capstone.idekita.databinding.ActivityMainBinding
import com.capstone.idekita.ui.addProject.AddProjectActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {


    private val mainViewModel by viewModels<MainViewModel> {
        MainViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        //setViewModel()

        binding.fabAddProject.setOnClickListener {
            val intent = Intent(this@MainActivity, AddProjectActivity::class.java)
            startActivity(intent)
        }

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setupWithNavController(navController)

    }


}