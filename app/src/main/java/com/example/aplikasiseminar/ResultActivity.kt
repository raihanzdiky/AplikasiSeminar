package com.example.aplikasiseminar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    private lateinit var tvNama: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvHp: TextView
    private lateinit var tvGender: TextView
    private lateinit var tvSeminar: TextView

    private lateinit var btnKembali: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_result)

        initView()

        getDataFromIntent()

        setupButton()
    }

    private fun initView() {

        tvNama =
            findViewById(R.id.tvNama)

        tvEmail =
            findViewById(R.id.tvEmail)

        tvHp =
            findViewById(R.id.tvHp)

        tvGender =
            findViewById(R.id.tvGender)

        tvSeminar =
            findViewById(R.id.tvSeminar)

        btnKembali =
            findViewById(R.id.btnKembali)
    }

    private fun getDataFromIntent() {

        val nama =
            intent.getStringExtra("nama")

        val email =
            intent.getStringExtra("email")

        val noHp =
            intent.getStringExtra("nohp")

        val gender =
            intent.getStringExtra("gender")

        val seminar =
            intent.getStringExtra("seminar")

        tvNama.text = nama

        tvEmail.text = email

        tvHp.text = noHp

        tvGender.text = gender

        tvSeminar.text = seminar
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
    }
}