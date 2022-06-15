package com.example.storyaplikasi.akun

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.about.app_substoryapp.Connection.ApiConfig
import com.about.app_substoryapp.Connection.ResponseLogin
import com.about.app_substoryapp.Helper.Constant
import com.example.storyaplikasi.MainActivity
import com.example.storyaplikasi.button.btnlogin
import com.example.storyaplikasi.databinding.ActivityLoginBinding
import com.example.storyaplikasi.edittext.myemail
import com.example.storyaplikasi.edittext.mypassword
import com.example.storyaplikasi.helper.PreferencesHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val api by lazy { ApiConfig().endPoint }
    private lateinit var sharedpref: PreferencesHelper
    private lateinit var buttonlogin: btnlogin
    private lateinit var etemail: myemail
    private lateinit var etpassword: mypassword

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedpref = PreferencesHelper(this)

        buttonlogin = binding.btnLogin
        etemail = binding.myEmail
        etpassword = binding.myPassword

        setMyButtonEnable()

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        etemail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        etpassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        buttonlogin.setOnClickListener {

            loginProses()

        }
    }

    override fun onStart() {
        super.onStart()
        if (sharedpref.getBoolean(Constant.PREF_IS_LOGIN)) {
            moveToHome()
        }
    }


    private fun loginProses() {
        showloading(true)
        api.loginUser(
            binding.myEmail.text.toString(),
            binding.myPassword.text.toString()
        ).enqueue(object : Callback<ResponseLogin> {
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                val data = response.body()
                if (response.isSuccessful) {
                    if (data != null) {
                        showloading(false)
                        Toast.makeText(applicationContext, data.message, Toast.LENGTH_SHORT).show()
                        saveUserData(
                            data.loginResult.userId,
                            data.loginResult.name,
                            data.loginResult.token
                        )
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(applicationContext, "data null", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(applicationContext, "respon gagal", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun saveUserData(userId: String, name: String, token: String) {
        sharedpref.put(Constant.PREF_USERID, userId)
        sharedpref.put(Constant.PREF_NAME, name)
        sharedpref.put(Constant.PREF_TOKEN, token)
        sharedpref.put(Constant.PREF_IS_LOGIN, true)
    }

    private fun showloading(isLoading: Boolean) {
        if (isLoading) {
            binding.loading.visibility = View.VISIBLE
        } else {
            binding.loading.visibility = View.GONE
        }
    }

    private fun moveToHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun setMyButtonEnable() {
        val resultemail = etemail.text
        val resultpw = etpassword.text

        buttonlogin.isEnabled = resultemail != null && resultemail.toString()
            .isNotEmpty() && resultpw != null && resultpw.toString().length > 5

    }
}