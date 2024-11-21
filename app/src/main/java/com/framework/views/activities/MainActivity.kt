package com.framework.views.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.data.network.model.ParseService
import com.data.repositories.MainRepository
import com.diegoalfaro.myapplication.databinding.ActivityMainBinding
import com.domain.MainRequirement
import com.framework.adapter.MainAdaptador
import com.framework.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MainAdaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inicializarVinculos()
        inicializarViewModel()
        inicializarRecyclerView()
        inicializarObservadores()

        viewModel.recuperarEventos()
    }

    private fun inicializarVinculos() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun inicializarViewModel() {
        val service = ParseService()
        val repository = MainRepository(service)
        val requirement = MainRequirement(repository)
        viewModel = MainViewModel(requirement)
    }

    private fun inicializarRecyclerView() {
        adapter = MainAdaptador(emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun inicializarObservadores() {
        viewModel.events.observe(this) { events ->
            if (events != null) {
                adapter.updateData(events)
            }
        }

        viewModel.error.observe(this) { errorMessage ->
            Log.d("ErrorActivity", errorMessage.toString())
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
