package com.jaimejiag.remoteconfigexample

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var mLoading: LoadingDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseInstanceId.getInstance().instanceId
            .addOnSuccessListener {
                Log.d("ID_TOKEN", it.token)
            }

        bt_login?.setOnClickListener {
            val firebaseAnalytics = FirebaseAnalytics.getInstance(this)
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.METHOD, FIREBASE_EVENT_LOGIN)
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle)
        }

        showProgress()
        fecthData()
    }


    private fun fecthData() {
        val remoteConfig = FirebaseRemoteConfig.getInstance()

        if (BuildConfig.DEBUG) {
            val remoteConfigSettings = FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(0)
                .build()

            remoteConfig.setConfigSettingsAsync(remoteConfigSettings)
        }

        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        remoteConfig.fetchAndActivate().addOnCompleteListener {
            if (it.isSuccessful) {
                tv_message?.text = remoteConfig.getString(LOGIN_MESSAGE)

                cv_login?.setCardBackgroundColor(Color.parseColor(remoteConfig.getString(
                    LOGIN_BACKGROUND_COLOR)))
            }

            hideProgress()
        }
    }


    private fun showProgress() {
        runOnUiThread {
            mLoading = LoadingDialog(this)
            mLoading?.showLoading()
        }
    }


    private fun hideProgress() {
        runOnUiThread {
            mLoading?.dismiss()
        }
    }


    companion object {
        const val FIREBASE_EVENT_LOGIN = "login"
        const val LOGIN_BACKGROUND_COLOR = "login_background_color"
        const val LOGIN_MESSAGE = "login_message"
    }
}
