package com.example.brandat.ui.fragments.registeration.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.brandat.R
import com.example.brandat.databinding.FragmentLoginBinding
import com.example.brandat.ui.fragments.cart.Cart
import com.example.brandat.ui.fragments.cart.CartViewModel
import com.example.brandat.utils.ConnectionUtil
import com.example.brandat.utils.Constants
import com.example.brandat.utils.Constants.Companion.showSnackBar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var email: String
    private lateinit var password: String
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        Paper.init(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvNewAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.loginBtn.setOnClickListener {
            if (ConnectionUtil.isNetworkAvailable(requireContext())) {
                if (checkEmpty()) {
                    binding.loginBtn.visibility = View.GONE
                    binding.prog.visibility = View.VISIBLE
                    loginViewModel.loginCustomer(binding.etEmail.text.toString(), "")
                }

            } else {
                showSnackBar(getString(R.string.no_connection))
                binding.animationView.visibility = View.VISIBLE
            }

        }

        loginViewModel.signInSuccess.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {

                if (it[0].tags == binding.etPass.text.toString()) {
                    Paper.book().write("id", it[0].id)
                    Paper.book().write("email", it[0].email)
                    Paper.book().write("name", it[0].firstName + " " + it[0].lastName)

                    initUser()

                    requireActivity().finish()
                    Toast.makeText(
                        requireContext(),
                        context?.getString(R.string.user_logged_successfully),
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    binding.loginBtn.visibility = View.VISIBLE
                    binding.prog.visibility = View.GONE
                    binding.etPass.error = getString(R.string.not_correct)
                }

            } else {
                Toast.makeText(
                    requireContext(),
                    context?.getString(R.string.this_user_is_not_exist),
                    Toast.LENGTH_SHORT
                ).show()

                binding.loginBtn.visibility = View.VISIBLE
                binding.prog.visibility = View.GONE
                binding.etEmail.setText("")
                binding.etPass.setText("")
            }
        }
    }

    private fun initUser() {
        val cartViewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]

        if (Paper.book().read<String>("email") != null) {
            Constants.user.id = Paper.book().read<Long>("id", 0)?.toLong()!!
            Constants.user.email = Paper.book().read<String>("email").toString()
            Constants.user.firstName = Paper.book().read<String>("name").toString()

            FirebaseDatabase.getInstance()
                .getReference(Constants.user.id.toString())
                .child("cart")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        Paper.book().write<Int>("count", snapshot.children.count())

                        snapshot.children.forEach {
                            val cart : Cart = it.getValue(Cart::class.java)!!
                            cartViewModel.addProductToCart(cart)
                        }

                    }
                    override fun onCancelled(error: DatabaseError) {}
                })
        }
    }

    //=======================================
    private fun checkEmpty(): Boolean {
        email = binding.etEmail.text.toString()
        password = binding.etPass.text.toString()

        if (email.isEmpty()) {
            binding.etEmail.requestFocus()
            binding.etEmail.error = getString(R.string.required)
            return false
        }
        if (password.isEmpty()) {
            binding.etPass.requestFocus()
            binding.etPass.error = getString(R.string.required)
            return false
        }

        return true

    }
}