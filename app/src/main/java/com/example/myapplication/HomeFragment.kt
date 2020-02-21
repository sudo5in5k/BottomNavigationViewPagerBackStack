package com.example.myapplication

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.myapplication.DeepFragment.Companion.TAG
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            childFragmentManager.beginTransaction().replace(R.id.new_container, DeepFragment(), TAG)
                .addToBackStack(TAG).commit()
        }
    }
}