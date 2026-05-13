package com.example.aplikasiseminar

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView

class HistoryResultActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageView
    private lateinit var btnFilter: ImageView
    private lateinit var etSearch: EditText

    private lateinit var btnDetail1: Button
    private lateinit var btnDetail2: Button
    private lateinit var btnDetail3: Button
    private lateinit var btnDetail4: Button
    private lateinit var btnDetail5: Button

    private lateinit var card1: MaterialCardView
    private lateinit var card2: MaterialCardView
    private lateinit var card3: MaterialCardView
    private lateinit var card4: MaterialCardView
    private lateinit var card5: MaterialCardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.history_result)

        initView()

        setupBackButton()

        setupNotification()

        setupSearch()

        setupFilter()

        setupDetailButton()
    }

    private fun initView() {

        btnBack =
            findViewById(R.id.btnBack)

        btnFilter =
            findViewById(R.id.btnFilter)

        etSearch =
            findViewById(R.id.etSearch)

        btnDetail1 =
            findViewById(R.id.btnDetail1)

        btnDetail2 =
            findViewById(R.id.btnDetail2)

        btnDetail3 =
            findViewById(R.id.btnDetail3)

        btnDetail4 =
            findViewById(R.id.btnDetail4)

        btnDetail5 =
            findViewById(R.id.btnDetail5)

        card1 =
            findViewById(R.id.card1)

        card2 =
            findViewById(R.id.card2)

        card3 =
            findViewById(R.id.card3)

        card4 =
            findViewById(R.id.card4)

        card5 =
            findViewById(R.id.card5)
    }

    private fun setupBackButton() {

        btnBack.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )

            finish()
        }
    }

    private fun setupNotification() {

        val notifIcon =
            findViewById<FrameLayout>(R.id.notifLayout)

        notifIcon.setOnClickListener {

            val sharedPreferences =
                getSharedPreferences(
                    "RESULT_DATA",
                    MODE_PRIVATE
                )

            val nama =
                sharedPreferences.getString(
                    "nama",
                    "-"
                )

            val seminar =
                sharedPreferences.getString(
                    "seminar",
                    "-"
                )

            AlertDialog.Builder(this)
                .setTitle("Notifikasi")
                .setMessage(
                    "Pendaftaran terbaru:\n\n" +
                            "Nama : $nama\n" +
                            "Seminar : $seminar"
                )
                .setPositiveButton(
                    "OK",
                    null
                )
                .show()
        }
    }

    private fun setupSearch() {

        etSearch.addTextChangedListener(
            object : TextWatcher {

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

                    val keyword =
                        s.toString().lowercase()

                    filterCard(
                        card1,
                        "Seminar IT Modern",
                        keyword
                    )

                    filterCard(
                        card2,
                        "Seminar Bisnis Digital",
                        keyword
                    )

                    filterCard(
                        card3,
                        "Cyber Security Summit",
                        keyword
                    )

                    filterCard(
                        card4,
                        "Seminar Robotik & AI",
                        keyword
                    )

                    filterCard(
                        card5,
                        "Digital Photography",
                        keyword
                    )
                }

                override fun afterTextChanged(
                    s: Editable?
                ) {
                }
            }
        )
    }

    private fun filterCard(
        card: MaterialCardView,
        seminarName: String,
        keyword: String
    ) {

        if (
            seminarName.lowercase()
                .contains(keyword)
        ) {

            card.visibility = View.VISIBLE

        } else {

            card.visibility = View.GONE
        }
    }

    private fun setupFilter() {

        btnFilter.setOnClickListener {

            val pilihan =
                arrayOf(
                    "Semua Seminar",
                    "Sudah Mendaftar",
                    "Belum Mendaftar"
                )

            AlertDialog.Builder(this)
                .setTitle("Filter Seminar")
                .setItems(pilihan) { _, which ->

                    when (which) {

                        0 -> {

                            showAllCard()
                        }

                        1 -> {

                            filterByStatus(true)
                        }

                        2 -> {

                            filterByStatus(false)
                        }
                    }
                }
                .show()
        }
    }

    private fun showAllCard() {

        card1.visibility = View.VISIBLE
        card2.visibility = View.VISIBLE
        card3.visibility = View.VISIBLE
        card4.visibility = View.VISIBLE
        card5.visibility = View.VISIBLE
    }

    private fun filterByStatus(
        isRegistered: Boolean
    ) {

        val sharedPreferences =
            getSharedPreferences(
                "SEMINAR_DATA",
                MODE_PRIVATE
            )

        card1.visibility =
            getVisibility(
                sharedPreferences.getBoolean(
                    "Seminar IT Modern",
                    false
                ),
                isRegistered
            )

        card2.visibility =
            getVisibility(
                sharedPreferences.getBoolean(
                    "Seminar Bisnis Digital",
                    false
                ),
                isRegistered
            )

        card3.visibility =
            getVisibility(
                sharedPreferences.getBoolean(
                    "Cyber Security Summit",
                    false
                ),
                isRegistered
            )

        card4.visibility =
            getVisibility(
                sharedPreferences.getBoolean(
                    "Seminar Robotik & AI",
                    false
                ),
                isRegistered
            )

        card5.visibility =
            getVisibility(
                sharedPreferences.getBoolean(
                    "Digital Photography",
                    false
                ),
                isRegistered
            )
    }

    private fun getVisibility(
        value: Boolean,
        target: Boolean
    ): Int {

        return if (value == target) {

            View.VISIBLE

        } else {

            View.GONE
        }
    }

    private fun setupDetailButton() {

        btnDetail1.setOnClickListener {

            openResult(
                "Seminar IT Modern"
            )
        }

        btnDetail2.setOnClickListener {

            openResult(
                "Seminar Bisnis Digital"
            )
        }

        btnDetail3.setOnClickListener {

            openResult(
                "Cyber Security Summit"
            )
        }

        btnDetail4.setOnClickListener {

            openResult(
                "Seminar Robotik & AI"
            )
        }

        btnDetail5.setOnClickListener {

            openResult(
                "Digital Photography"
            )
        }
    }

    private fun openResult(
        seminarName: String
    ) {

        val sharedPreferences =
            getSharedPreferences(
                "RESULT_DATA",
                MODE_PRIVATE
            )

        val intent =
            Intent(
                this,
                ResultActivity::class.java
            )

        intent.putExtra(
            "nama",
            sharedPreferences.getString(
                "nama",
                "-"
            )
        )

        intent.putExtra(
            "email",
            sharedPreferences.getString(
                "email",
                "-"
            )
        )

        intent.putExtra(
            "nohp",
            sharedPreferences.getString(
                "nohp",
                "-"
            )
        )

        intent.putExtra(
            "gender",
            sharedPreferences.getString(
                "gender",
                "-"
            )
        )

        intent.putExtra(
            "seminar",
            seminarName
        )

        startActivity(intent)
    }
}