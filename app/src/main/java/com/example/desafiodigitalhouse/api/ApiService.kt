package com.example.desafiodigitalhouse.api

import com.example.desafiodigitalhouse.utils.Constants.Api.API_HASH_NAME
import com.example.desafiodigitalhouse.utils.Constants.Api.API_KEY_NAME
import com.example.desafiodigitalhouse.utils.Constants.Api.API_KEY_VALUE
import com.example.desafiodigitalhouse.utils.Constants.Api.API_PRIVATE_KEY_VALUE
import com.example.desafiodigitalhouse.utils.Constants.Api.API_TS_NAME
import com.example.desafiodigitalhouse.utils.Constants.Api.BASE_URL
import com.example.desafiodigitalhouse.utils.Constants.Api.QUERY_PARAM_CHARACTER_NAME
import com.example.desafiodigitalhouse.utils.Constants.Api.QUERY_PARAM_CHARACTER_VALUE
import com.example.desafiodigitalhouse.utils.Constants.Api.QUERY_PARAM_FORMAT_NAME
import com.example.desafiodigitalhouse.utils.Constants.Api.QUERY_PARAM_FORMAT_TYPE_NAME
import com.example.desafiodigitalhouse.utils.Constants.Api.QUERY_PARAM_FORMAT_TYPE_VALUE
import com.example.desafiodigitalhouse.utils.Constants.Api.QUERY_PARAM_FORMAT_VALUE
import com.example.desafiodigitalhouse.utils.Constants.Api.QUERY_PARAM_NO_VARIANTS_NAME
import com.example.desafiodigitalhouse.utils.Constants.Api.QUERY_PARAM_NO_VARIANTS_VALUE
import com.example.desafiodigitalhouse.utils.Constants.Api.QUERY_PARAM_ORDER_BY_NAME
import com.example.desafiodigitalhouse.utils.Constants.Api.QUERY_PARAM_ORDER_BY_VALUE
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.concurrent.TimeUnit

object ApiService {
    val marvelApi: MarvelAPI = getMarvelClient().create(MarvelAPI::class.java)

    private fun getMarvelClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getInterceptorMarvelClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getInterceptorMarvelClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val interceptor = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor {chain ->
                val newRequest = chain.request().newBuilder()
                    .build()
                chain.proceed(newRequest)
            }
            .addInterceptor { chain ->
                val currentTimestamp = System.currentTimeMillis()
                val url = chain.request().url().newBuilder()
                    .addQueryParameter(API_HASH_NAME,
                    toMD5Hash(currentTimestamp.toString() + API_PRIVATE_KEY_VALUE + API_KEY_VALUE))
                    .addQueryParameter(API_TS_NAME, currentTimestamp.toString())
                    .addQueryParameter(QUERY_PARAM_FORMAT_NAME, QUERY_PARAM_FORMAT_VALUE)
                    .addQueryParameter(QUERY_PARAM_FORMAT_TYPE_NAME, QUERY_PARAM_FORMAT_TYPE_VALUE)
                    .addQueryParameter(QUERY_PARAM_CHARACTER_NAME, QUERY_PARAM_CHARACTER_VALUE)
                    .addQueryParameter(QUERY_PARAM_NO_VARIANTS_NAME, QUERY_PARAM_NO_VARIANTS_VALUE)
                    .addQueryParameter(API_KEY_NAME, API_KEY_VALUE)
                    .build()
                val newRequest = chain.request().newBuilder().url(url).build()
                chain.proceed(newRequest)
            }
        return  interceptor.build()
    }

    private fun toMD5Hash(toBeEncrypt: String): String {
        var pass = toBeEncrypt
        var encryptedString: String? = null
        val md5: MessageDigest
        try {
            md5 = MessageDigest.getInstance("MD5")
            md5.update(pass.toByteArray(), 0, pass.length)
            pass = BigInteger(1, md5.digest()).toString(16)
            while (pass.length < 32) {
                pass = "0$pass"
            }
            encryptedString = pass
        } catch (e1: NoSuchAlgorithmException) {
            e1.printStackTrace()
        }
        return encryptedString ?: ""
    }
}