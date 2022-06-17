package com.example.brandat.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.example.brandat.CategoryViewModel
import com.example.brandat.ui.fragments.home.BrandViewModel

class ConnectionUtil {
    companion object {
        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val capabilities =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                    } else {
                        TODO("VERSION.SDK_INT < M")
                    }

                if (capabilities != null) {
                    when {
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                            Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                            return true
                        }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                            Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                            return true
                        }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                            Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                            return true
                        }
                    }
                }
            }
            return false
        }
//============================================
 fun registerConnectivityNetworkMonitor(context: Context,viewModel: ViewModel,activity: FragmentActivity) {
    if (context != null) {
        val connectivityManager =
           context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder = NetworkRequest.Builder()
        connectivityManager.registerNetworkCallback(builder.build(),
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    if (activity != null) {
                        activity!!.runOnUiThread {
                           // brandViewModel.getBrands()
                            if (viewModel is BrandViewModel){
                                viewModel.getBrands()
                            }else if(viewModel is CategoryViewModel){
                                viewModel.getAllProductsByName()
                            }
                        }
                    }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    if (activity != null) {
                        activity!!.runOnUiThread {
                            Toast.makeText(
                                context, "No Internet",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                }
            }
        )
    }
}

    }
}