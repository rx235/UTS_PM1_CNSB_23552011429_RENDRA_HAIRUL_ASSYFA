package com.example.mychat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    companion object {
        val EXTRA_NEW_CHAT = "extra_new_chat"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etName = findViewById<EditText>(R.id.et_name)
        val btnRegister = findViewById<Button>(R.id.btn_register)

        btnRegister.setOnClickListener {
            val name = etName.text.toString().trim()

            if (name.isEmpty()) {
                etName.error = "Nama tidak boleh kosong"
                return@setOnClickListener
            }

            // Buat data chat baru
            val newChat = Chat(
                name = name,
                lastMessage = "Bergabung dengan Obrolin!",
                time = "Baru saja"
            )

            // Kirim data ke ChatListActivity
            val intent = Intent(this, ChatListActivity::class.java).apply {
                putExtra(EXTRA_NEW_CHAT, newChat)
            }
            startActivity(intent)
            finish()
        }
    }
}
