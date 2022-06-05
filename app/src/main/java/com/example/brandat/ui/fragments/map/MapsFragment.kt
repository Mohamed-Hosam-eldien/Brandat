package com.example.brandat.ui.fragments.map

import android.content.SharedPreferences
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
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

    // private lateinit var viewModel: MapViewModel

    private lateinit var _binding: FragmentMapsBinding
    private val binding get() = _binding
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        googleMap.setOnMapClickListener { latLng ->
            latAndLong = latLng
            val options = MarkerOptions()
            options.position(latAndLong)
            options.title(latAndLong.latitude.toString() + " : " + latAndLong.longitude)
            latitude = latAndLong.latitude
            longitude = latAndLong.longitude

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
    ): View? {
        setHasOptionsMenu(true)
//        sharedPreferences = activity?.getSharedPreferences(Utli.LOCATION, Context.MODE_PRIVATE)!!
//        latitude = sharedPreferences.getString("lat", "33")?.toDouble()!!
//        longitude = sharedPreferences.getString("lon", "-94.04")?.toDouble()!!
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_maps, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.google_map_location) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)


        binding.btnLocation.setOnClickListener {

            customerAddress = getTimeZone(latitude, longitude)

            viewModel.insertAddress(customerAddress)

            Toast.makeText(requireContext(), "this country saved successfully", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_mapsFragment_to_addressFragment)
            //navController.popBackStack()

        }
    }

    private fun getTimeZone(lat: Double, lon: Double)
            : CustomerAddress {

        var street: String = "Cairo"
        var state: String = "state"
        var city: String = "Street 45"
        var country: String = "Egypt"
        val geocoder: Geocoder
        val addresses: List<Address>?
        geocoder = Geocoder(context, Locale.getDefault())

        try {
            addresses = geocoder.getFromLocation(lat, lon, 1)
            if (addresses != null) {
                val address = addresses[0]
                //street = address.locality


                state = address.locality ?: "Nile Street"
                city = address.subAdminArea ?: "Suez"
                country = address.countryName ?: "Egypt"
               street = address.getAddressLine(0)
                //timeZone= "$state $city $country".trimIndent()


            }

        } catch (e: IOException) {

        }

        return CustomerAddress(state, city, country)
    }
}