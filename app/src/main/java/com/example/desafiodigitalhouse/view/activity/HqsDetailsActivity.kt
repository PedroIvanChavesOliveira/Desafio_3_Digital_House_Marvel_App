package com.example.desafiodigitalhouse.view.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.desafiodigitalhouse.adapter.CreatorsAdapter
import com.example.desafiodigitalhouse.model.Result
import com.example.desafiodigitalhouse.utils.Constants
import com.example.desafiodigitalhouse.utils.Constants.Intent.COMIC_DATA
import com.example.desafiodigitalhouse.utils.Constants.Intent.URL
import com.example.desafiotresdigitalhouse.R
import com.example.desafiotresdigitalhouse.databinding.ActivityHqsDetailsBinding

class HqsDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHqsDetailsBinding
    private var comic: Result? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHqsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        comic = intent.getParcelableExtra(COMIC_DATA)

        comic?.images?.forEach {
            Glide.with(this).load(it.path)
                    .placeholder(R.drawable.marvel_placeholder)
                    .into(binding.ivBackDropHq)
        }

        Glide.with(this).load(comic?.thumbnail?.path)
                .placeholder(R.drawable.marvel_placeholder)
                .into(binding.ivImageHq)

        binding.tvTitleHq.text = comic?.title
        if(comic?.description == "") {
            binding.tvDescriptionHq.text = "Descrição não encontrada"
        }else {
            binding.tvDescriptionHq.text = comic?.description
        }

        comic?.prices?.forEach { binding.tvPriceHq.text = "Preço: US$ ${it.price}" }
        binding.tvPagesHq.text = "Páginas: ${comic?.pageCount}"

        binding.btComicDetails.isVisible = comic?.urls != null
        binding.btComicDetails.setOnClickListener {
            comic?.urls?.forEach{
                if(it.type == "detail") {
                    val intent = Intent(this, WebViewActivity::class.java)
                    intent.putExtra(URL, it.url)
                    startActivity(intent)
                }
            }
        }

        binding.ivArrowBackHq.setOnClickListener {
            finish()
        }

        binding.rvCreators.apply {
            layoutManager = LinearLayoutManager(this@HqsDetailsActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = comic?.creators?.items?.let { CreatorsAdapter(it) }
        }
    }
}