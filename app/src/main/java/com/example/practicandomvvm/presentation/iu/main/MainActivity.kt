package com.example.practicandomvvm.presentation.iu.main

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicandomvvm.R
import com.example.practicandomvvm.databinding.ActivityMainBinding
import com.example.practicandomvvm.domain.model.Product
import com.example.practicandomvvm.presentation.adapter.ProductAdapter
import com.example.practicandomvvm.presentation.common.UiState
import com.example.practicandomvvm.presentation.iu.operationProduct.OperationProductActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
        viewModel.getListApi(binding.etBuscar.text.toString().trim())
    }

    override fun onResume() {
        super.onResume()

        if (!existeCambio) return
        viewModel.getListApi("")
        existeCambio = false
    }

    private fun initListener() {

        binding.includeLayout.toolbar.apply {
            title = "Patron MVVM"
            subtitle = "Clean Arquitecture"
            navigationIcon = AppCompatResources.getDrawable(
                this@MainActivity,
                R.drawable.ic_apps
            )
        }

        binding.includeLayout.ibSincronizar.setOnClickListener {
            viewModel.getListApi(binding.etBuscar.text.toString().trim())
        }

        binding.rvLista.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = ProductAdapter(this@MainActivity)
        }

        binding.tilBuscar.setEndIconOnClickListener{
            viewModel.getListApi(binding.etBuscar.text.toString().trim())
        }

       /* binding.etBuscar.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                viewModel.getListApi(p0.toString().trim())
            }

        })*/

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


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiStateDelete.collect{
                    when(it){
                        is UiState.Error -> {
                            binding.progressBar.isVisible = false
                            UtilsMessage.showAlertOk(
                                "ERROR", it.message, this@MainActivity
                            )
                            viewModel.resetUiStateDelete()
                        }
                        is UiState.Loading -> binding.progressBar.isVisible = true
                        is UiState.Success -> {
                            binding.progressBar.isVisible = false
                            UtilsMessage.showToast(
                                this@MainActivity,
                                "El registro fue eliminado correctamente"
                            )
                            viewModel.resetUiStateDelete()
                            viewModel.getListApi(binding.etBuscar.text.toString().trim())
                        }
                        null -> Unit
                    }
                }
            }
        }

    }

    override fun clickEdit(mode: Product) {
        val intent = Intent(this, OperationProductActivity::class.java)
        intent.putExtra("id", mode.id)
        startActivity(intent)
    }

    override fun clickDelete(mode: Product) {
        MaterialAlertDialogBuilder(this).apply {
            setCancelable(false)
            setTitle("Eliminar")
            setMessage("¿Esta seguro de eliminar el registro: ${mode.descripcion}?")
            setPositiveButton("Aceptar") { dialog, _ ->
                viewModel.delete(mode)
                dialog.dismiss()
            }
            setNegativeButton("Cancelar") { dialog, _ ->
                dialog.cancel()
            }
        }.create().show()
    }

    companion object{
        var existeCambio = false
    }

}