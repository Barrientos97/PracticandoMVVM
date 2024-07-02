package com.example.practicandomvvm.presentation.iu.operationProduct

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.practicandomvvm.databinding.ActivityOperationProductBinding
import com.example.practicandomvvm.domain.model.Product
import com.example.practicandomvvm.presentation.common.UiState
import dagger.hilt.android.AndroidEntryPoint
import pe.pcs.libpcs.UtilsCommon
import pe.pcs.libpcs.UtilsMessage

@AndroidEntryPoint
class OperationProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOperationProductBinding
    private val viewModel: OperationProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOperationProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
        initUiState()
    }

    private fun initUiState() {
        // Observar los estados de la UI

        viewModel.uiStateSave.observe(this){
            when (it){
                is UiState.Error -> {
                    binding.progressBar.isVisible = false
                    UtilsMessage.showAlertOk(
                        "ERROR", it.message,this
                    )
                }
                is UiState.Loading -> binding.progressBar.isVisible = true
                is UiState.Success ->{
                    binding.progressBar.isVisible = false

                    UtilsCommon.cleanEditText(binding.root.rootView)
                    binding.etDescripcion.requestFocus()
                    UtilsMessage.showToast(this,"El registro fue grabado correctamente")
                }
            }
        }
    }

    private fun initListener() {
        binding.includeLayout.toolbar.apply {
            title = "Registrar | Editar"
            subtitle = "Producto"

            setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        binding.fabGrabar.setOnClickListener {
            UtilsCommon.hideKeyboard(this,it)

            if(binding.etDescripcion.text.toString().trim().isEmpty()||
                binding.etCodigoBarra.text.toString().trim().isEmpty()||
                binding.etPrecio.text.toString().trim().isEmpty()){
                UtilsMessage.showToast(this, "Todos los datos son obligatorios")
                return@setOnClickListener
            }

            viewModel.save(
                Product().apply {
                    id = viewModel.itemProduct.value?.id?: 0
                    codigoBarra = binding.etCodigoBarra.text.toString().trim()
                    descripcion = binding.etDescripcion.text.toString().trim()
                    precio = binding.etPrecio.text.toString().trim().toDouble()
                }
            )

        }
    }
}