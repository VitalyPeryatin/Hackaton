package com.infinity_coder.hackatonapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_mycard.view.*
import java.io.File


class RecyclerCardAdapter(private val listener: Listener, context: Context) :RecyclerView.Adapter<RecyclerCardAdapter.CardViewHolder>() {
    private val mocking = listOf(
        AdapterCard("1", "${context.filesDir}/sample1.png"),
        AdapterCard("2", "${context.filesDir}/sample2.jpg"),
        AdapterCard("3", "${context.filesDir}/sample3.jpg"),
        AdapterCard("4", "${context.filesDir}/sample1.png"),
        AdapterCard("5", "${context.filesDir}/sample2.jpg")
        )

    private var cardList :List<AdapterCard> = mocking

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        Picasso.get()
            .load(cardList[position].path)
            .noFade()
            .into(holder.image)

    }

    override fun getItemCount(): Int = cardList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mycard, parent, false)
        return CardViewHolder(view, listener)
    }


    interface Listener{
        fun onClick(id: String)
    }

    inner class CardViewHolder(view: View, private val listener: Listener) : RecyclerView.ViewHolder(view){
        val image = view.imageCard!!

        init{
            view.setOnClickListener{listener.onClick(cardList[layoutPosition].id)}

        }

    }
}