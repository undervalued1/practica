package com.example.ychebpr2.ui.dashboard

import Product
import adapters.ProductAdapter
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.ychebpr2.databinding.FragmentProductsBinding


class ProductsFragment : Fragment() {

    private lateinit var binding: FragmentProductsBinding
    private lateinit var context: Context
    private lateinit var adapter: ProductAdapter
    private lateinit var products: List<Product>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProductsBinding.inflate(inflater, container, false)
        context = this.requireContext()
        adapter = ProductAdapter.create(context)

        products = listOf(
            Product(1, "Один"),
            Product(2, "Два"),
            Product(3, "Три"),
            Product(4, "Четыре"),
            Product(5, "Пять"),
            Product(6, "Шесть"),
            Product(7, "Семь"),
            Product(8, "Восемь"),
            Product(9, "Девять"),
            Product(10, "Десять"))

        binding.rvProducts.layoutManager = LinearLayoutManager(context)
        binding.rvProducts.adapter=adapter
        adapter.refreshProducts(products)



        val dashboardViewModel=ViewModelProvider(this).get(ProdcutsViewModel::class.java)
        return binding.root
    }
}

