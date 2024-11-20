package com.example.kotlinhomework2chapter9_1

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var prRabbit = 0
    private var prTurtle = 0
    private lateinit var btnStart: Button
    private lateinit var sbRabbit: SeekBar
    private lateinit var sbTurtle: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnStart = findViewById(R.id.btnStart)
        sbRabbit = findViewById(R.id.sbRabbit)
        sbTurtle = findViewById(R.id.sbTurtle)
        btnStart.setOnClickListener {
            btnStart.isEnabled = false
            prRabbit = 0
            prTurtle = 0
            sbRabbit.progress = 0
            sbTurtle.progress = 0
            runRabbit()
            runTurtle()
        }
    }

    private fun showToast(msg: String) =
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    private val handler = Handler(Looper.getMainLooper()) { msg ->
        if (msg.what == 1) {
            sbRabbit.progress = prRabbit
            if (prRabbit >= 100 && prTurtle < 100) {
                showToast("兔子勝利")
                btnStart.isEnabled = true
            }
        } else if (msg.what == 2) {
            sbTurtle.progress = prTurtle
            if (prTurtle >= 100 && prRabbit < 100) {
                showToast("烏龜勝利")
                btnStart.isEnabled = true
            }
        }
        true
    }

    private fun runRabbit() {
        Thread {
            val sleepProbability = arrayOf(true, true, false)
            while (prRabbit < 100 && prTurtle < 100) {
                try {
                    Thread.sleep(100)
                    if (sleepProbability.random())
                        Thread.sleep(300)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                prRabbit += 3//可以改兔子的速度

                val msg = Message()
                msg.what = 1
                handler.sendMessage(msg)
            }
        }.start()
    }

    private fun runTurtle() {
        Thread {
            while (prTurtle < 100 && prRabbit < 100) {
                try {
                    Thread.sleep(100)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                prTurtle += 1//烏龜的速度

                val msg = Message()
                msg.what = 2
                handler.sendMessage(msg)
            }
        }.start()
    }
}