package com.example.desafiodigitalhouse.view.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.desafiodigitalhouse.adapter.ComicsAdapter
import com.example.desafiodigitalhouse.utils.Constants.Intent.COMIC_DATA
import com.example.desafiodigitalhouse.viewModel.ComicsViewModel
import com.example.desafiotresdigitalhouse.R
import com.example.desafiotresdigitalhouse.databinding.ActivityMenuBinding


class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var viewModelComics: ComicsViewModel
    var name: String? = null
    var imageUri: Uri? = null

    private val comicAdapter by lazy {
        ComicsAdapter {result ->
            result?.let {
                val intent = Intent(this, HqsDetailsActivity::class.java)
                intent.putExtra(COMIC_DATA, it)
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelComics = ViewModelProvider(this).get(ComicsViewModel::class.java)
        name = intent.getStringExtra("name")
        imageUri = intent.getParcelableExtra("uriPhoto")


        binding.tvNameMenu.text = name ?: "Herói"
        Glide.with(this).load(imageUri)
            .placeholder(R.drawable.no_photo_placeholder)
            .into(binding.ivProfilePhotoMenu)

        setActionBar()
        setUpRecyclerView()
        loadContentComic()
    }

    private fun setUpRecyclerView() {
        binding.rvMenu.apply {
            layoutManager = GridLayoutManager(this@MenuActivity, 3)
            adapter = comicAdapter
        }
    }

    private fun loadContentComic() {
        viewModelComics.comicPagedList?.observe(this) {pagedList ->
            comicAdapter.submitList(pagedList)
        }
    }

    private fun setActionBar() {
        supportActionBar?.setTitle(R.string.string_marvel)
    }
}