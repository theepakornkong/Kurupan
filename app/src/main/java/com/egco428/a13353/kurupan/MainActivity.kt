package com.egco428.a13353.kurupan

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginButton.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        searchButton.setOnClickListener {
            var intent = Intent(this, DataActivity::class.java)
            intent.putExtra("logincheck",false)
            startActivity(intent)
        }
    }
}
