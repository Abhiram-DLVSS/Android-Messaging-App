package com.example.message.views

import com.example.message.R
import com.example.message.models.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*

class ChatFromItem(val text: String, val user: User): Item<GroupieViewHolder>()
{
    override fun bind(viewHolder: GroupieViewHolder, position: Int)
    {
        viewHolder.itemView.chat_from_row_textview.text = " From Message ......"
        val uri = user.profileImageUrl
        val targetImageView = viewHolder.itemView.imageView_chat_from_row
        Picasso.get().load(uri).into(targetImageView)
    }

    override fun getLayout(): Int
    {
        return R.layout.chat_from_row
    }
}
class ChatTOItem(val text: String,val user: User): Item<GroupieViewHolder>()
{

    override fun bind(viewHolder: GroupieViewHolder, position: Int)
    {
        viewHolder.itemView.chat_to_row_textview.text = "This is the to row message that is longer"
        val uri = user.profileImageUrl
        val targetImageView = viewHolder.itemView.imageView_chat_to_row
        Picasso.get().load(uri).into(targetImageView)
    }

    override fun getLayout(): Int
    {
        return R.layout.chat_to_row
    }
}
