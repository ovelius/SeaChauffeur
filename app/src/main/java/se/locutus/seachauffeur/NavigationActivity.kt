package se.locutus.seachauffeur

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.bluetooth.BluetoothSocket
import android.os.Handler
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.maps.model.Marker
import se.locutus.sea_chauffeur.Messages
import java.io.InputStream
import java.io.OutputStream
import java.io.IOException
import java.lang.Exception
import java.util.*


class NavigationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var mButton : Button
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        mButton = findViewById(R.id.button_send)

        mButton.setOnClickListener {
            Toast.makeText(this, "Sending data" +
                "", Toast.LENGTH_SHORT).show()
            mmOutputStream!!.write("hello".toByteArray())
        }

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
                    while (mmInputStream!!.available()> 0) {
                        System.err.println("Buffer flush read ${mmInputStream!!.read()}")
                    }
                    ex.printStackTrace()
                   // stopWorker = true
                }

            }
        })

        workerThread!!.start()
    }

    @Throws(IOException::class)
    fun closeBT() {
        stopWorker = true
        mmOutputStream!!.close()
        mmInputStream!!.close()
        mmSocket!!.close()
    }
}
