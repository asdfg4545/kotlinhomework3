package com.example.kotlinhomework2chapter7;

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class MyAdapter(
    context: Context,
    data: List<Item>,
    private val layout: Int
) : ArrayAdapter<Item>(context, layout, data) {

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val view = convertView ?: View.inflate(parent.context, layout, null)
        val item = getItem(position) ?: return view
        val imgPhoto = view.findViewById<ImageView>(R.id.imgPhoto)
        imgPhoto.setImageResource(item.photo)
        val tvMsg = view.findViewById<TextView>(R.id.tvMsg)
        tvMsg.text = if (layout == R.layout.adapter_vertical) {
            item.name
        } else {
            "${item.name}: ${item.price}元"
        }
        return view
    }
}
