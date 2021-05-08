package ru.nightgoat.needdrummer.activity

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import ru.nightgoat.needdrummer.R
import ru.nightgoat.needdrummer.core.CoreActivity
import ru.nightgoat.needdrummer.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : CoreActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}