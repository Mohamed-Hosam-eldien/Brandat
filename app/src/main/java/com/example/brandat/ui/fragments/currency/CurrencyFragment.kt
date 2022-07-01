package com.example.brandat.ui.fragments.currency

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.brandat.R
import com.example.brandat.databinding.FragmentCurrencyBinding
import com.example.brandat.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyFragment : Fragment() {
    lateinit var binding: FragmentCurrencyBinding
    lateinit var sharedPreferences:SharedPreferences
    lateinit var  editor : SharedPreferences.Editor
    lateinit var currencyCode:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_currency, container, false)
        return  binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences(Constants.SHARD_NAME,Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        currencyCode = sharedPreferences.getString(Constants.CURRENCY_TYPE,getString(R.string.egypt_currency))!!

        when(currencyCode){
           getString( R.string.dollar_currency)->{
                binding.rdbBound.isChecked = false
                binding.rdbDollar.isChecked = true
               }
            getString( R.string.egypt_currency)->{
                binding.rdbBound.isChecked = true
                binding.rdbDollar.isChecked = false
            }
        }
        binding.cardBound.setOnClickListener {
            binding.rdbBound.isChecked = true
            binding.rdbDollar.isChecked = false
            editor.putString(Constants.CURRENCY_TYPE,getString(R.string.egypt_currency))
            editor.apply()
            editor.commit()
        }

        binding.dollar.setOnClickListener {
            binding.rdbBound.isChecked = false
            binding.rdbDollar.isChecked = true
            editor.putString(Constants.CURRENCY_TYPE,getString(R.string.dollar_currency))
            editor.apply()
            editor.commit()
        }

    }

}
