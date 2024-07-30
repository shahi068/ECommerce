package com.adamco.ecommerceapp.model.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.adamco.ecommerceapp.databinding.AddressItemBinding
import com.adamco.ecommerceapp.model.data.delivery_data.Address
import com.adamco.ecommerceapp.model.data.delivery_data.DeliveryViewModel

class DeliveryPageAdapter(
    private val addresses: List<Address>,
    viewModelStoreOwner: ViewModelStoreOwner,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<DeliveryPageAdapter.DeliveryPageViewHolder>() {

    private val viewModel: DeliveryViewModel = ViewModelProvider(viewModelStoreOwner)[DeliveryViewModel::class.java]
    private var selectedPosition = -1 // No selection by default

    init {
        viewModel.selectedPosition.observe(lifecycleOwner) { position ->
            selectedPosition = position ?: -1
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryPageViewHolder {
        val binding = AddressItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeliveryPageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeliveryPageViewHolder, position: Int) {
        holder.bind(addresses[position], position)
    }

    override fun getItemCount() = addresses.size

    inner class DeliveryPageViewHolder(private val binding: AddressItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(address: Address, position: Int) {
            with(binding) {
                txtLocation.text = address.title
                txtAddress.text = address.address
                checkBox.setOnCheckedChangeListener(null) // Clear any previous listeners to prevent unwanted behavior
                checkBox.isChecked = position == selectedPosition

                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        viewModel.selectFinalAddress(address, adapterPosition)
                        Log.d("DeliveryPageAdapter", "Selected address: $address at position $position")
                    }
                }
            }
        }
    }
}
