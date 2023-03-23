package com.tsu.mobile_lab

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tsu.mobile_lab.databinding.ActivitySignUpBinding


private lateinit var binding: ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.backButton?.setOnClickListener{
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
        }

        binding.loginButton?.setOnClickListener{
            val intent = Intent(this, MainAppActivity::class.java)
            startActivity(intent)
        }

        binding.signUpButton.setOnClickListener {
            if (binding.nameEditText.text.toString().equals("")) {
                showAlertDialog("Name")

            }
            else if (binding.emailEditText.text.toString().equals("")) {
                showAlertDialog("Email")
            }

            else if (binding.passwordEditText.text.toString().equals("")) {
                showAlertDialog("Password")
            }

            else {
                val intent = Intent(this, com.tsu.mobile_lab.MainAppActivity::class.java)
                startActivity(intent)
            }

        }
    }
    fun showAlertDialog(fieldName: String) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Alert")
        dialogBuilder.setMessage("Field " + fieldName + " is empty!")
        dialogBuilder.setNeutralButton("Ok", { dialogInterface: DialogInterface, i: Int ->

        })
        dialogBuilder.show()
    }
}