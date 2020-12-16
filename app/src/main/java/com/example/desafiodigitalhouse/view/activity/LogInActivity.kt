package com.example.desafiodigitalhouse.view.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import androidx.core.content.edit
import androidx.core.widget.doOnTextChanged
import com.example.desafiodigitalhouse.utils.Constants.SharedPreference.CHECKED_SHAREDPREFERENCE
import com.example.desafiodigitalhouse.utils.Constants.SharedPreference.EMAIL_SHAREDPREFERENCE
import com.example.desafiodigitalhouse.utils.Constants.SharedPreference.NAME_SHAREDPREFERENCE
import com.example.desafiodigitalhouse.utils.Constants.SharedPreference.PASSWORD_SHAREDPREFERENCE
import com.example.desafiotresdigitalhouse.R
import com.example.desafiotresdigitalhouse.databinding.ActivityLogInBinding
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_log_in.*

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    private var sharedPreferences: SharedPreferences? = null
    private var emailCheck = false
    private var passwordCheck = false
    private var validationEmail = false
    private var textInputEmail = ""
    private var textInputPassword = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        sharedPreferences = getSharedPreferences(NAME_SHAREDPREFERENCE, Context.MODE_PRIVATE)

        validatingEditText(tietEmail, tilEmail, R.string.string_email)
        validatingEditText(tietPassword, tilPassword, R.string.string_password)

        binding.tbRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            sharedPreferences?.edit {
                putString(EMAIL_SHAREDPREFERENCE, textInputEmail)
                putString(PASSWORD_SHAREDPREFERENCE, textInputPassword)
            }
        }
        binding.btLogIn.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            sharedPreferences?.edit {
                putString(EMAIL_SHAREDPREFERENCE, textInputEmail)
                putString(PASSWORD_SHAREDPREFERENCE, textInputPassword)
            }
        }

        binding.cbRemember.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences?.edit {
                putBoolean(CHECKED_SHAREDPREFERENCE, isChecked)
                putString(EMAIL_SHAREDPREFERENCE, textInputEmail)
                putString(PASSWORD_SHAREDPREFERENCE, textInputPassword)
            }
        }

        initComponents()
    }

    private fun initComponents() {
        val checkBoxRemember = sharedPreferences?.getBoolean(CHECKED_SHAREDPREFERENCE, false)
        val emailRemember = sharedPreferences?.getString(EMAIL_SHAREDPREFERENCE, "")
        val passwordRemember = sharedPreferences?.getString(PASSWORD_SHAREDPREFERENCE, "")

        binding.cbRemember.isChecked = checkBoxRemember ?: false
        if(binding.cbRemember.isChecked) {
            binding.tietEmail.setText(emailRemember)
            binding.tietPassword.setText(passwordRemember)
        }
    }

    private fun validatingEditText(editText: EditText, textInput: TextInputLayout, errorString: Int) {
        editText.doOnTextChanged { text, _, _, _ ->
            if(text?.isBlank() == true) {
                textInput.error = getString(R.string.string_error_message, getString(errorString))
                getByTag(editText.tag as String, false)
            }else {
                textInput.isErrorEnabled = false
                getByTag(editText.tag as String, true)
            }

            if(editText.tag == getString(R.string.string_email)) {
                validatingEmail(text.toString())
            }

            when(editText.tag) {
                getString(R.string.string_email) -> {
                    textInputEmail = text.toString()
                }
                getString(R.string.string_password) -> {
                    textInputPassword = text.toString()
                }
            }
            activatingButtonLogin()
        }
    }

    private fun getByTag(tag: String, check: Boolean) {
        when(tag) {
            getString(R.string.string_email) -> emailCheck = check
            getString(R.string.string_password) -> passwordCheck = check
        }
    }

    private fun validatingEmail(email: String) {
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            validationEmail = true
            tilEmail.isErrorEnabled = false
        }else {
            validationEmail = false
            tilEmail.error = getString(R.string.validationEmail)
        }
    }

    private fun activatingButtonLogin(): Boolean {
        val isOk: Boolean
        if(emailCheck && passwordCheck) {
            binding.btLogIn.isEnabled = true
            isOk = true
        }else {
            binding.btLogIn.isEnabled = false
            isOk = false
        }
        return isOk
    }
}