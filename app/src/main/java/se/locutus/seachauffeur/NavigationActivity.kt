package se.locutus.seachauffeur

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.bluetooth.BluetoothDevice
import java.nio.file.Files.size
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.bluetooth.BluetoothSocket
import android.os.Handler
import android.widget.Button
import android.widget.Toast
import java.io.InputStream
import java.io.OutputStream
import java.io.IOException
import java.util.*


class NavigationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var mButton : Button

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
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        findBT()
        openBT()
        beginListenForData()
    }

    var mBluetoothAdapter: BluetoothAdapter? = null
    var mmSocket: BluetoothSocket? = null
    var mmDevice: BluetoothDevice? = null
    var mmOutputStream: OutputStream? = null
    var mmInputStream: InputStream? = null
    var workerThread: Thread? = null
    var readBuffer: ByteArray = ByteArray(1024)
    var readBufferPosition: Int = 0
    var counter: Int = 0
    @Volatile
    var stopWorker: Boolean = false

    fun findBT() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "No bluetooth adapter available", Toast.LENGTH_SHORT).show()
            return
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
        Toast.makeText(this, "Bluetooth device found :)" +
                "", Toast.LENGTH_SHORT).show()
    }

    @Throws(IOException::class)
    fun openBT() {
        val uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB") //Standard SerialPortService ID
        mmSocket = mmDevice!!.createRfcommSocketToServiceRecord(uuid)
        mmSocket!!.connect()
        mmOutputStream = mmSocket!!.getOutputStream()
        mmInputStream = mmSocket!!.getInputStream()

        beginListenForData()

        Toast.makeText(this, "Bluetooth opened :)", Toast.LENGTH_SHORT).show()
    }

    fun beginListenForData() {
        val handler = Handler()
        val delimiter: Byte = 10 //This is the ASCII code for a newline character

        stopWorker = false
        readBufferPosition = 0
        workerThread = Thread(Runnable {
            while (!Thread.currentThread().isInterrupted && !stopWorker) {
                try {
                    val bytesAvailable = mmInputStream!!.available()
                    if (bytesAvailable > 0) {
                        val packetBytes = ByteArray(bytesAvailable)
                        mmInputStream!!.read(packetBytes)
                        for (i in 0 until bytesAvailable) {
                            val b = packetBytes[i]
                            if (b == delimiter) {
                                val encodedBytes = ByteArray(readBufferPosition)
                                System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.size)
                                val data = String(encodedBytes)
                                readBufferPosition = 0

                                handler.post{
                                    Toast.makeText(this, "Bluetooth data: $data", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                readBuffer[readBufferPosition++] = b
                            }
                        }
                    }
                } catch (ex: IOException) {
                    stopWorker = true
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
