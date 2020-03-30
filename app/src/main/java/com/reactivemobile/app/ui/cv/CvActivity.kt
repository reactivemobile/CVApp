package com.reactivemobile.app.ui.cv

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commitNow
import com.reactivemobile.app.R

class CvActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment = CvFragment.newInstance()
        supportFragmentManager.commitNow {
            replace(
                R.id.root_container,
                fragment,
                CvFragment.TAG
            )
        }
    }
}
