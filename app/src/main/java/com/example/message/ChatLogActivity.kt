package com.example.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_chat_log.*


class ChatLogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        supportActionBar?.title= "Chat log"


        val adapter = GroupAdapter<RecyclerView.ViewHolder>()

        adapter.add(???)

        recylcerview_chat_log.adapter =adapter

    }
}

class ChatItem: Item<RecyclerView.ViewHolder() {
    override fun bind(viewHolder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getLayout(): Int {

    }
}