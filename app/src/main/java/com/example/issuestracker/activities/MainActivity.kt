package com.example.issuestracker.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.issuestracker.R
import com.example.issuestracker.databinding.ActivityMainBinding
import com.example.issuestracker.utils.*
import com.example.issuestracker.viewModels.MainViewModel

class MainActivity : AppCompatActivity() {
    private val repository by lazy { Repository(RetrofitHelper.getServiceInstance()) }
    private val viewModel by lazy {
        ViewModelProvider(this, MyViewModelFactory(repository)).get(
            MainViewModel::class.java
        ).apply {
           this.itemClickListener=myClickListener
        }.also {
            vm=it
        }
    }
    private val myClickListener:RvItemClickListener=object: RvItemClickListener{
        override fun onItemClicked(position: Int, view: View) {
            Intent(this@MainActivity,DetailsActivity::class.java).let{
                it.putExtra("url",viewModel.getCommentUrl(position))
                startActivity(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ActivityMainBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@MainActivity
            this.viewmodel = this@MainActivity.viewModel
        }.also { binding ->
            setContentView(binding.root)
        }

        NetworkStateHelper(this).observe(this, {
            it?.let {
                viewModel.isOnline.postValue(it)
            }
        })
    }
    companion object{
        var vm:MainViewModel? = null;
    }
}