package com.example.issuestracker.viewModels

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.issuestracker.adapters.IssuesListAdapter
import com.example.issuestracker.data.Issue
import com.example.issuestracker.utils.Repository
import com.example.issuestracker.utils.RvItemClickListener
import com.example.issuestracker.utils.UiVisibilityController
import com.google.gson.Gson
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {
    lateinit var itemClickListener: RvItemClickListener
    lateinit var selectedIssue:Issue
    private val issueList = ArrayList<Issue>()
    private val _adapter = MutableLiveData<IssuesListAdapter>()
    val adapter get() = _adapter
    private val uiController = UiVisibilityController()
    val isOnline = MutableLiveData<Boolean>(false)
    private var isDataAvailable = false

    init {
        _adapter.value =
            IssuesListAdapter(issueList).apply { setItemClickListener(object : RvItemClickListener {
                override fun onItemClicked(position: Int, view: View) {
                    selectedIssue=issueList[position]
                    itemClickListener?.onItemClicked(position, view)
                }
            }) };
        isOnline.observeForever { online ->
            if (!isDataAvailable) {
                if (online) {
                    viewModelScope.launch {
                        fetchMainData()
                    }
                } else {
                    uiController.showError("No Internet!")
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private suspend fun fetchMainData() {
        try {
            uiController.showProgress()
            val temp = repository.getIssues();
            Log.e(TAG, Gson().toJson(temp));
            issueList.addAll(temp!!)
            isDataAvailable = true
            _adapter.value!!.notifyDataSetChanged();
            uiController.showContent()
        } catch (e: Exception) {
            isDataAvailable = false
            uiController.showError("Something Went Wrong!")
        }
    }

    fun getCommentUrl(position: Int): String = issueList[position].comment_url

    companion object {
        public val TAG: String = MainViewModel::class.java.simpleName
    }
}