package com.gemastik.ideation.ui.PmDetailSide.contributorAcc.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.gemastik.ideation.MainActivity
import com.gemastik.ideation.UserPreference
import com.gemastik.ideation.api.ApiConfig
import com.gemastik.ideation.databinding.ActivityLoginBinding
import com.gemastik.ideation.model.UserModel
import com.gemastik.ideation.response.LoginResponse
import com.gemastik.ideation.ui.register.RegisterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {

    companion object {
        const val TAG = "LoginActivity"
    }

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setAction()
        setViewModel()

        val password = binding.PassworEditText

        password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(p0: Editable?) {
                //
            }

        })
    }


    private fun setViewModel() {
        loginViewModel = ViewModelProvider(
            this,
            LoginViewModelFactory(UserPreference.getInstance(dataStore))
        )[LoginViewModel::class.java]
    }

    private fun setAction() {
        binding.btnLogin.setOnClickListener {
            val email = binding.UsernameEditText.text.toString()
            val password = binding.PassworEditText.text.toString()
            when {

                email.isEmpty() -> {
                    binding.UsernameEditText.error = "Masukkan email"
                }
                password.isEmpty() -> {
                    binding.passwordInputLayout.error = "Masukkan password"
                }
                else -> {
                    login()
                }
            }
        }

        binding.LinkKedaftar.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {

        val username = binding.UsernameEditText.text.toString()
        val pasword = binding.PassworEditText.text.toString()
        showLoading(true)
        val client = ApiConfig.getApiService().postLogin(username, pasword)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                     showLoading(false)

                    loginViewModel.saveUser(
                        UserModel(
                            responseBody.user.username,
                            responseBody.user.email,
                            responseBody.user.token,
                            isLogin = true
                        )
                    )
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    showLoading(false)
                    Log.e(RegisterActivity.TAG, "onFailure ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure ${t.message}")
            }

        })
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar3.visibility =View.VISIBLE
        }else{
            binding.progressBar3.visibility =View.GONE
        }
    }

    private fun setMyButtonEnable() {
        val result = binding.PassworEditText.text
        if (result != null) {
            binding.btnLogin.isEnabled = result.length >= 8
        }
    }


}