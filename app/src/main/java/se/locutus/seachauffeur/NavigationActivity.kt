package se.locutus.seachauffeur

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.bluetooth.BluetoothSocket
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.os.Handler
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.Marker
import se.locutus.sea_chauffeur.Messages
import java.io.InputStream
import java.io.OutputStream
import java.io.IOException
import java.lang.Exception
import java.util.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.math.abs

const val LOCATION_ACCESS_REQUEST_CODE = 99


class NavigationActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        findViewById<FloatingActionButton>(R.id.trim_button).setOnClickListener{
            showTrimDialog()
        }

        locationAccessCheck()
        createBtConnection()
    }


    var currentDestination : Marker? = null

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapClickListener { latLng ->
            if (currentDestination != null) {
                currentDestination!!.remove()
            }
            currentDestination = mMap.addMarker(MarkerOptions().position(latLng).title("Current destination"))
            sendNewDestination(latLng)
        }
        centerMapOnMyLocation()
    }

    private fun locationAccessCheck() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_ACCESS_REQUEST_CODE
                )
            } else {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_ACCESS_REQUEST_CODE
                )
            }
        } else {
            Toast.makeText(this, "No access to location :(", Toast.LENGTH_SHORT).show()
        }
    }

    private fun centerMapOnMyLocation() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {

            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                            mMap.animateCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        location.latitude,
                                        location.longitude
                                    ),
                                    14.0f
                                )
                            )
                        } else {
                            Toast.makeText(this, "No access to location :(", Toast.LENGTH_SHORT).show()
                        }
                }
        }
    }

    fun createBtConnection() {
        var retry = false
        try {
            if (!findBT()) {
                Toast.makeText(this, "Did not find bluetooth device.. will retry :)" +
                        "", Toast.LENGTH_SHORT).show()
                retry = true
            } else {
                openBTAndListenForData()
            }
        } catch (e : Exception) {
            e.printStackTrace()
            mmDevice = null
            if (mmSocket != null) {
                mmSocket!!.close()
            }
            retry = true
        }
        if (retry) {
            handler.postDelayed({
                createBtConnection()
            }, 2000)
        }
    }

    fun sendNewDestination(latLng : LatLng) {
        val request = Messages.SeaRequest.newBuilder().setNavRequest(
            Messages.NavRequest.newBuilder()
                .setLocation(Messages.Location.newBuilder().setLat(latLng.latitude.toFloat())
                    .setLng(latLng.longitude.toFloat())))
            .build()
        if (mmOutputStream != null) {
            val array = request.toByteArray()
            mmOutputStream!!.write(array.size)
            mmOutputStream!!.write(array)

            Toast.makeText(this, "Sent new destination ${request.navRequest.location.lat} ${request.navRequest.location.lng}",
                Toast.LENGTH_SHORT).show()
        }
    }

    fun sendTrimRequest(millis : Int) {
        val request = Messages.SeaRequest.newBuilder().setTrimRequest(Messages.SteeringMove.newBuilder()
            .setDirection(if (millis >= 0) Messages.SteeringDirection.LEFT else Messages.SteeringDirection.RIGHT)
            .setPower(100) // Not used...
            .setMillis(abs(millis)))
            .build()
        if (mmOutputStream != null) {
            val array = request.toByteArray()
            mmOutputStream!!.write(array.size)
            mmOutputStream!!.write(array)

            Toast.makeText(this, "Sent new destination ${request.navRequest.location.lat} ${request.navRequest.location.lng}",
                Toast.LENGTH_SHORT).show()
        }
    }

    var trimState = 50

    var mBluetoothAdapter: BluetoothAdapter? = null
    var mmSocket: BluetoothSocket? = null
    var mmDevice: BluetoothDevice? = null
    var mmOutputStream: OutputStream? = null
    var mmInputStream: InputStream? = null
    var workerThread: Thread? = null
    @Volatile
    var stopWorker: Boolean = false

    fun findBT() : Boolean {
        mmDevice = null
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "No bluetooth adapter available", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!mBluetoothAdapter!!.isEnabled) {
            val enableBluetooth = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBluetooth, 0)
        }

        val pairedDevices = mBluetoothAdapter!!.getBondedDevices()
        if (pairedDevices.size > 0) {
            for (device in pairedDevices) {
                if (device.name == "SeaChauffeur") {
                    mmDevice = device
                    break
                }
            }
        }
        return mmDevice != null
    }

    @Throws(IOException::class)
    fun openBTAndListenForData() {
        val uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB") //Standard SerialPortService ID
        mmSocket = mmDevice!!.createRfcommSocketToServiceRecord(uuid)
        mmSocket!!.connect()
        mmOutputStream = mmSocket!!.getOutputStream()
        mmInputStream = mmSocket!!.getInputStream()

        beginListenForData()

        Toast.makeText(this, "Bluetooth opened :)", Toast.LENGTH_SHORT).show()
    }

    fun beginListenForData() {
        stopWorker = false
        workerThread = Thread(Runnable {
            while (!Thread.currentThread().isInterrupted && !stopWorker) {
                try {
                    val response = Messages.SeaResponse.parseDelimitedFrom(mmInputStream)
                    handler.post{
                        Toast.makeText(this, "Bluetooth data response code: ${response.responseCode} current destination ${response.currentDestination.lat} ${response.currentDestination.lng}", Toast.LENGTH_SHORT).show()
                    }
                } catch (ex: IOException) {
                    if (mmSocket!!.isConnected) {
                        while (mmInputStream!!.available() > 0) {
                            System.err.println("Buffer flush read ${mmInputStream!!.read()}")
                        }
                    }
                    ex.printStackTrace()
                }

            }
        })

        workerThread!!.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        closeBT()
    }


    @Throws(IOException::class)
    fun closeBT() {
        stopWorker = true
        mmOutputStream!!.close()
        mmInputStream!!.close()
        mmSocket!!.close()
    }

    fun showTrimDialog(){
        val popDialog = AlertDialog.Builder(this)
        val seek = SeekBar(this)
        seek.max = 600
        seek.progress =  seek.max/2
        trimState =  seek.max/2
        seek.keyProgressIncrement = 1
        seek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                val diff = trimState - seekBar.progress
                trimState = seekBar.progress
                sendTrimRequest(diff)
            }
        })
        popDialog.setIcon(android.R.drawable.btn_star_big_on);
        popDialog.setTitle("Trim to go straight")
        popDialog.setView(seek)
        popDialog.create()
        popDialog.show()
    }
}
