package com.example.loginapiapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapiapp.R
import com.example.loginapiapp.model.Pasien

class PasienAdapter(
    private var pasienList: List<Pasien>
) : RecyclerView.Adapter<PasienAdapter.PasienViewHolder>() {

    class PasienViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        val tvTanggalLahir: TextView = itemView.findViewById(R.id.tvTanggalLahir)
        val tvJenisKelamin: TextView = itemView.findViewById(R.id.tvJenisKelamin)
        val tvAlamat: TextView = itemView.findViewById(R.id.tvAlamat)
        val tvTelepon: TextView = itemView.findViewById(R.id.tvTelepon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasienViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pasien, parent, false)
        return PasienViewHolder(view)
    }

    override fun onBindViewHolder(holder: PasienViewHolder, position: Int) {
        val pasien = pasienList[position]

        holder.tvNama.text = pasien.nama
        holder.tvTanggalLahir.text = "Tanggal Lahir: ${pasien.tanggal_lahir}"
        holder.tvJenisKelamin.text = "Jenis Kelamin: ${pasien.jenis_kelamin}"
        holder.tvAlamat.text = "Alamat: ${pasien.alamat}"
        holder.tvTelepon.text = "No Telepon: ${pasien.no_telepon}"
    }

    override fun getItemCount(): Int {
        return pasienList.size
    }

    fun updateData(newData: List<Pasien>) {
        pasienList = newData
        notifyDataSetChanged()
    }
}