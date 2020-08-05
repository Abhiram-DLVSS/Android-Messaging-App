package com.example.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*
import models.ChatMessage
import models.User

class ChatLogActivity : AppCompatActivity()
{
    companion object{
        val TAG="ChatLog"
    }
    val adapter = GroupAdapter<GroupieViewHolder>()
    var toUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        recylcerview_chat_log.adapter =adapter
       toUser =  intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)

        supportActionBar?.title = toUser?.username

       // setupDummyData()
        listenForMessages()
        send.setOnClickListener {
            Log.d(TAG, "Attempt to send msg")
            performSendMessage()
        }
    }

    private fun listenForMessages(){
        val ref = FirebaseDatabase.getInstance().getReference("/messaages")

        ref.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
       val chatMessage= p0.getValue(ChatMessage::class.java)

                if (chatMessage != null) {
                    Log.d(TAG, chatMessage?.text)

                    if(chatMessage.fromId== FirebaseAuth.getInstance().uid)
                    {
                        val currentUser = LatestMessagesActivity.currentUser ?: return
                        adapter.add(ChatFromItem(chatMessage?.text, currentUser))}
                    else{
                    adapter.add(ChatTOItem(chatMessage?.text,toUser!!))}
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }
        })
    }


    private fun performSendMessage(){
        val text = editText_chat_log.text.toString()
        val fromId = FirebaseAuth.getInstance().uid
        val user =  intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        val toId =user.uid
        if (fromId == null) return
        val reference = FirebaseDatabase.getInstance().getReference("/message").push()

        val chatMessage = ChatMessage(reference.key!!,text,fromId!!, toId, System.currentTimeMillis() /1000)
        reference.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d(TAG, "Saved our chat message: ${reference.key}")
            }
    }
//    private fun setupDummyData(){
//
//        val adapter = GroupAdapter<GroupieViewHolder>()
//
//
////        adapter.add(ChatFromItem("From messageeeeeeeeeeee"))
////        adapter.add(ChatTOItem("to messsssage"))
//
//
//
//        recylcerview_chat_log.adapter =adapter
//    }
}

class ChatFromItem(val text: String, val user: com.example.message.User): Item<GroupieViewHolder>()
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

