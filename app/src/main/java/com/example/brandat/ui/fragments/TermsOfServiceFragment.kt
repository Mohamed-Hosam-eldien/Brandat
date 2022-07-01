package com.example.brandat.ui.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.example.brandat.R
import com.example.brandat.databinding.FragmentTermsOfServiceBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermsOfServiceFragment : Fragment() {

    lateinit var binding: FragmentTermsOfServiceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_terms_of_service, container, false)

        return binding.root
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webViewSetUp()

    }


    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun webViewSetUp() {
        binding.webViewWv.webViewClient = WebViewClient()

        binding.webViewWv.apply {
            loadUrl("https://www.shopify.com/legal/terms")
            settings.javaScriptEnabled  = true
            settings.safeBrowsingEnabled = true
        }
    }

}