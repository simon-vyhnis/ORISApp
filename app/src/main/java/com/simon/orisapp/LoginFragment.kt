package com.simon.orisapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class LoginFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root =  inflater.inflate(R.layout.fragment_login, container, false)
        val registration = root.findViewById<EditText>(R.id.input_registration)
        val username = root.findViewById<EditText>(R.id.input_username)
        val password = root.findViewById<EditText>(R.id.input_password)
        val btn = root.findViewById<Button>(R.id.button)
        val viewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        btn.setOnClickListener {
            val registrationText = registration.text.toString()
            val usernameText = username.text.toString()
            val passwordText = password.text.toString()

            if(registrationText.isBlank() || usernameText.isBlank() || passwordText.isBlank()) {
                Toast.makeText(context, "Vyplňte prosím údaje", Toast.LENGTH_SHORT).show()
            }else {
                viewModel.savePersonalInfo(registrationText, usernameText, passwordText, "")
            }
        }
        return root
    }
}