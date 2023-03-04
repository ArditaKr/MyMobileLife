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

}