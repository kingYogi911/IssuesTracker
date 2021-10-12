package com.example.issuestracker.viewModels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.issuestracker.adapters.CommentListAdapter
import com.example.issuestracker.data.Comments
import com.example.issuestracker.utils.Repository
import com.example.issuestracker.utils.RetrofitHelper.Companion.BASE_URL
import com.example.issuestracker.utils.UiVisibilityController
import com.google.gson.Gson
import kotlinx.coroutines.launch

class CommentsViewModel(private val repository: Repository) : ViewModel() {
    private val commentList = ArrayList<Comments>()
    val _adapter=MutableLiveData<CommentListAdapter>()
    val comment_url = MutableLiveData<String>()
    private val uiController = UiVisibilityController()
    private var isDataAvailable = false
    private var isInProgress = false;
    val isOnline = MutableLiveData<Boolean>(false)

    init {
        _adapter.value= CommentListAdapter(commentList);
        comment_url.observeForever {
            if (!isInProgress) {
                it?.let {
                    viewModelScope.launch {
                        fetchCommentData(it)
                    }
                }
            }
        }
        isOnline.observeForever { online ->
            if (!isInProgress&&!isDataAvailable) {
                if (online) {
                    viewModelScope.launch {
                        fetchCommentData(comment_url.value!!)
                    }
                } else {
                    uiController.showError("No Internet!")
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private suspend fun fetchCommentData(url: String) {
        val finalURL=url.split(BASE_URL).let { it[1] }
        if(!isInProgress) {
            try {
                isInProgress=true;
                uiController.showProgress()
                val temp = repository.getComments(finalURL);
                Log.e(TAG, Gson().toJson(temp));
                isDataAvailable = true
                temp?.let { commentList.addAll(it) }
                _adapter.value!!.notifyDataSetChanged();
                uiController.showContent()
            } catch (e: Exception) {
                isDataAvailable = false
                uiController.showError("Something Went Wrong!")
            } finally {
                isInProgress = false;
            }
        }
    }
    companion object{
        val TAG=CommentsViewModel::class.java.simpleName
    }
}