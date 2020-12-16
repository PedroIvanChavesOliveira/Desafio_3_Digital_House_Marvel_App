package com.example.desafiodigitalhouse.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.desafiodigitalhouse.utils.Constants.Intent.URL
import com.example.desafiotresdigitalhouse.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
    lateinit var binding: ActivityWebViewBinding
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        url = intent.getStringExtra(URL)

        //Abrindo o site no próprio aplicativo
        binding.wvComicDetails.webViewClient = object: WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        url?.let { binding.wvComicDetails.loadUrl(it) }
    }
}