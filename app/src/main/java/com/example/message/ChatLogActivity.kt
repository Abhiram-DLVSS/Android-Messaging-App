package com.example.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_chat_log.*


class ChatLogActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        supportActionBar?.title= "Chat log"

        val username= intent.getStringArrayExtra(NewMessageActivity.USER_KEY)
        supportActionBar?.title = username

        val adapter = GroupAdapter<GroupieViewHolder>()


        adapter.add(ChatFromItem())
        adapter.add(ChatTOItem())
        adapter.add(ChatFromItem())
        adapter.add(ChatTOItem())
        adapter.add(ChatFromItem())
        adapter.add(ChatTOItem())
        adapter.add(ChatFromItem())
        adapter.add(ChatTOItem())


        recylcerview_chat_log.adapter =adapter
    }
}

class ChatFromItem: Item<GroupieViewHolder>()
{
    override fun bind(viewHolder: GroupieViewHolder, position: Int)
    {

    }

    override fun getLayout(): Int
    {
    return R.layout.chat_from_row
    }
}
class ChatTOItem: Item<GroupieViewHolder>()
{
    override fun bind(viewHolder: GroupieViewHolder, position: Int)
    {

    }

    override fun getLayout(): Int
    {
        return R.layout.chat_to_row
    }
}