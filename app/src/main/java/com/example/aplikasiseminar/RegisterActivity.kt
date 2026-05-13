package com.example.aplikasiseminar

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var etNama: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText

    private lateinit var btnDaftar: Button
    private lateinit var btnBack: ImageView

    private lateinit var spProdi: Spinner

    private lateinit var cbCoding: CheckBox
    private lateinit var cbReading: CheckBox
    private lateinit var cbGaming: CheckBox
    private lateinit var cbTraveling: CheckBox
    private lateinit var cbLainnya: CheckBox

    private lateinit var rgGender: RadioGroup

    private var isPasswordVisible = false
    private var isConfirmVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initView()

        setupSpinner()

        realtimeValidation()

        setupPasswordToggle()

        setupLongPress()

        btnBack.setOnClickListener {

            startActivity(
                Intent(this, LoginActivity::class.java)
            )

            finish()
        }

        btnDaftar.setOnClickListener {

            val validationMessage = validateInput()

            if (validationMessage.isNotEmpty()) {

                AlertDialog.Builder(this)
                    .setTitle("Data Belum Lengkap")
                    .setMessage(validationMessage)
                    .setPositiveButton("OK", null)
                    .show()

            } else {

                AlertDialog.Builder(this)
                    .setTitle("Konfirmasi")
                    .setMessage("Apakah data sudah benar?")
                    .setNegativeButton("Tidak", null)
                    .setPositiveButton("Ya") { _, _ ->

                        saveAccount()

                        Toast.makeText(
                            this,
                            "Registrasi berhasil",
                            Toast.LENGTH_SHORT
                        ).show()

                        startActivity(
                            Intent(this, LoginActivity::class.java)
                        )

                        finish()
                    }
                    .show()
            }
        }
    }

    private fun initView() {

        etNama = findViewById(R.id.etNama)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)

        btnDaftar = findViewById(R.id.btnDaftar)
        btnBack = findViewById(R.id.btnBack)

        spProdi = findViewById(R.id.spProdi)

        cbCoding = findViewById(R.id.cbCoding)
        cbReading = findViewById(R.id.cbReading)
        cbGaming = findViewById(R.id.cbGaming)
        cbTraveling = findViewById(R.id.cbTraveling)
        cbLainnya = findViewById(R.id.cbLainnya)

        rgGender = findViewById(R.id.rgGender)
    }

    private fun setupSpinner() {

        val prodi = arrayOf(
            "Teknik Industri",
            "Teknik Informatika",
            "Bisnis Digital",
            "Management Retail",
            "Desain Komunikasi Visual"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            prodi
        )

        spProdi.adapter = adapter
    }

    private fun realtimeValidation() {

        btnDaftar.isEnabled = false
        btnDaftar.alpha = 0.5f

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

                validateRealtime()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }

        etNama.addTextChangedListener(watcher)
        etEmail.addTextChangedListener(watcher)
        etPassword.addTextChangedListener(watcher)
        etConfirmPassword.addTextChangedListener(watcher)
    }

    private fun validateRealtime() {

        val nama = etNama.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()

        if (nama.isEmpty()) {
            etNama.error = "Nama wajib diisi"
        } else {
            etNama.error = null
        }

        if (email.isEmpty()) {

            etEmail.error = "Email wajib diisi"

        } else if (
            !android.util.Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches()
        ) {

            etEmail.error = "Format email tidak valid"

        } else {

            etEmail.error = null
        }

        if (password.isEmpty()) {
            etPassword.error = "Password wajib diisi"
        } else {
            etPassword.error = null
        }

        if (confirmPassword.isEmpty()) {

            etConfirmPassword.error =
                "Konfirmasi password wajib diisi"

        } else if (password != confirmPassword) {

            etConfirmPassword.error =
                "Password tidak sama"

        } else {

            etConfirmPassword.error = null
        }

        val isValid =
            nama.isNotEmpty() &&
                    email.isNotEmpty() &&
                    android.util.Patterns.EMAIL_ADDRESS
                        .matcher(email)
                        .matches() &&
                    password.isNotEmpty() &&
                    confirmPassword.isNotEmpty() &&
                    password == confirmPassword

        btnDaftar.isEnabled = isValid

        btnDaftar.alpha =
            if (isValid) 1.0f else 0.5f
    }

    private fun setupPasswordToggle() {

        etPassword.setOnTouchListener { _, event ->

            val DRAWABLE_END = 2

            if (event.action == MotionEvent.ACTION_UP) {

                if (event.rawX >=
                    (etPassword.right -
                            etPassword.compoundDrawables[DRAWABLE_END].bounds.width())
                ) {

                    isPasswordVisible = !isPasswordVisible

                    if (isPasswordVisible) {

                        etPassword.transformationMethod =
                            HideReturnsTransformationMethod.getInstance()

                    } else {

                        etPassword.transformationMethod =
                            PasswordTransformationMethod.getInstance()
                    }

                    etPassword.setSelection(
                        etPassword.text.length
                    )

                    return@setOnTouchListener true
                }
            }

            false
        }

        etConfirmPassword.setOnTouchListener { _, event ->

            val DRAWABLE_END = 2

            if (event.action == MotionEvent.ACTION_UP) {

                if (event.rawX >=
                    (etConfirmPassword.right -
                            etConfirmPassword.compoundDrawables[DRAWABLE_END].bounds.width())
                ) {

                    isConfirmVisible = !isConfirmVisible

                    if (isConfirmVisible) {

                        etConfirmPassword.transformationMethod =
                            HideReturnsTransformationMethod.getInstance()

                    } else {

                        etConfirmPassword.transformationMethod =
                            PasswordTransformationMethod.getInstance()
                    }

                    etConfirmPassword.setSelection(
                        etConfirmPassword.text.length
                    )

                    return@setOnTouchListener true
                }
            }

            false
        }
    }

    private fun setupLongPress() {

        btnDaftar.setOnLongClickListener {

            Toast.makeText(
                this,
                "Tekan sekali untuk melakukan registrasi",
                Toast.LENGTH_LONG
            ).show()

            true
        }
    }

    private fun validateInput(): String {

        val error = StringBuilder()

        if (etNama.text.toString().trim().isEmpty()) {
            error.append("• Nama lengkap belum diisi\n")
        }

        if (etEmail.text.toString().trim().isEmpty()) {
            error.append("• Email belum diisi\n")
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(
                etEmail.text.toString()
            ).matches()
        ) {
            error.append("• Format email tidak valid\n")
        }

        if (etPassword.text.toString().isEmpty()) {
            error.append("• Password belum diisi\n")
        }

        if (etConfirmPassword.text.toString().isEmpty()) {
            error.append("• Konfirmasi password belum diisi\n")
        }

        if (etPassword.text.toString()
            != etConfirmPassword.text.toString()
        ) {
            error.append("• Password tidak sama\n")
        }

        if (rgGender.checkedRadioButtonId == -1) {
            error.append("• Jenis kelamin belum dipilih\n")
        }

        if (countCheckedHobbies() < 3) {
            error.append("• Pilih minimal 3 hobi\n")
        }

        if (spProdi.selectedItemPosition == 0) {
            error.append("• Program studi belum dipilih\n")
        }

        return error.toString()
    }

    private fun countCheckedHobbies(): Int {

        var count = 0

        if (cbCoding.isChecked) count++
        if (cbReading.isChecked) count++
        if (cbGaming.isChecked) count++
        if (cbTraveling.isChecked) count++
        if (cbLainnya.isChecked) count++

        return count
    }

    private fun saveAccount() {

        val sharedPreferences: SharedPreferences =
            getSharedPreferences("USER_DATA", MODE_PRIVATE)

        val editor = sharedPreferences.edit()

        editor.putString("nama", etNama.text.toString())

        editor.putString("email", etEmail.text.toString())

        editor.putString("password", etPassword.text.toString())

        editor.apply()
    }
}