package com.example.desafiodigitalhouse.view.activity

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import androidx.core.content.edit
import androidx.core.widget.doOnTextChanged
import com.example.desafiotresdigitalhouse.R
import com.example.desafiotresdigitalhouse.databinding.ActivityRegisterBinding
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.android.synthetic.main.activity_register.*
import java.io.FileNotFoundException
import java.lang.Exception

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private var checkEmail = false
    private var checkName = false
    private var checkPassword = false
    private var validationEmail = false
    var getUri: Uri = Uri.parse("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAMgAAADICAMAAACahl6sAAAAMFBMVEXU1NT////Y2Nj7+/va2trm5ubz8/Pf39/29vbe3t7j4+P8/Pzt7e3Z2dn09PTp6enlgXfuAAAEj0lEQVR4nO2dCZarOgxEMVPCkGT/u31N8+mEEIIHVUmf47sC6ghNRhZFkclkMplMJpPJZDKZTCaTyWQymUwmk8lsKLuu75sf+r7rSu2niaNrxrZyK6p2bDrt5wqibtrB7TC0Ta39fH6Uj+ueiIXrw/5r1rdHKmbaXvtJv9JUxxL+PKbRfto9yhAZsxSTb1gfKONXir0XrPb0jXdaYyHssRtujxge2s/+wu0w4H7jetN+/oU+2hz/GcWIp4xpMiZGbQ0TkV6+ptVWUZR3CR3O3ZVTSpnk5q9cVZWUEUlwj0pRiZw9JhRtIuQfC3ctHSLx6hWl2PWQ1uGcSrlykdfh3IWvQzJgPVEIXeIOMkN3kwajwzlyA1wmFrz7DNyXS6Di3YNaCXc4Hc4xDyNFS5N3rjwdPVKHc7yGEWoQokkgOf0VVn4HG4RmEmjImuEELmAOWeDkEki1uKZi6ADH3hlGBAaVvWsYRTCsXHxlwOuAJ5EZfCoBdOqfwHfv8Gw4A8+JJUeHc+j+iuQieCeB9ervoHt3Qn0yg65SKOlwAp0SCYXWDLrcYulwDquDFn3R8bfmCcGORBC6wwVsl3gaIbTEjk7tlPZwBtsknsYip/GR0wg5TR45TYlynqKR1LLjm/bT9COk0yD8edBpDh9OcxzEClv4DwukYxT8px5S/Yv/QEJyEsJECiUlMr7rUg5NGcNOlHeLMutEqFI4c3SEuEUaq4HnRMpn9oLg7qy5RtxA4wxvrBFcy/PmsTHDywvMIWaol1Anf4F1CnE2s4Ae1JGv7sPaEvZNPpS/868r1JBkMijcQYaUXCqXXQFuonTVVTwGcyPvE2mH17tS2Yk6/KC4/KWTvOKqusSmFlNSKS9/kFKiraMobiJKKgN7HySuUOteZv8jOTOaWPkwcUl6vSqFC7p7lAmHdq2N12ohdjeKlZ0oT25RnjIaiFYbuuDwdbW6ke4S5CqtISff0Hi7ymB24VlR9mNQGK7G3lbA+qVsonaL3I1tb/PdBfgJO/sB67A3aks1qpe+P1xE1tXctSPYRW6bk6aUXnYJkpazyFnjT4qGVW6Qr9QtvfaKX8z4HfLaxph1n74Q14KmtFE+sFqttMbWB07zSxmhwx9H1KxLx+CqJXVtqT/YZp42vjwBDMS0i7ozKEeRXS/pA+YkVe4Lgj+IM3oNHQglOjrklWjpkFYi+a0wWIngcaSePX6ViNkEOzDnoUQoCvPzxztC+YR2P2wfkclscl3yGYFqhbbR5TvJZ/fEW8bfSQzC2gHrSWLoMuDoC0kOb8RBZhLcBDOAGUvC4KZ6JlwTPSlI7dB9iOzibb1YE5Evl6GItRAVuYi7XPyJOOyykwpfiUiLJmrFLcHVI/pCWCzBF8mMGiTYJFYNEmwSswYJNMnNrEF+TBLy4dewQYJMYtdDJgK8xFy1uMa/djSZ1J943xInLpqLw/frtcGyd41nEUzcVxqLn7sbd/UJP3c31ql/wqt7Jy7+i8en5zV1lrWHzxmX8E8OMXj8OvF/ELMmjuOWyTOHLcenEOaz4cxxTjRd+D7Z/KDkH+MbT03dnEr6AAAAAElFTkSuQmCC")
    var nameText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBar()
        validatingEditText(tietNameRegister, tilNameRegister, R.string.string_name)
        validatingEditText(tietEmailRegister, tilEmailRegister, R.string.string_email)
        validatingEditText(tietPasswordRegister, tilPasswordRegister, R.string.string_password)

        binding.btRegister.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            intent.putExtra("uriPhoto", getUri)
            intent.putExtra("name", nameText)
            startActivity(intent)
        }

        addImageFromGalery()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val targetUri = data?.data
        val bitmap: Bitmap
        if(resultCode == Activity.RESULT_OK) {
            try {
                bitmap = BitmapFactory.decodeStream(targetUri?.let {
                    contentResolver.openInputStream(it)
                })
                binding.ivProfilePhoto.setImageBitmap(bitmap)
                targetUri?.let {
                    getUri = it
                }
            } catch (exception: FileNotFoundException) {
                exception.printStackTrace()
            }
        }
    }

    private fun addImageFromGalery() {
        binding.ibAddPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 0)
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

            when(editText.tag) {
                getString(R.string.string_email) -> validatingEmail(text.toString())
                getString(R.string.string_name) -> nameText = text.toString()
            }

            activatingButtonLogin()
        }
    }

    private fun getByTag(tag: String, check: Boolean) {
        when(tag) {
            getString(R.string.string_email) -> checkEmail = check
            getString(R.string.string_password) -> checkPassword = check
            getString(R.string.string_name) -> checkName = check
        }
    }

    private fun validatingEmail(email: String) {
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            validationEmail = true
            tilEmailRegister.isErrorEnabled = false
        }else {
            validationEmail = false
            tilEmailRegister.error = getString(R.string.validationEmail)
        }
    }

    private fun activatingButtonLogin(): Boolean {
        val isOk: Boolean
        if(checkEmail && checkPassword && checkName) {
            binding.btRegister.isEnabled = true
            isOk = true
        }else {
            binding.btRegister.isEnabled = false
            isOk = false
        }
        return isOk
    }

    private fun setActionBar() {
        supportActionBar?.setIcon(R.drawable.ic_baseline_arrow_back_24)
        supportActionBar?.setTitle(R.string.string_register)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}