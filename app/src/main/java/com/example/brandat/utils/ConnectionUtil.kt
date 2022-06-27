package com.example.brandat.utils

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.example.brandat.CategoryViewModel
import com.example.brandat.R
import com.example.brandat.ui.fragments.home.BrandViewModel
import com.example.brandat.ui.fragments.myOrder.MyOrderViewModel
import com.example.brandat.ui.fragments.serach.SearchViewModel
import com.example.brandat.viewmodels.ProductDetailsViewModel
import com.google.android.material.snackbar.Snackbar

class ConnectionUtil {
    companion object {

        var snack: Snackbar? = null
        var dialog: AlertDialog? = null
        var id: Long = 0
        var isShow = false
        var email: String = ""
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

        fun registerConnectivityNetworkMonitor(
            context: Context,
            viewModel: ViewModel,
            activity: FragmentActivity
        ) {

            if (context != null) {
                val connectivityManager =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val builder = NetworkRequest.Builder()
                connectivityManager.registerNetworkCallback(builder.build(),
                    object : ConnectivityManager.NetworkCallback() {
                        override fun onAvailable(network: Network) {
                            super.onAvailable(network)
                            if (activity != null) {
                                activity.runOnUiThread {
                                   // showSnackBar(activity)//come back
                                    if (viewModel is BrandViewModel) {
                                        viewModel.getBrands()
                                        viewModel.getDiscountCode()

                                    } else if (viewModel is CategoryViewModel) {
                                        viewModel.getAllProductsByName()
                                        viewModel.getCategory(395964875010L)
                                    } else if (viewModel is ProductDetailsViewModel) {
                                        Log.d("TAG", "onAvailable: id -->$id")
                                        viewModel.getProductDetailsFromDatabase(id)
                                    } else if (viewModel is SearchViewModel) {
                                        viewModel.getAllProduct()
                                    } else if (viewModel is MyOrderViewModel) {
                                        viewModel.getOrdersFromApi(email)
                                    }
                                }
                            }
                        }

                        override fun onLost(network: Network) {
                            super.onLost(network)
                            if (activity != null) {

                                for (i in 1..4) {
                                    if (!isShow) {
                                        showDialoge(context)
                                        isShow = true
                                        break
                                    }
                                }
                            }
                        }
                    }
                )
            }

        }

        fun showDialoge(context: Context) {
            var dialog = AlertDialog.Builder(context)
            dialog.apply {
                setIcon(R.drawable.ic_no_internet)
                setTitle(context.getString(R.string.warning))
                setCancelable(false)
                setMessage(context.getString(R.string.itseemyou))
                setPositiveButton(context.getString(R.string.ok)) { _, _ ->
                    isShow = false
                }
                show()
            }

        }
    }
}