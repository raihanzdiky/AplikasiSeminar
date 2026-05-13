package com.example.aplikasiseminar

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class RegisterSeminarActivity : AppCompatActivity() {

    private lateinit var btnKembali: LinearLayout

    private lateinit var etNama: EditText
    private lateinit var etEmail: EditText
    private lateinit var etNoHp: EditText

    private lateinit var rgGender: RadioGroup

    private lateinit var spSeminar: Spinner

    private lateinit var cbPersetujuan: CheckBox

    private lateinit var btnDaftar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register_seminar)

        initView()

        setupSpinner()

        realtimeValidation()

        loadUserData()

        setupButton()
    }

    private fun initView() {

        btnKembali =
            findViewById(R.id.btnKembali)

        etNama =
            findViewById(R.id.etNama)

        etEmail =
            findViewById(R.id.etEmail)

        etNoHp =
            findViewById(R.id.etNoHp)

        etNoHp.inputType =
            android.text.InputType.TYPE_CLASS_NUMBER

        rgGender =
            findViewById(R.id.rgGender)

        spSeminar =
            findViewById(R.id.spSeminar)

        cbPersetujuan =
            findViewById(R.id.cbPersetujuan)

        btnDaftar =
            findViewById(R.id.btnDaftar)
    }

    private fun loadUserData() {

        val sharedPreferences: SharedPreferences =
            getSharedPreferences(
                "USER_DATA",
                MODE_PRIVATE
            )

        val nama =
            sharedPreferences.getString(
                "nama",
                ""
            )

        val email =
            sharedPreferences.getString(
                "email",
                ""
            )

        etNama.setText(nama)

        etEmail.setText(email)
    }

    private fun setupSpinner() {

        val seminarList = arrayOf(
            "Pilih Seminar"
        ) + MainActivity.seminarList

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            seminarList
        )

        spSeminar.adapter = adapter
    }

    private fun realtimeValidation() {

        etNama.addTextChangedListener(textWatcher)

        etEmail.addTextChangedListener(textWatcher)

        etNoHp.addTextChangedListener(textWatcher)

        etNoHp.filters =
            arrayOf(InputFilter.LengthFilter(13))
    }

    private val textWatcher = object : TextWatcher {

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

            validateNama()

            validateEmail()

            validateNoHp()
        }

        override fun afterTextChanged(s: Editable?) {
        }
    }

    private fun validateNama(): Boolean {

        return if (
            etNama.text.toString()
                .trim()
                .isEmpty()
        ) {

            etNama.error =
                "Nama lengkap wajib diisi"

            false

        } else {

            etNama.error = null

            true
        }
    }

    private fun validateEmail(): Boolean {

        val email =
            etEmail.text.toString().trim()

        return if (email.isEmpty()) {

            etEmail.error =
                "Email wajib diisi"

            false

        } else if (!email.contains("@")) {

            etEmail.error =
                "Email harus mengandung @"

            false

        } else {

            etEmail.error = null

            true
        }
    }

    private fun validateNoHp(): Boolean {

        val noHp =
            etNoHp.text.toString().trim()

        return when {

            noHp.isEmpty() -> {

                etNoHp.error =
                    "Nomor HP wajib diisi"

                false
            }

            !noHp.matches(Regex("[0-9]+")) -> {

                etNoHp.error =
                    "Nomor HP hanya boleh angka"

                false
            }

            !noHp.startsWith("08") -> {

                etNoHp.error =
                    "Nomor HP harus diawali 08"

                false
            }

            noHp.length < 10 ||
                    noHp.length > 13 -> {

                etNoHp.error =
                    "Nomor HP harus 10-13 digit"

                false
            }

            else -> {

                etNoHp.error = null

                true
            }
        }
    }

    private fun validateInput(): Boolean {

        val namaValid =
            validateNama()

        val emailValid =
            validateEmail()

        val hpValid =
            validateNoHp()

        var isValid =
            namaValid &&
                    emailValid &&
                    hpValid

        if (rgGender.checkedRadioButtonId == -1) {

            Toast.makeText(
                this,
                "Jenis kelamin wajib dipilih",
                Toast.LENGTH_SHORT
            ).show()

            isValid = false
        }

        if (spSeminar.selectedItemPosition == 0) {

            Toast.makeText(
                this,
                "Pilih seminar terlebih dahulu",
                Toast.LENGTH_SHORT
            ).show()

            isValid = false
        }

        if (!cbPersetujuan.isChecked) {

            Toast.makeText(
                this,
                "Anda harus menyetujui data",
                Toast.LENGTH_SHORT
            ).show()

            isValid = false
        }

        return isValid
    }

    private fun setupButton() {

        btnKembali.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )

            finish()
        }

        btnDaftar.setOnClickListener {

            if (validateInput()) {

                AlertDialog.Builder(this)
                    .setTitle("Konfirmasi")
                    .setMessage(
                        "Apakah data yang Anda isi sudah benar?"
                    )

                    .setNegativeButton(
                        "Tidak",
                        null
                    )

                    .setPositiveButton(
                        "Ya"
                    ) { _, _ ->

                        val selectedSeminar =
                            spSeminar.selectedItem.toString()

                        // SIMPAN STATUS SEMINAR
                        val seminarPrefs =
                            getSharedPreferences(
                                "SEMINAR_DATA",
                                MODE_PRIVATE
                            )

                        seminarPrefs.edit()
                            .putBoolean(
                                selectedSeminar,
                                true
                            )
                            .apply()

                        // AMBIL GENDER
                        val selectedGenderId =
                            rgGender.checkedRadioButtonId

                        val radioButton =
                            findViewById<RadioButton>(
                                selectedGenderId
                            )

                        // SIMPAN DATA HASIL
                        val resultPrefs =
                            getSharedPreferences(
                                "RESULT_DATA",
                                MODE_PRIVATE
                            )

                        resultPrefs.edit()
                            .putString(
                                "nama",
                                etNama.text.toString()
                            )
                            .putString(
                                "email",
                                etEmail.text.toString()
                            )
                            .putString(
                                "nohp",
                                etNoHp.text.toString()
                            )
                            .putString(
                                "gender",
                                radioButton.text.toString()
                            )
                            .putString(
                                "seminar",
                                selectedSeminar
                            )
                            .apply()

                        // PINDAH KE HALAMAN HASIL
                        val intent =
                            Intent(
                                this,
                                ResultActivity::class.java
                            )

                        intent.putExtra(
                            "nama",
                            etNama.text.toString()
                        )

                        intent.putExtra(
                            "email",
                            etEmail.text.toString()
                        )

                        intent.putExtra(
                            "nohp",
                            etNoHp.text.toString()
                        )

                        intent.putExtra(
                            "gender",
                            radioButton.text.toString()
                        )

                        intent.putExtra(
                            "seminar",
                            selectedSeminar
                        )

                        startActivity(intent)

                        finish()
                    }

                    .show()
            }
        }
    }
}