package com.example.brandat.ui.fragments.currency

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.brandat.R
import com.example.brandat.databinding.FragmentCurrencyBinding
import com.example.brandat.utils.Constants
import com.example.brandat.utils.CurrenciesEnum
import com.example.brandat.utils.ResponseResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyFragment : Fragment() {
    lateinit var binding: FragmentCurrencyBinding
    lateinit var sharedPreferences:SharedPreferences
    lateinit var  editor : SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_currency, container, false)
        return  binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences(Constants.SHARD_NAME,Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        binding.cardBound.setOnClickListener {
            binding.rdbBound.isChecked = true
            binding.rdbDollar.isChecked = false
            editor.putString(Constants.CURRENCY_TYPE,CurrenciesEnum.EGP.toString())
            editor.apply()
            editor.commit()
        }

        binding.dollar.setOnClickListener {
            binding.rdbBound.isChecked = false
            binding.rdbDollar.isChecked = true
            editor.putString(Constants.CURRENCY_TYPE,CurrenciesEnum.USD.toString())
            editor.apply()
            editor.commit()
        }

    }

}