package com.example.loginapiapp

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapiapp.adapter.PasienAdapter
import com.example.loginapiapp.network.RetrofitClient
import kotlinx.coroutines.launch

class PasienActivity : AppCompatActivity() {

    private lateinit var tvUserName: TextView
    private lateinit var rvPasien: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: PasienAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pasien)

        tvUserName = findViewById(R.id.tvUserName)
        rvPasien = findViewById(R.id.rvPasien)
        progressBar = findViewById(R.id.progressBarPasien)

        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
        val userName = prefs.getString("user_name", "User")
        val token = prefs.getString("token", "") ?: ""

        tvUserName.text = "Halo, $userName"

        adapter = PasienAdapter(emptyList())
        rvPasien.layoutManager = LinearLayoutManager(this)
        rvPasien.adapter = adapter

        if (token.isEmpty()) {
            Toast.makeText(this, "Token tidak ditemukan, silakan login ulang", Toast.LENGTH_SHORT).show()
            return
        }

        getDataPasien(token)
    }

    private fun getDataPasien(token: String) {
        lifecycleScope.launch {
            showLoading(true)

            try {
                val response = RetrofitClient.apiService.getPasien("Bearer $token")

                if (response.isSuccessful && response.body()?.success == true) {
                    val pasienList = response.body()?.data ?: emptyList()
                    adapter.updateData(pasienList)
                } else {
                    Toast.makeText(
                        this@PasienActivity,
                        response.body()?.message ?: "Gagal mengambil data pasien",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@PasienActivity,
                    "Error: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            } finally {
                showLoading(false)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}