package com.egco428.a13353.kurupan

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_data.*

class DataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val data = intent.extras
        var check: Boolean = data.getBoolean("logincheck")

            imageView2.setOnClickListener {
                if (check == true){Log.d("hello","login")
                    Toast.makeText(this, "Login Session", Toast.LENGTH_SHORT).show()
                }
                else{Log.d("hello","not login")
                    Toast.makeText(this, "Guest Session", Toast.LENGTH_SHORT).show()
                }
            }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.pagemenu,menu)
        return true
    }
}
