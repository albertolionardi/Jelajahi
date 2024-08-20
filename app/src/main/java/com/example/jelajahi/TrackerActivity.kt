package com.example.jelajahi

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.snackbar.Snackbar


class TrackerActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private var isRecording: Boolean = false
    private val pathPoints = mutableListOf<LatLng>()

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracker)

        // Initialize Google Map
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Initialize location services
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Setup Start/Stop Button
        val btnStartStop = findViewById<Button>(R.id.btnStartStop)
        btnStartStop.setOnClickListener {
            if (isRecording) {
                stopRecording()
                btnStartStop.text = "Start Recording"
            } else {
                checkLocationPermissionAndStartRecording()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Check for location permission and start recording if granted
        checkLocationPermissionAndStartRecording()
    }

    private fun checkLocationPermissionAndStartRecording() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permission is already granted, start recording
            startRecording()
        } else {
            // Request location permissions
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun startRecording() {
        Log.d("TrackerActivity", "Start recording invoked")
        isRecording = true
        pathPoints.clear()

        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000)
            .setMinUpdateIntervalMillis(3000)
            .build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    val latLng = LatLng(location.latitude, location.longitude)
                    pathPoints.add(latLng)
                    updateMap(latLng)
                }
            }
        }

        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())

            // Get the last known location and move the camera to it immediately
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val userLocation = LatLng(location.latitude, location.longitude)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 18f))
                    Log.d("TrackerActivity", "Moved camera to user's location: $userLocation")
                } else {
                    Log.d("TrackerActivity", "Last known location is null")
                }
            }

            Log.d("TrackerActivity", "Location updates requested")
        } else {
            Log.d("TrackerActivity", "Location permission not granted")
        }
    }

    private fun stopRecording() {
        isRecording = false
        fusedLocationClient.removeLocationUpdates(locationCallback)
        Snackbar.make(findViewById(R.id.map), "Recording stopped", Snackbar.LENGTH_LONG).show()
    }

    private fun updateMap(latLng: LatLng) {
        if (pathPoints.size > 1) {
            val lastPoint = pathPoints[pathPoints.size - 2]
            mMap.addPolyline(PolylineOptions().add(lastPoint, latLng).color(android.graphics.Color.BLUE))
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))  // Keep the camera centered on the user's location
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                // At least one location permission is granted
                startRecording()
            } else {
                // Permission was denied, show a message to the user
                Snackbar.make(findViewById(R.id.map), "Location permission is required to track your path.", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (isRecording) {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }

    override fun onResume() {
        super.onResume()
        if (isRecording) {
            checkLocationPermissionAndStartRecording()
        }
    }
}