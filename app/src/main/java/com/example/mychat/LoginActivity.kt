package com.example.mychat

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.view.View
import android.os.Handler
import android.os.Looper


class LoginActivity : AppCompatActivity() {
    //    mendeklarasikan  variabel komponen UI yang akan digunakan
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnToRegister: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inisialisasi view
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)
        btnToRegister = findViewById(R.id.btn_to_register)
        progressBar = findViewById(R.id.progress_bar)

        // Memantau perubahan teks pada EditText
        val textWatcher = object : android.text.TextWatcher {
            override fun afterTextChanged(s: android.text.Editable?) = checkFields()
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
        etEmail.addTextChangedListener(textWatcher)
        etPassword.addTextChangedListener(textWatcher)

        btnLogin.setOnClickListener { performLogin() }
        btnToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // Disable tombol awal
        btnLogin.isEnabled = false
    }

    private fun checkFields() {
        val email = etEmail.text.toString().trim() //mengambil input teks
        val password = etPassword.text.toString().trim()
        btnLogin.isEnabled = email.isNotEmpty() && password.isNotEmpty()
    }
    //from login
    private fun performLogin() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        // Validasi tambahan (redundant, tapi untuk safety)
        if (email.isEmpty() || password.isEmpty()) return
//        menampilkan progresBar
        progressBar.visibility = View.VISIBLE
//        mennaktifkan tombol login
        btnLogin.isEnabled = false

        Handler(Looper.getMainLooper()).postDelayed({  //Menjalankan kode UI dan menunda eksekusi selama 1,5 detik
            progressBar.visibility = View.GONE
            btnLogin.isEnabled = true
            startActivity(Intent(this, ChatListActivity::class.java))
            finish()
        }, 1500)
    }
}