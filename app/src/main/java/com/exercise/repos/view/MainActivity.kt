package com.exercise.repos.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.exercise.repos.R
import com.exercise.repos.databinding.ActivityMainBinding
import com.exercise.repos.business.dispatcher.DataSource
import com.exercise.repos.viewModel.GitViewModel
import com.exercise.repos.business.utils.SharedPrefs
import com.exercise.repos.business.utils.State
import com.exercise.repos.view.adapters.GitReposeAdapter
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val viewModel: GitViewModel by inject()
    private lateinit var adapter: GitReposeAdapter
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (SharedPrefs.getPrefs(this).isNightMode(false)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        adapter = GitReposeAdapter()
        binding.recyclerView.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = adapter
        }
        binding.retryButton.setOnClickListener {
            viewModel.start(DataSource.REMOTE)
        }
        binding.uiMode.setOnClickListener {

            if (SharedPrefs.getPrefs(this).isNightMode(false)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Toast.makeText(this, "Light Mode Enabled", Toast.LENGTH_SHORT).show()
                SharedPrefs.getPrefs(this).setNightMode(false)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                Toast.makeText(this, "Dark Mode Enabled", Toast.LENGTH_SHORT).show()
                SharedPrefs.getPrefs(this).setNightMode(true)
            }

        }
        viewModel.getState().observe(this) {
            when (it) {
                is State.Error -> {
                    Glide.with(binding.lottieAnimation).load(R.drawable.error_animation).into(binding.lottieAnimation)
                    binding.shimmerLayout.visibility = View.GONE
                    binding.errorGroup.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
                is State.Loading -> {
                    binding.shimmerLayout.visibility = View.VISIBLE
                    binding.errorGroup.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                }
                else -> {

                }
            }
        }
        viewModel.getData().observe(this) {
            binding.shimmerLayout.visibility = View.GONE
            binding.errorGroup.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            adapter.updateList(it)
        }

        binding.retryButton.setOnClickListener {
            viewModel.start(DataSource.REMOTE)
        }
        //below code will call the api once in 12 hours and during this time will get data from local db.
        val currentTime = System.currentTimeMillis()
        val previousTime = SharedPrefs.getPrefs(this).getValue("time_passed", currentTime)
        val dif = (currentTime / (60 * 60 * 1000 )) - (previousTime/(60 * 60 * 1000 ))
        if (dif >= 12 || SharedPrefs.getPrefs(this).isFirstTime()){
            SharedPrefs.getPrefs(this).setValue("time_passed", currentTime)
            viewModel.start(DataSource.REMOTE)
        } else {
            viewModel.start(DataSource.LOCAL)
        }

    }

}