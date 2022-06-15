package com.example.storyaplikasi.akun

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.about.app_substoryapp.Connection.ApiConfig
import com.about.app_substoryapp.Connection.ResponseRegister
import com.example.storyaplikasi.button.btnregister
import com.example.storyaplikasi.databinding.ActivityRegisterBinding
import com.example.storyaplikasi.edittext.myemail
import com.example.storyaplikasi.edittext.myname
import com.example.storyaplikasi.edittext.mypassword
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val api by lazy { ApiConfig().endPoint }
    private lateinit var buttonregister: btnregister
    private lateinit var etemail: myemail
    private lateinit var etpassword: mypassword
    private lateinit var etname: myname

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonregister = binding.myBtn
        etemail = binding.myEmail
        etpassword = binding.myPassword
        etname = binding.myName

        setMyButtonEnable()

        binding.myName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(s: Editable) {

            }

        })

        binding.myEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        binding.myPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        buttonregister.setOnClickListener {
            checkForm()
        }

    }

    private fun checkForm() {
        api.Create_user(
            binding.myName.text.toString(),
            binding.myEmail.text.toString(),
            binding.myPassword.text.toString()
        ).enqueue(object : Callback<ResponseRegister> {
            override fun onResponse(
                call: Call<ResponseRegister>,
                response: Response<ResponseRegister>
            ) {
                val data = response.body()
                if (response.isSuccessful) {
                    if (data != null) {
                        Toast.makeText(applicationContext, data.message, Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        finish()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setMyButtonEnable() {
        val resultname = etname.text
        val resultemail = etemail.text
        val resultpw = etpassword.text

        buttonregister.isEnabled = resultname != null && resultname.toString()
            .isNotEmpty() && resultemail != null && resultemail.toString()
            .isNotEmpty() && resultpw != null && resultpw.toString().length > 5

    }
}