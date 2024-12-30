package com.pichs.app.xwidget

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pichs.app.xwidget.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var isSelected = true
        binding.cardImg.setOnClickListener {
            if (isSelected) {
                binding.cardImg.isSelected = false
            } else {
                binding.cardImg.isSelected = true
            }
            isSelected = !isSelected
        }
    }

}