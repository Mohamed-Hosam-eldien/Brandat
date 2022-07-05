package com.example.brandat.ui.fragments.map

import android.content.SharedPreferences
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.brandat.R
import com.example.brandat.databinding.FragmentMapsBinding
import com.example.brandat.models.CustomerAddress
import com.example.brandat.ui.fragments.address.AddressViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.*

@AndroidEntryPoint
class MapsFragment : Fragment() {
    private lateinit var latAndLong: LatLng
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    private val viewModel: AddressViewModel by viewModels()
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    lateinit var navController: NavController

    lateinit var customerAddress: CustomerAddress

    private lateinit var _binding: FragmentMapsBinding
    private val binding get() = _binding
    private val callback = OnMapReadyCallback { googleMap ->

        googleMap.setOnMapClickListener { latLng ->

            latAndLong = latLng
            val options = MarkerOptions()
            options.position(latAndLong)
            options.title(latAndLong.latitude.toString() + " : " + latAndLong.longitude)
            latitude = latAndLong.latitude
            longitude = latAndLong.longitude
            binding.addresTxt.visibility = View.VISIBLE
            binding.addresTxt.text = getTimeZone(latitude, longitude).printAddress()
            binding.btnLocation.visibility = View.VISIBLE
            googleMap.clear()
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latAndLong, 10.2222f))
            googleMap.addMarker(options)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)

        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_maps, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.google_map_location) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)



        binding.btnLocation.setOnClickListener {

            if (getTimeZone(latitude, longitude).country != "Egypt") {
                Toast.makeText(context, "Delivery in Egypt only so far", Toast.LENGTH_SHORT).show()
            } else {
                if (latitude != 0.0 && longitude != 0.0) {

                    customerAddress = getTimeZone(latitude, longitude)
                    viewModel.insertAddress(customerAddress)


                    Toast.makeText(
                        requireContext(),
                        getString(R.string.saved_successfully),
                        Toast.LENGTH_SHORT
                    ).show()

                    findNavController().popBackStack()
                    //navController.popBackStack()
                } else {
                    Toast.makeText(
                        context,
                        getString(R.string.select_your_address),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

        }
    }

    private fun getTimeZone(lat: Double, lon: Double)
            : CustomerAddress {

        var street = "Cairo"
        var state = "state"
        var city = "Street 45"
        var country = "Egypt"
        val geocoder: Geocoder
        val addresses: List<Address>
        geocoder = Geocoder(context, Locale.getDefault())

        try {

            addresses = geocoder.getFromLocation(lat, lon, 5)
            if (addresses != null) {
                val address = addresses[0]

                state = address.locality ?: address.featureName
                city = address.adminArea ?: "Suez"
                country = address.countryName ?: "Egypt"
                street = address.getAddressLine(0)

            }


        } catch (e: IOException) {

        }

        return CustomerAddress(false, state, city, country)
    }
}