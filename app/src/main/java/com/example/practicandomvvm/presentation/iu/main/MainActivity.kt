package com.example.practicandomvvm.presentation.iu.main

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicandomvvm.databinding.ActivityMainBinding
import com.example.practicandomvvm.domain.model.Product
import com.example.practicandomvvm.presentation.adapter.ProductAdapter
import com.example.practicandomvvm.presentation.common.UiState
import com.example.practicandomvvm.presentation.iu.operationProduct.OperationProductActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import pe.pcs.libpcs.UtilsMessage

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ProductAdapter.IOnClickListener {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: mainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
        initUiState()

        viewModel.getList(binding.etBuscar.text.toString().trim())
    }

    private fun initListener() {

        binding.rvLista.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = ProductAdapter(this@MainActivity)
        }

        binding.etBuscar.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                viewModel.getList(p0.toString().trim())
            }

        })

        binding.fabNuevo.setOnClickListener{
            startActivity(
                Intent(this, OperationProductActivity::class.java)
            )
        }
    }

    private fun initUiState(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.listProduct.collect{
                    (binding.rvLista.adapter as ProductAdapter).submitList(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiStateList.collect{
                    when(it){
                        is UiState.Error -> {
                            binding.progressBar.isVisible = false
                            UtilsMessage.showAlertOk(
                                "ERROR", it.message, this@MainActivity
                            )
                        }
                        is UiState.Loading -> binding.progressBar.isVisible = true
                        is UiState.Success -> {
                            binding.progressBar.isVisible = false
                        }
                        null -> Unit
                    }
                }
            }
        }
    }

    override fun clickEdit(mode: Product) {

    }

    override fun clickDelete(mode: Product) {

    }

}