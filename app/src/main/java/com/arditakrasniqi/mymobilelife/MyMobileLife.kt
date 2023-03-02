package com.arditakrasniqi.mymobilelife

import android.app.Application
import com.bumptech.glide.Glide
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@HiltAndroidApp
class MyMobileLife: Application() {

    override fun onCreate() {
        super.onCreate()

        // Create a TrustManager that trusts all certificates
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}

            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        })

// Create an SSLContext with the TrustManager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())

// Create an OkHttpClient with the SSLContext and set it as the default client
        val okHttpClient = OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
            .build()


    }
}