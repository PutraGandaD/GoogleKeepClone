package com.putragandad.noteschallenge4.ui.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.putragandad.noteschallenge4.R
import com.putragandad.noteschallenge4.databinding.FragmentRegisterBinding
import com.putragandad.noteschallenge4.ui.viewmodels.UserViewModel

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var userViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            val name = binding.tfFullname.editText?.text.toString()
            val email = binding.tfEmail.editText?.text.toString()
            val password = binding.tfPassword.editText?.text.toString()
            val passwordCv = binding.tfPasswordcv.editText?.text.toString()

            registerProcess(name, email, password, passwordCv)
        }
    }

    private fun registerProcess(name: String, email: String, password: String, passwordCv: String) {
        if(name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && passwordCv.isNotEmpty()) {
            val registerAuth = userViewModel.register(email, name, password, passwordCv)
            if(registerAuth) {
                Snackbar.make(requireView(), "User registered successfully! Please log in with your credentials to continue", Snackbar.LENGTH_LONG).show()
                findNavController().popBackStack()
            } else {
                Snackbar.make(requireView(), "Passwords do not match. Please make sure your password and password confirmation are the same.", Snackbar.LENGTH_LONG).show()
            }
        } else {
            Snackbar.make(requireView(), "Please fill in all fields to register. All fields are required.", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}