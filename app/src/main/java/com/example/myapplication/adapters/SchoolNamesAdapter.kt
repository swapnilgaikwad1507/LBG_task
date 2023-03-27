package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.AdapterSchoolNamesBinding
import com.example.myapplication.model.SchoolNamesModelItem

class SchoolNamesAdapter: RecyclerView.Adapter<MainViewHolder>() {

    var schoolNames = mutableListOf<SchoolNamesModelItem>()

    fun setCovidCasesAdapter(schoolNamesList: List<SchoolNamesModelItem>) {
        this.schoolNames = schoolNamesList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = AdapterSchoolNamesBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val schoolName = schoolNames[position]
        holder.binding.schoolName.text = schoolName.school_name
        holder.binding.dbn.text = schoolName.dbn
    }

    override fun getItemCount(): Int {
        return schoolNames.size
    }
}

class MainViewHolder(val binding: AdapterSchoolNamesBinding) : RecyclerView.ViewHolder(binding.root) {

}