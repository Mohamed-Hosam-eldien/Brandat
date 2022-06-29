package com.example.brandat.ui.fragments.registeration.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.brandat.R
import com.example.brandat.databinding.FragmentRegisterBinding
import com.example.brandat.models.Customer
import com.example.brandat.models.CustomerRegisterModel
import com.example.brandat.models.DefaultAddress
import com.example.brandat.ui.fragments.cart.Cart
import com.example.brandat.ui.fragments.cart.CartViewModel
import com.example.brandat.utils.ConnectionUtil
import com.example.brandat.utils.Constants
import com.example.brandat.utils.Constants.Companion.EMAIL_PATTERN
import com.example.brandat.utils.Constants.Companion.showSnackBar
import com.example.brandat.utils.Constants.Companion.user
import com.example.brandat.utils.observeOnce
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var email: String
    private lateinit var name: String
    private lateinit var lastName: String
    private lateinit var pass: String
    private lateinit var confirmPass: String

    private val registerViewModel: RegisterViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_register, null)
        binding = FragmentRegisterBinding.bind(view)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerBtn.setOnClickListener {
            if (ConnectionUtil.isNetworkAvailable(requireContext())) {
                if (checkEmpty()) {
                    binding.registerBtn.visibility = View.GONE
                    binding.prog.visibility = View.VISIBLE

                    val customer = Customer(
                        email = binding.emailEt.text.toString(),
                        firstName = binding.firstNameEt.text.toString(),
                        lastName = binding.lastEt.text.toString(),
                        state = "enabled",
                        tags = binding.passwordEt.text.toString()
                    )

                    val model = CustomerRegisterModel(customer)
                    registerViewModel.registerCustomer(model)
                }

            } else {
                showSnackBar(getString(R.string.no_connection))
                binding.animationView.visibility = View.VISIBLE
            }
        }

        registerViewModel.signUpSuccess.observe(viewLifecycleOwner) {
            if (it != null) {
                Paper.init(requireContext())
                Paper.book().write("id", it.id)
                Paper.book().write("email", binding.emailEt.text.toString())
                Paper.book().write(
                    "name",
                    binding.firstNameEt.text.toString() + " " + binding.lastEt.text.toString()
                )
                initUser()
                requireActivity().finish()
                Toast.makeText(
                    requireContext(),
                    context?.getString(R.string.User_Created_Successfully),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(requireContext(), "this user already exist", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun initUser() {
        val cartViewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]
        if (Paper.book().read<String>("email") != null) {
            user.id = Paper.book().read<Long>("id")!!
            user.email = Paper.book().read<String>("email").toString()
            user.firstName = Paper.book().read<String>("name").toString()

            FirebaseDatabase.getInstance()
                .getReference(user.id.toString())
                .child("cart")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        Paper.book().write<Int>("count", snapshot.children.count())
                        //Constants.count = Paper.book().read<Int>("count")!!
//                        val viewModel = ViewModelProvider(this@LoginFragment)[ProfileSharedViewModel::class.java]
//                        viewModel.setCount(count)

                        snapshot.children.forEach {
                            val cart: Cart = it.getValue(Cart::class.java)!!
                            cartViewModel.addProductToCart(cart)
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
        }
    }

    private fun checkEmpty(): Boolean {
        email = binding.emailEt.text.toString()
        name = binding.firstNameEt.text.toString()
        lastName = binding.lastEt.text.toString()
        pass = binding.passwordEt.text.toString()
        confirmPass = binding.confirmEt.text.toString()
        if (name.isEmpty()) {
            binding.firstNameEt.requestFocus()
            binding.firstNameEt.error = getString(R.string.required)
            return false
        }
        if (email.isEmpty()) {
            binding.emailEt.requestFocus()
            binding.emailEt.error = getString(R.string.required)

            return false
        }
        if (!isValidEmail(email)) {
            binding.emailEt.error = getString(R.string.not_valid_email)
        }

        if (lastName.isEmpty()) {
            binding.lastEt.requestFocus()
            binding.lastEt.error = getString(R.string.required)
            return false
        }
        if (pass.isEmpty()) {
            binding.passwordEt.requestFocus()
            binding.passwordEt.error = getString(R.string.required)
            return false
        }
        if (confirmPass.isEmpty()) {
            binding.confirmEt.requestFocus()
            binding.confirmEt.error = getString(R.string.required)
            return false
        }
        if (pass != confirmPass) {
            binding.confirmEt.requestFocus()
            binding.confirmEt.error = getString(R.string.passward_doesnot_match)
            return false
        }
        return true
    }

    private fun isValidEmail(str: String): Boolean {
        return EMAIL_PATTERN.matcher(str).matches()
    }

}