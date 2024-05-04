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
import com.putragandad.noteschallenge4.databinding.FragmentLoginBinding
import com.putragandad.noteschallenge4.ui.viewmodels.UserViewModel

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var userViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

        val loginStatus = userViewModel.checkLogin()
        if(loginStatus) {
            findNavController().navigate(R.id.action_loginFragment_to_noteListFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val email = binding.tfEmail.editText?.text.toString()
            val password = binding.tfPassword.editText?.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()) {
                val loginAuth = userViewModel.login(email, password)
                if(loginAuth) {
                    findNavController().navigate(R.id.action_loginFragment_to_noteListFragment)
                    Snackbar.make(it, "Login successful! You're signed in as ${userViewModel.getEmail()}", Snackbar.LENGTH_LONG)
                        .show()
                } else {
                    Snackbar.make(it, "Invalid email or password. Try Again.", Snackbar.LENGTH_LONG)
                        .show()
                }
            } else {
                Snackbar.make(it, "Email or password can't be empty.", Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        binding.btnRegisterPage.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}