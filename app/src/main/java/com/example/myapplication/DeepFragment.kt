package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment

class DeepFragment: Fragment(R.layout.fragment_deep) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("debug", fragmentManager?.findFragmentByTag(TAG).toString())
    }

    companion object {
        const val TAG = "DeepFragment"
    }
}