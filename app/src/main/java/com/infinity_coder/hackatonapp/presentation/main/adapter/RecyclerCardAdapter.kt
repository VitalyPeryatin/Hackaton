package com.infinity_coder.hackatonapp.presentation.main.adapter

import android.view.*
import androidx.appcompat.view.ActionMode
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.infinity_coder.hackatonapp.R
import com.infinity_coder.hackatonapp.data.db.entity.AbstractCard
import com.infinity_coder.hackatonapp.presentation.main.view.MainActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_mycard.view.*
import java.io.File


class RecyclerCardAdapter(private val listener: Listener, context: MainActivity, var cardList : LiveData<List<AbstractCard>>) :RecyclerView.Adapter<RecyclerCardAdapter.CardViewHolder>() {
    private val selected = mutableListOf<Int>()
    private val selectedViews = mutableListOf<CardViewHolder>()
    var items : List<AbstractCard> = listOf()

    init {
        cardList.observe(context, Observer {
            items = it
            notifyDataSetChanged()
        })
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        if (items[position].path != "") {
            Picasso.get()
                .load(File(items[position].path))
                .noFade()
                .into(holder.image)
        }
        /*val isSelected = !selected.contains(position)
        holder.itemView.isSelected = isSelected
        if (!isSelected)
            holder.itemView.setBackgroundColor(holder.itemView.context.resources.getColor(R.color.colorAccent))
        else
            holder.itemView.setBackgroundColor(holder.itemView.context.resources.getColor(android.R.color.white))*/
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mycard, parent, false)
        return CardViewHolder(view, listener)
    }


    interface Listener{
        fun onClick(number: String)
    }

    inner class CardViewHolder(view: View, private val listener: Listener) : RecyclerView.ViewHolder(view){
        val image = view.imageCard!!
        init{
            view.setOnClickListener{listener.onClick(items[layoutPosition].number)}
            /*view.setOnLongClickListener{
                notifyItemChanged(layoutPosition)
                if (!selected.contains(layoutPosition)) {
                    selected.add(layoutPosition)
                }
                else{
                    selected.remove(layoutPosition)
                }
            }*/
        }

    }

    /**
     * Работа с контекстным меню действий, здесь реализуется удаление элементов
     */
    private var currentActionMode: ActionMode? = null
    private val modeCallback: ActionMode.Callback = object: ActionMode.Callback{
        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            when(item?.itemId){
                R.id.menu_remove -> {
                    for(position in selected) {
                        selected.add(position)
                    }
                    mode?.finish()
                }
                else -> return false
            }
            return true
        }

        /**
         * Инициализирует контекстное меню и присваивает заголовку "1" - кол-во выбранных элементов
         */
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            currentActionMode = mode
           // mode?.title = "1"
            //mode?.menuInflater?.inflate(R.menu.menu_action_chapters, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        /**
         * Убирает контестное меню и очищает выбранные элементы
         */
        override fun onDestroyActionMode(mode: ActionMode?) {
            currentActionMode = null
            selected.clear()
            for(view in selectedViews) {
                view.image.setBackgroundColor(view.itemView.context.resources.getColor(android.R.color.white))
                //view.visibility = GONE
                //view.ivMaskSelectedDone.visibility = GONE
            }
            selected.clear()
        }
    }

}