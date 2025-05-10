package com.example.mychat

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ChatListActivity : AppCompatActivity() {
    // Data chat awal
    private val initialChats = listOf(
        Chat("Admin", "Selamat datang di My Chat!", "10:00"),
        Chat("Support", "Ada yang bisa kami bantu?", "09:30"),
        Chat("Kadal", "Hai, apa kabar?", "08:15")
    )

    private val chatList = initialChats.toMutableList()
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)

        // menangkap data dari registrasi
        intent.getParcelableExtra<Chat>(RegisterActivity.EXTRA_NEW_CHAT)?.let { newChat ->
            chatList.add(0, newChat) // Tambahkan di urutan teratas
            Toast.makeText(this, "${newChat.name} telah bergabung!", Toast.LENGTH_SHORT).show()
        }

        // Inisialisasi ListView dan Adapter
        val chatListView = findViewById<ListView>(R.id.lv_chats)
        chatAdapter = ChatAdapter(this, chatList)
        chatListView.adapter = chatAdapter

        // Handle klik pada item chat
        chatListView.setOnItemClickListener { _, _, position, _ ->
            showChatDetail(chatList[position])
        }
    }

    private fun showChatDetail(chat: Chat) {
        Toast.makeText(this,
            "Chat dengan: ${chat.name}\nPesan terakhir: ${chat.lastMessage}",
            Toast.LENGTH_LONG).show()

    }
}
@Parcelize
data class Chat(
    val name: String,
    val lastMessage: String,
    val time: String
) : Parcelable {
    //    construktor untuk melihat data di parsel
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(lastMessage)
        parcel.writeString(time)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Chat> {
        override fun createFromParcel(parcel: Parcel): Chat = Chat(parcel)
        override fun newArray(size: Int): Array<Chat?> = arrayOfNulls(size)
    }
}

class ChatAdapter(
    private val context: android.content.Context,
    private val chats: List<Chat>
) : android.widget.ArrayAdapter<Chat>(context, 0, chats) {
    override fun getView(
        position: Int,
        convertView: android.view.View?,
        parent: android.view.ViewGroup
    ): android.view.View {
        val view = convertView ?: android.view.LayoutInflater.from(context)
            .inflate(R.layout.item_chat, parent, false)

        val chat = chats[position]

        view.findViewById<android.widget.TextView>(R.id.tv_name).text = chat.name
        view.findViewById<android.widget.TextView>(R.id.tv_message).text = chat.lastMessage
        view.findViewById<android.widget.TextView>(R.id.tv_time).text = chat.time

        return view
    }
}
