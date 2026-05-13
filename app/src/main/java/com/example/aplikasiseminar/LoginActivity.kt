package com.example.aplikasiseminar

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText

    private lateinit var btnLogin: Button
    private lateinit var tvGoToRegister: TextView

    private lateinit var ivShowPassword: ImageView

    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()

        realtimeValidation()

        setupPasswordToggle()

        tvGoToRegister.setOnClickListener {

            startActivity(
                Intent(this, RegisterActivity::class.java)
            )
        }

        btnLogin.setOnClickListener {

            loginAccount()
        }
    }

    private fun initView() {

        etEmail = findViewById(R.id.etEmail)

        etPassword = findViewById(R.id.etPassword)

        btnLogin = findViewById(R.id.btnLogin)

        tvGoToRegister =
            findViewById(R.id.tvGoToRegister)

        ivShowPassword =
            findViewById(R.id.ivShowPassword)
    }

    private fun realtimeValidation() {

        btnLogin.isEnabled = false
        btnLogin.alpha = 0.5f

        val watcher = object : TextWatcher {

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {

                validateForm()
            }

            override fun afterTextChanged(
                s: Editable?
            ) {
            }
        }

        etEmail.addTextChangedListener(watcher)

        etPassword.addTextChangedListener(watcher)
    }

    private fun validateForm() {

        val usernameOrEmail =
            etEmail.text.toString().trim()

        val password =
            etPassword.text.toString().trim()

        if (usernameOrEmail.isEmpty()) {

            etEmail.error =
                "Nama atau email wajib diisi"

        } else {

            etEmail.error = null
        }

        if (password.isEmpty()) {

            etPassword.error =
                "Password wajib diisi"

        } else {

            etPassword.error = null
        }

        val isValid =
            usernameOrEmail.isNotEmpty() &&
                    password.isNotEmpty()

        btnLogin.isEnabled = isValid

        btnLogin.alpha =
            if (isValid) 1.0f else 0.5f
    }

    private fun setupPasswordToggle() {

        ivShowPassword.setOnClickListener {

            isPasswordVisible =
                !isPasswordVisible

            if (isPasswordVisible) {

                etPassword.transformationMethod =
                    HideReturnsTransformationMethod
                        .getInstance()

                ivShowPassword.setColorFilter(
                    resources.getColor(
                        android.R.color.holo_blue_dark
                    )
                )

            } else {

                etPassword.transformationMethod =
                    PasswordTransformationMethod
                        .getInstance()

                ivShowPassword.setColorFilter(
                    resources.getColor(
                        android.R.color.darker_gray
                    )
                )
            }

            etPassword.setSelection(
                etPassword.text.length
            )
        }
    }

    private fun loginAccount() {

        val sharedPreferences:
                SharedPreferences =
            getSharedPreferences(
                "USER_DATA",
                MODE_PRIVATE
            )

        val savedNama =
            sharedPreferences.getString(
                "nama",
                ""
            )

        val savedEmail =
            sharedPreferences.getString(
                "email",
                ""
            )

        val savedPassword =
            sharedPreferences.getString(
                "password",
                ""
            )

        val inputUser =
            etEmail.text.toString().trim()

        val inputPassword =
            etPassword.text.toString().trim()

        val isUserValid =
            inputUser == savedNama ||
                    inputUser == savedEmail

        if (!isUserValid) {

            AlertDialog.Builder(this)
                .setTitle("Login Gagal")
                .setMessage(
                    "Username atau email tidak ditemukan"
                )
                .setPositiveButton(
                    "OK",
                    null
                )
                .show()

            return
        }

        if (inputPassword != savedPassword) {

            AlertDialog.Builder(this)
                .setTitle("Login Gagal")
                .setMessage(
                    "Password salah"
                )
                .setPositiveButton(
                    "OK",
                    null
                )
                .show()

            return
        }

        Toast.makeText(
            this,
            "Login Berhasil",
            Toast.LENGTH_SHORT
        ).show()

        startActivity(
            Intent(
                this,
                MainActivity::class.java
            )
        )

        finish()
    }
}