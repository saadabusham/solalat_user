package com.raantech.solalat.user.ui.main

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout.SimpleDrawerListener
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.raantech.solalat.user.R
import com.raantech.solalat.user.common.CommonEnums
import com.raantech.solalat.user.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.solalat.user.data.common.CustomObserverResponse
import com.raantech.solalat.user.databinding.ActivityMainBinding
import com.raantech.solalat.user.ui.addhorse.AddHorseActivity
import com.raantech.solalat.user.ui.base.activity.BaseBindingActivity
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.solalat.user.ui.cart.CartActivity
import com.raantech.solalat.user.ui.main.adapters.DrawerRecyclerAdapter
import com.raantech.solalat.user.ui.main.viewmodels.MainViewModel
import com.raantech.solalat.user.ui.media.MediaActivity
import com.raantech.solalat.user.ui.more.aboutus.AboutUsActivity
import com.raantech.solalat.user.ui.more.faqs.FaqsActivity
import com.raantech.solalat.user.ui.splash.SplashActivity
import com.raantech.solalat.user.utils.LocaleUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main_content.*
import kotlinx.android.synthetic.main.layout_home_toolbar.*
import kotlin.math.abs

@AndroidEntryPoint
class MainActivity : BaseBindingActivity<ActivityMainBinding>(),
    BaseBindingRecyclerViewAdapter.OnItemClickListener {

    private val viewModel: MainViewModel by viewModels()
    lateinit var drawerRecyclerAdapter: DrawerRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            layoutResID = R.layout.activity_main,
            hasToolbar = true,
            toolbarView = toolbar,
            hasTitle = true,
            title = R.string.solalat
        )
        setUpBinding()
        setupNavigation()
        setUpDrawer()
        setUpListeners()
    }

    private fun setUpListeners() {
        binding?.appBarMain?.layoutToolbar?.imgCart?.setOnClickListener {
            CartActivity.start(this)
        }
        binding?.appBarMain?.layoutToolbar?.imgAddHorse?.setOnClickListener {
            AddHorseActivity.start(this)
        }
        binding?.appBarMain?.layoutToolbar?.imgSearch?.setOnClickListener {

        }
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }


    private fun setupNavigation() {
        val navController = findNavController(R.id.main_nav_host_fragment)
        NavigationUI.setupWithNavController(
            bnv_main,
            navController
        )

        bnv_main?.setOnNavigationItemReselectedListener {
            // Do Nothing To Disable ReLunch fragment when reClick on nav icon
        }

    }

    private fun setUpDrawer() {
        drawerRecyclerAdapter = DrawerRecyclerAdapter(this)
        drawerRecyclerAdapter.submitItems(getDrawerList())
        binding?.drawerRecyclerView?.adapter = drawerRecyclerAdapter
        binding?.drawerRecyclerView?.setOnItemClickListener(this)
        val toggle = ActionBarDrawerToggle(
            this, binding?.drawerLayout, binding?.appBarMain?.layoutToolbar?.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        initDrawer(toggle)
    }


    private fun initDrawer(toggle: ActionBarDrawerToggle) {
        val drawable = ResourcesCompat.getDrawable(
            resources, R.drawable.ic_menu,
            theme
        )
        toggle.isDrawerIndicatorEnabled = false
        toggle.setHomeAsUpIndicator(drawable)
        binding?.drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()
        toggle.toolbarNavigationClickListener = View.OnClickListener { v: View? ->
            if (binding?.drawerLayout?.isDrawerVisible(GravityCompat.START) == true) {
                binding?.drawerLayout?.closeDrawer(GravityCompat.START)
            } else {
                binding?.drawerLayout?.openDrawer(GravityCompat.START)
            }
        }

        binding?.drawerLayout?.setScrimColor(Color.TRANSPARENT)
        binding?.drawerLayout?.drawerElevation = 0.toFloat()
//        binding?.drawerLayout?.setScrimColor(resources.getColor(R.color.button_color))
        binding?.drawerLayout?.addDrawerListener(object : SimpleDrawerListener() {
            override fun onDrawerSlide(drawer: View, slideOffset: Float) {
                if (LocaleUtil.getLanguage() == "ar") {
                    binding?.appBarMain?.holder?.rotation = (slideOffset * -1) * 10
                    binding?.appBarMain?.container?.scaleX = abs(slideOffset * 0.4f - 1)
                    binding?.appBarMain?.container?.scaleY = abs(slideOffset * 0.4f - 1)
                    binding?.appBarMain?.container?.pivotX = 0.toFloat()
                    binding?.appBarMain?.container?.pivotY = (1000).toFloat()
                } else {
                    binding?.appBarMain?.container?.x =
                        (binding?.navigationView?.width!! * (slideOffset))
                    binding?.appBarMain?.holder?.rotation = slideOffset * 10
                    binding?.appBarMain?.container?.scaleX = abs(slideOffset * 0.4f - 1)
                    binding?.appBarMain?.container?.scaleY = abs(slideOffset * 0.4f - 1)
                    binding?.appBarMain?.container?.pivotX = 0.toFloat()
                    binding?.appBarMain?.container?.pivotY = (1000).toFloat()
                }
            }

            override fun onDrawerClosed(drawerView: View) {}
        }
        )
    }

    private fun getDrawerList(): List<String> {
        return arrayListOf(
            resources.getString(R.string.menu_my_account),
            resources.getString(R.string.media),
            resources.getString(R.string.menu_notifications),
            resources.getString(R.string.menu_language),
            resources.getString(R.string.menu_report_provider),
            resources.getString(R.string.menu_technical_support),
            resources.getString(R.string.menu_about_us),
            resources.getString(R.string.logout)
        )
    }

    override fun onItemClick(view: View?, position: Int, item: Any) {
        if (item is String) {
            binding?.drawerLayout?.closeDrawer(GravityCompat.START)
            when (position) {
                1 -> MediaActivity.start(this)
                3 -> viewModel.saveLanguage().observe(this, Observer {
                    this.let {
                        (it as BaseBindingActivity<*>).setLanguage(
                            if (viewModel.getAppLanguage() == "ar")
                                CommonEnums.Languages.Arabic.value else CommonEnums.Languages.English.value
                        )
                    }
                })
                5 -> FaqsActivity.start(this)
                6 -> AboutUsActivity.start(this)
                7 -> {
                    viewModel.logoutRemote().observe(this, logoutResultObserver())
                }
            }
        }
    }


    private fun logoutResultObserver(): CustomObserverResponse<Any> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<Any> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: Any?
                ) {
                    viewModel.logoutLocale()
                    SplashActivity.start(this@MainActivity)
                }
            })
    }


    companion object {
        const val EXTRA_FROM_NOTIFICATION = "notifications"
        fun start(context: Context?) {
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context?.startActivity(intent)
        }

    }

}