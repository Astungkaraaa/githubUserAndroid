package com.example.githubuserproject.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuserproject.FollFragment

class ViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var username: String = ""
    override fun createFragment(position: Int): Fragment {
        val fragment = FollFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollFragment.ARG_POSITION, position + 1)
            putString(FollFragment.ARG_USERNAME, username)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }

}