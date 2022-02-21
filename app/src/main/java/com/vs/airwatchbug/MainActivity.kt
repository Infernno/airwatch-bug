package com.vs.airwatchbug

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.airwatch.gateway.ui.GatewayBaseActivity
import kotlinx.coroutines.*
import java.io.File
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : GatewayBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.run_button)
        val progress = findViewById<ProgressBar>(R.id.progress)

        btn.setOnClickListener {
            lifecycleScope.launch {
                btn.isVisible = false
                progress.isVisible = true

                val filesDirectory = filesDir
                val directory = File(filesDirectory, "temp")

                withContext(Dispatchers.IO) {
                    // Create temp directory to read/write files
                    if (!directory.exists())
                        directory.mkdirs()

                    execute(directory)
                }

                btn.isVisible = true
                progress.isVisible = false
            }
        }
    }

    private suspend fun execute(workingDirectory: File) = coroutineScope {
        var attempt = 0

        while (isActive) {
            try {
                // 1. Create temporary file
                val tempFile = File(workingDirectory, "test_$attempt.txt")

                tempFile.createNewFile()
                tempFile.appendText("Hello world! This is attempt $attempt") // Here throws IOException: write failed: EPIPE (Broken pipe)

                // 2. Make request
                makeRequest()

                showToast("Success")
            } catch (th: Throwable) {
                Log.e(TAG, "execute() failed", th)
                showToast(th.toString())
            }

            attempt++

            delay(2000)
        }
    }

    private suspend fun showToast(msg: String) {
        withContext(Dispatchers.Main) {
            Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun makeRequest() {
        val url = URL(ADDRESS)
        val connection = (url.openConnection() as HttpsURLConnection).apply {
            requestMethod = "GET"
            readTimeout = 1500
            connectTimeout = 1500
        }

        connection.connect()
    }

    private companion object {
        const val ADDRESS = "<type address here>"
        const val TAG = "DemoApp"
    }
}