package com.example.practicandomvvm.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.practicandomvvm.databinding.ItemsProductBinding
import com.example.practicandomvvm.domain.model.Product
import pe.pcs.libpcs.UtilsCommon

class ProductAdapter(
    private val iOnClickListener: IOnClickListener
): ListAdapter<Product, ProductAdapter.BindViewHolder>(DiffCalback) {

    interface IOnClickListener {
        fun clickEdit(mode: Product)
        fun clickDelete(mode: Product)
    }

    private object DiffCalback : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }

    inner class BindViewHolder(
        private val binding: ItemsProductBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(model: Product){
            binding.tvTitulo.text = model.descripcion
            binding.tvCodigoBarra.text = model.codigoBarra
            binding.tvPrecio.text = UtilsCommon.formatFromDoubleToString(model.precio)
            binding.ibEditar.setOnClickListener {
                iOnClickListener.clickEdit(model)
            }
            binding.ibEliminar.setOnClickListener {
                iOnClickListener.clickDelete(model)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindViewHolder {
        return BindViewHolder(
            ItemsProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BindViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}