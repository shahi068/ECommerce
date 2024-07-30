package com.adamco.ecommerceapp.model.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.adamco.ecommerceapp.databinding.PaymentItemBinding
import com.adamco.ecommerceapp.model.data.payment_data.PaymentViewModel

class PaymentPageAdapter(
    private val paymentMethods: List<String>,
    viewModelStoreOwner: ViewModelStoreOwner,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<PaymentPageAdapter.PaymentPageViewHolder>() {
    private var selectedPosition = -1
    private val viewModel: PaymentViewModel = ViewModelProvider(viewModelStoreOwner)[PaymentViewModel::class.java]

    init {
        viewModel.selectedPosition.observe(lifecycleOwner) { position ->
            selectedPosition = position ?: -1
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentPageViewHolder {
        val binding = PaymentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PaymentPageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PaymentPageViewHolder, position: Int) {
        holder.bind(paymentMethods[position], position)
    }

    override fun getItemCount() = paymentMethods.size

    inner class PaymentPageViewHolder(private val binding: PaymentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(paymentOption: String, position: Int) {
            with(binding) {
                txtPaymentMethod.text = paymentOption
                checkBox.setOnCheckedChangeListener(null)
                checkBox.isChecked = position == selectedPosition

                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        viewModel.selectFinalPayment(paymentOption, adapterPosition)
                        Log.d("PaymentPageAdapter", "Selected payment method: $paymentOption at position $position")
                    }
                }
            }
        }
    }
}
