package com.example.practicandomvvm.presentation.iu.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.practicandomvvm.databinding.ActivityMainBinding
import com.example.practicandomvvm.presentation.iu.operationProduct.OperationProductActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
        initUiState()
    }

    private fun initListener() {
        binding.fabNuevo.setOnClickListener{
            startActivity(
                Intent(this, OperationProductActivity::class.java)
            )
        }
    }

    private fun initUiState(){

    }

}