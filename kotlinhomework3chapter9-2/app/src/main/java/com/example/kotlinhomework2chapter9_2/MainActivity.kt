package com.example.kotlinhomework2chapter9_2

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    private lateinit var btnCalculate: Button
    private lateinit var edH: EditText
    private lateinit var edW: EditText
    private lateinit var edAge: EditText
    private lateinit var tvW: TextView
    private lateinit var tvF: TextView
    private lateinit var tvB: TextView
    private lateinit var tvP: TextView
    private lateinit var prBar: ProgressBar
    private lateinit var llPr: LinearLayout
    private lateinit var btnBoy: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnCalculate = findViewById(R.id.btnCalculate)
        edH = findViewById(R.id.edHeight)
        edW = findViewById(R.id.edWeight)
        edAge = findViewById(R.id.edAge)
        tvW = findViewById(R.id.tvWeightResult)
        tvF = findViewById(R.id.tvFatResult)
        tvB = findViewById(R.id.tvBmiResult)
        tvP = findViewById(R.id.tvProgress)
        prBar = findViewById(R.id.progressBar)
        llPr = findViewById(R.id.llProgress)
        btnBoy = findViewById(R.id.btnBoy)

        btnCalculate.setOnClickListener {
            when {
                edH.text.isEmpty() -> showToast("請輸入身高")
                edW.text.isEmpty() -> showToast("請輸入體重")
                edAge.text.isEmpty() -> showToast("請輸入年齡")
                else -> runThread()
            }
        }
    }

    private fun showToast(msg: String) =
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    private fun runThread() {
        tvW.text = "標準體重\n無"
        tvF.text = "體脂肪\n無"
        tvB.text = "BMI\n無"
        prBar.progress = 0
        tvP.text = "0%"
        llPr.visibility = View.VISIBLE

        Thread {
            var progress = 0
            while (progress < 100) {
                try {
                    Thread.sleep(50)
                } catch (ignored: InterruptedException) {
                }
                progress++
                runOnUiThread {
                    prBar.progress = progress
                    tvP.text = "$progress%"
                }
            }

            val height = edH.text.toString().toDouble()
            val weight = edW.text.toString().toDouble()
            val age = edAge.text.toString().toDouble()
            val bmi = weight / ((height / 100).pow(2))
            val (standWeight, bodyFat) = if (btnBoy.isChecked) {
                Pair((height - 80) * 0.7, 1.39 * bmi + 0.16 * age - 19.34)
            } else {
                Pair((height - 70) * 0.6, 1.39 * bmi + 0.16 * age - 9)
            }
            runOnUiThread {
                llPr.visibility = View.GONE
                tvW.text = "標準體重 \n${String.format("%.2f", standWeight)}"
                tvF.text = "體脂肪 \n${String.format("%.2f", bodyFat)}"
                tvB.text = "BMI \n${String.format("%.2f", bmi)}"
            }
        }.start()
    }
}