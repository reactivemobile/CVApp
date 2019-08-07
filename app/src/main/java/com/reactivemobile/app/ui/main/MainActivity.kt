package com.reactivemobile.app.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.transaction
import com.reactivemobile.app.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment = MainFragment.newInstance()
        supportFragmentManager.transaction(allowStateLoss = true) {
            replace(
                R.id.root_container,
                fragment,
                MainFragment.TAG
            )
        }
    }
}
