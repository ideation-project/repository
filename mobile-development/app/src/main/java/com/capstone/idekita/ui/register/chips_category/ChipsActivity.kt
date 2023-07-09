package com.capstone.idekita.ui.register.chips_category

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.capstone.idekita.api.ApiConfig
import com.capstone.idekita.databinding.ActivityChipsBinding
import com.capstone.idekita.response.RegisterResponse
import com.capstone.idekita.ui.login.LoginActivity
import com.capstone.idekita.ui.register.RegisterActivity
import com.google.android.material.chip.Chip
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChipsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityChipsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityChipsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val x = binding.chipBudaya



        binding.btRegister.setOnClickListener{

            val getList = intent.getStringArrayListExtra(LIST_DATA_REGISTER)
            if(getList != null && whenChipChecked().isNotEmpty()){
                register(getList[0],getList[1],getList[2],getList[3],whenChipChecked())
            }else{
                Toast.makeText(this,"Silahakn pilih kategori", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun whenChipChecked(): String {
        val listChip = ArrayList<String>()
        val checked = binding.chipGroup.checkedChipIds
        for (chipId in checked) {
            val chip = binding.chipGroup.findViewById<Chip>(chipId)
            listChip.add(chip.text.toString())
        }

        return listChip.joinToString(separator = " | ")
    }

    private fun register(name : String,email : String,password : String,username : String,pref : String) {
        //showLoading(true)


        val client = ApiConfig.getApiService().postRegister(name, email, password, username,pref)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                val responseBody = response.body()
                //showLoading(false)
                if (response.isSuccessful && responseBody != null) {
                    AlertDialog.Builder(this@ChipsActivity).apply {
                        setTitle("Anda Telah Terdaftar")
                        setMessage("Silahkan klik lanjut untuk menuju ke halaman login")
                        setPositiveButton("Lanjut") { _, _ ->
                            val intent = Intent(context, LoginActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }
                        create()
                        show()
                    }
                } else {
                    //showLoading(false)
                    Log.e(RegisterActivity.TAG, "onFailure ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                //showLoading(false)
                Log.e(RegisterActivity.TAG, "onFailure ${t.message}")
            }

        })


    }

    companion object {
//        const val USERNAME = "username"
//        const val PASSWORD = "password"
//        const val EMAIL = "email"
//        const val NAME = "name"
        const val LIST_DATA_REGISTER = "listdata"
    }
}