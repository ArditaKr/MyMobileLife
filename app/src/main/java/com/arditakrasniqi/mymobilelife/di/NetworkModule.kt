package com.arditakrasniqi.mymobilelife.di


import androidx.viewbinding.BuildConfig
import com.arditakrasniqi.mymobilelife.api.ServiceAPI
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val TIMEOUT: Long = 10

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideServiceAPI(retrofit: Retrofit): ServiceAPI {
        return retrofit.create(ServiceAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        val useLogging: Boolean = BuildConfig.DEBUG
        val interceptor = HttpLoggingInterceptor()

        interceptor.setLevel(if (useLogging) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .callTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
        return builder.build()
    }


    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder()
            .setLenient()
            .create()


    private val baseUrl by lazy { if (BuildConfig.DEBUG) PRODUCTION_URL else DEV_URL }

    private const val DEV_URL = "https://picsum.photos/"
    private const val LOCAL_URL = ""
    private const val PRODUCTION_URL = "https://picsum.photos/"

}