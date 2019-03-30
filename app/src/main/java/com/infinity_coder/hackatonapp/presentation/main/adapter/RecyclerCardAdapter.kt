package com.infinity_coder.hackatonapp.presentation.main.adapter

import android.content.Context
import android.view.*
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.widget.RecyclerView
import com.infinity_coder.hackatonapp.R
import com.infinity_coder.hackatonapp.data.db.entity.AdapterCard
import com.infinity_coder.hackatonapp.data.repository.CardRepository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_mycard.view.*


class RecyclerCardAdapter(private val listener: Listener, context: Context) :RecyclerView.Adapter<RecyclerCardAdapter.CardViewHolder>() {
    private val selected = mutableListOf<Int>()
    private val selectedViews = mutableListOf<CardViewHolder>()
    private val repository = CardRepository()

    /*private val mocking = listOf(
        AdapterCard("1", "${context.filesDir}/sample1.png"),
        AdapterCard("2", "${context.filesDir}/sample2.jpg"),
        AdapterCard("3", "${context.filesDir}/sample3.jpg"),
        AdapterCard("4", "${context.filesDir}/sample1.png"),
        AdapterCard("5", "${context.filesDir}/sample2.jpg")
        )*/

    private var cardList :List<AdapterCard> = repository.getAdapterCards()

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        Picasso.get()
            .load(cardList[position].path)
            .noFade()
            .into(holder.image)
        /*val isSelected = !selected.contains(position)
        holder.itemView.isSelected = isSelected
        if (!isSelected)
            holder.itemView.setBackgroundColor(holder.itemView.context.resources.getColor(R.color.colorAccent))
        else
            holder.itemView.setBackgroundColor(holder.itemView.context.resources.getColor(android.R.color.white))*/
    }

    override fun getItemCount(): Int = cardList.size

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
            view.setOnClickListener{listener.onClick(cardList[layoutPosition].number)}
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