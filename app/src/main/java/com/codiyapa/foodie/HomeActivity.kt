package com.codiyapa.foodie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codiyapa.foodie.home_fragment.AccountFragment
import com.codiyapa.foodie.home_fragment.CartFragment
import com.codiyapa.foodie.home_fragment.HomeFragment
import com.codiyapa.foodie.home_fragment.LearboardFragment

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }


    private fun setUpTabs() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment(),"Home")
        adapter.addFragment(CartFragment(), "Cart")
        adapter.addFragment(LearboardFragment(), "Leaderboard")
        adapter.addFragment(AccountFragment(), "My Account")


        binding.viewPager.adapter = adapter
        binding.tabs.setupWithViewPager(binding.viewPager)
        binding.tabs.getTabAt(0)!!.setIcon(R.drawable.sendicon)
        binding.tabs.getTabAt(1)!!.setIcon(R.drawable.task)
        binding.tabs.getTabAt(2)!!.setIcon(R.drawable.chooseicon)
    }
}