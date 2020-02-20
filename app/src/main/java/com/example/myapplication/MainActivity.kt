package com.example.myapplication

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener,
    ViewPager.OnPageChangeListener {

    lateinit var viewPagerAdapter: ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        navigation.setOnNavigationItemSelectedListener(this)
        view_pager.addOnPageChangeListener(this)
        view_pager.offscreenPageLimit = viewPagerAdapter.count -1
        view_pager.adapter = viewPagerAdapter
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home -> {
                view_pager.currentItem = 0
                return true
            }
            R.id.navigation_dashboard -> {
                view_pager.currentItem = 1
                return true
            }
            R.id.navigation_notifications -> {
                view_pager.currentItem = 2
                return true
            }
        }
        return false
    }

    override fun onPageScrollStateChanged(state: Int) {
        return
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        return
    }

    override fun onPageSelected(position: Int) {
        navigation.menu.getItem(position).isChecked = true
    }

    inner class ViewPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private var positionNow = -1
        private val stack = Stack<Int>()

        override fun getItem(position: Int): Fragment {
            return when(position) {
                0 -> HomeFragment()
                1 -> DashBoardFragment()
                2 -> NotificationsFragment()
                else -> HomeFragment()
            }
        }

        override fun getCount(): Int {
            return 3
        }

        override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
            if (positionNow != position) {
                stack.push(position)
                positionNow = position
            }
            super.setPrimaryItem(container, position, `object`)
        }

        fun getLastPositionFromStack(): Int {
            while (stack.size > 0) {
                val pos = stack.pop()
                if (pos != positionNow) {
                    return pos
                }
            }
            return -1
        }

        fun isFirstLaunchHome() = stack.size == 1 && positionNow == 0

        fun clear() {
            stack.clear()
            positionNow = -1
        }
    }

    override fun onBackPressed() {
        if(viewPagerAdapter.isFirstLaunchHome()) {
            viewPagerAdapter.clear()
            super.onBackPressed()
        } else {
            supportFragmentManager.fragments.forEach {
                if (it.isVisible) {
                    val position = viewPagerAdapter.getLastPositionFromStack()
                    if (position >= 0) {
                        view_pager.setCurrentItem(position, false)
                        return
                    }
                }
            }
        }
    }
}
