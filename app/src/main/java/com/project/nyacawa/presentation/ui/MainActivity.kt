package com.project.nyacawa.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.project.nyacawa.R
import com.project.nyacawa.databinding.ActivityMainBinding
import com.project.nyacawa.domain.logic.ToolBarState
import com.project.nyacawa.domain.logic.ToolBarTypes


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var state: ToolBarTypes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navController = navHostFragment.navController

        val toolbar = binding.includedLayout.toolBar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        navController.addOnDestinationChangedListener{_,_,_ ->
            updateToolBar(toolbar)
        }


        binding.includedLayout.bottomBar.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
            R.id.home_button -> {
                navController.navigate(R.id.goToMainMenu)
                true
            }
            R.id.search_button -> {

                true
            }
            R.id.player_button -> {

                true
            }
            R.id.more_button -> {
                navController.navigate(R.id.goToProfile)
                true
            }
                else -> false
            }
        }

        binding.includedLayout.toolBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId){
                R.id.account_button -> {
                    navController.navigate(R.id.goToProfile)
                    true
                }
                else  -> false
            }
        }

        toolBarStateSet(ToolBarTypes.MAIN_MENU, toolbar, getString(R.string.none))
    }


    private fun updateToolBar(toolbar: Toolbar?){
        val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)?.childFragmentManager?.fragments?.firstOrNull()
        if(fragment is ToolBarState){
            state = fragment.getToolBarState()
            toolBarStateSet(state, toolbar, fragment.getFragmentName())
        }else{
            toolBarStateSet(ToolBarTypes.BACK, toolbar, getString(R.string.none))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(state == ToolBarTypes.SEARCH || state ==ToolBarTypes.BACK){
            menuInflater.inflate(R.menu.menu_main, menu)
        }
        return true
    }

    private fun toolBarStateSet(state: ToolBarTypes, toolbar: Toolbar?, titleText: String?){
        when (state){
            ToolBarTypes.SEARCH -> setSearch(toolbar)
            ToolBarTypes.MAIN_MENU -> setMainMenu(toolbar)
            ToolBarTypes.BACK -> setBack(toolbar, titleText)
            ToolBarTypes.BACK_WITH_ACCOUNT -> setBackAccount(toolbar, titleText)
        }
    }

    @SuppressLint("InflateParams")
    private fun setSearch(toolbar: Toolbar?){
        if(toolbar != null){
            resetToolbar(toolbar)
            toolbar.setLogo(R.drawable.search_no_active_ico)

            val myView : View  = layoutInflater.inflate(R.layout.view_search, null)
            val container = FrameLayout(this)
            val containerParams = Toolbar.LayoutParams(
                Toolbar.LayoutParams.MATCH_PARENT,
                Toolbar.LayoutParams.WRAP_CONTENT
            )

            container.layoutParams = containerParams
            val padding = resources.getDimensionPixelSize(R.dimen.neutral_margin)
            container.setPadding(padding, 0, 0,0)
            container.addView(myView)
            toolbar.addView(container)
        }
    }

    private fun setMainMenu(toolbar: Toolbar?){
        if(toolbar != null){
            resetToolbar(toolbar)
            toolbar.setLogo(R.drawable.bell_ico)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

    }

    private fun setBack(toolbar: Toolbar?, titleText: String?){
        if(toolbar != null) {
            resetToolbar(toolbar)
            toolbar.title = titleText
            supportActionBar?.setDisplayShowTitleEnabled(true)
            toolbar.setLogo(R.drawable.back_ico)

            getToolbarLogoIcon(toolbar)?.setOnClickListener {
                navController.navigateUp()
            }
        }

    }

    private fun setBackAccount(toolbar: Toolbar?, titleText: String?){
        if(toolbar!=null){
            setBack(toolbar, titleText)
        }
    }

    private fun resetToolbar(toolbar: Toolbar) {
        toolbar.title = ""
        toolbar.subtitle = ""

        toolbar.navigationIcon = null
        toolbar.removeAllViews()
    }


    private fun getToolbarLogoIcon(toolbar: Toolbar): View? {
        val hadContentDescription = TextUtils.isEmpty(toolbar.logoDescription)
        val contentDescription =
            (if (!hadContentDescription) toolbar.logoDescription else "logoContentDescription").toString()
        toolbar.setLogoDescription(contentDescription)
        val potentialViews = ArrayList<View>()
        toolbar.findViewsWithText(
            potentialViews,
            contentDescription,
            View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION
        )
        var logoIcon: View? = null
        if (potentialViews.size > 0) {
            logoIcon = potentialViews[0]
        }
        if (hadContentDescription) toolbar.setLogoDescription(null)
        return logoIcon
    }

}