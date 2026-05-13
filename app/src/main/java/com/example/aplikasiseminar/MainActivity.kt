package com.example.aplikasiseminar

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {

        val seminarList = arrayOf(
            "Seminar IT Modern",
            "Seminar Bisnis Digital",
            "Cyber Security Summit",
            "Seminar Robotik & AI",
            "Digital Photography"
        )
    }

    private lateinit var tvHello: TextView

    private lateinit var btnLogout: LinearLayout
    private lateinit var btnDaftarSeminar: LinearLayout
    private lateinit var btnHasilSeminar: LinearLayout

    private lateinit var tvStatus1: TextView
    private lateinit var tvStatus2: TextView
    private lateinit var tvStatus3: TextView
    private lateinit var tvStatus4: TextView
    private lateinit var tvStatus5: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initView()

        loadUserData()

        loadStatusSeminar()

        setupLogout()

        setupNavigation()
    }

    override fun onResume() {
        super.onResume()
        loadStatusSeminar()
    }

    private fun initView() {

        tvHello = findViewById(R.id.tvHello)

        btnLogout =
            findViewById(R.id.btnLogout)

        btnDaftarSeminar =
            findViewById(R.id.btnDaftarSeminar)

        btnHasilSeminar =
            findViewById(R.id.btnHasilSeminar)

        tvStatus1 =
            findViewById(R.id.tvStatus1)

        tvStatus2 =
            findViewById(R.id.tvStatus2)

        tvStatus3 =
            findViewById(R.id.tvStatus3)

        tvStatus4 =
            findViewById(R.id.tvStatus4)

        tvStatus5 =
            findViewById(R.id.tvStatus5)
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
                "User"
            )

        tvHello.text = "Hello, $nama!"
    }

    private fun loadStatusSeminar() {

        val sharedPreferences: SharedPreferences =
            getSharedPreferences(
                "SEMINAR_DATA",
                MODE_PRIVATE
            )

        updateStatus(
            tvStatus1,
            sharedPreferences.getBoolean(
                seminarList[0],
                false
            )
        )

        updateStatus(
            tvStatus2,
            sharedPreferences.getBoolean(
                seminarList[1],
                false
            )
        )

        updateStatus(
            tvStatus3,
            sharedPreferences.getBoolean(
                seminarList[2],
                false
            )
        )

        updateStatus(
            tvStatus4,
            sharedPreferences.getBoolean(
                seminarList[3],
                false
            )
        )

        updateStatus(
            tvStatus5,
            sharedPreferences.getBoolean(
                seminarList[4],
                false
            )
        )
    }

    private fun updateStatus(
        textView: TextView,
        isRegistered: Boolean
    ) {

        if (isRegistered) {

            textView.text =
                "Sudah Mendaftar"

            textView.setBackgroundColor(
                Color.parseColor("#4CAF50")
            )

        } else {

            textView.text =
                "Belum Mendaftar"

            textView.setBackgroundColor(
                Color.parseColor("#FF9800")
            )
        }
    }

    private fun setupLogout() {

        btnLogout.setOnClickListener {

            AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage(
                    "Apakah anda yakin ingin keluar dari sesi ini?"
                )

                .setNegativeButton("Tidak", null)

                .setPositiveButton("Ya") { _, _ ->

                    val seminarPrefs =
                        getSharedPreferences(
                            "SEMINAR_DATA",
                            MODE_PRIVATE
                        )

                    seminarPrefs.edit()
                        .clear()
                        .apply()

                    // RESET HASIL PENDAFTARAN
                    val resultPrefs =
                        getSharedPreferences(
                            "RESULT_DATA",
                            MODE_PRIVATE
                        )

                    resultPrefs.edit()
                        .clear()
                        .apply()

                    startActivity(
                        Intent(
                            this,
                            LoginActivity::class.java
                        )
                    )

                    finishAffinity()
                }

                .show()
        }
    }

    private fun setupNavigation() {


        btnDaftarSeminar.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    RegisterSeminarActivity::class.java
                )
            )
        }

        btnHasilSeminar.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    HistoryResultActivity::class.java
                )
            )
        }
    }
}