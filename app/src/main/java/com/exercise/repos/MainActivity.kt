package com.exercise.repos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.exercise.repos.mvvm.GitViewModel
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val viewModel : GitViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.start()

        viewModel.getData().observe(this) {

            for (i in it){
                println("Names ${i.name}")
            }

        }
    }
}