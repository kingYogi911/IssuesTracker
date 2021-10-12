package com.example.issuestracker.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.issuestracker.databinding.ActivityDetailsBinding
import com.example.issuestracker.utils.MyViewModelFactory
import com.example.issuestracker.utils.NetworkStateHelper
import com.example.issuestracker.utils.Repository
import com.example.issuestracker.utils.RetrofitHelper
import com.example.issuestracker.viewModels.CommentsViewModel
import com.example.issuestracker.viewModels.MainViewModel

class DetailsActivity : AppCompatActivity() {
    private val url by lazy { intent.extras?.getString("url")}
    private val repository by lazy { Repository(RetrofitHelper.getServiceInstance()) }
    private val viewModel by lazy {
        ViewModelProvider(this, MyViewModelFactory(repository)).get(
            CommentsViewModel::class.java
        ).also {
            it.comment_url.postValue(url)
        }
    }
    private val mainViewModel by lazy {
        MainActivity.vm
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityDetailsBinding.inflate(layoutInflater).apply {
            this.lifecycleOwner=this@DetailsActivity
            this.viewModel=this@DetailsActivity.viewModel
            this.mainViewModel=this@DetailsActivity.mainViewModel
        }.let {
            setContentView(it.root)
        }
        NetworkStateHelper(this).observe(this, {
            it?.let {
                viewModel.isOnline.postValue(it)
            }
        })
    }
}