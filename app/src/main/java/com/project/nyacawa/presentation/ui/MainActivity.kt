package com.project.nyacawa.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.nyacawa.R
import com.project.nyacawa.databinding.ActivityMainBinding
import com.project.nyacawa.domain.logic.SearchViewModel
import com.project.nyacawa.domain.logic.ToolBarTypes


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var toolBarTypes: ToolBarTypes = ToolBarTypes.MAIN_MENU
    private var tempBottomBarId : Int = 0

    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tool Bar init
        val toolbar = binding.includedLayout.toolBar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        //

        // Navigator and navigation init
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener{_,destination,_ ->
            Log.d("Navigation", "Destination changed: ${destination.label}")

            toolBarTypes = when(destination.id){
                R.id.registration  -> ToolBarTypes.BACK
                R.id.authorization -> ToolBarTypes.BACK
                R.id.mainMenu -> ToolBarTypes.MAIN_MENU
                R.id.profile -> ToolBarTypes.BACK
                R.id.animeSearchList -> ToolBarTypes.SEARCH
                R.id.catalog -> ToolBarTypes.SEARCH

                else -> ToolBarTypes.BACK
            }

            when(destination.id){
                R.id.registration  -> binding.includedLayout.bottomBar.visibility = View.GONE
                R.id.authorization -> binding.includedLayout.bottomBar.visibility = View.GONE
                else -> binding.includedLayout.bottomBar.visibility = View.VISIBLE
            }

            updateToolBar(toolbar, destination.label.toString())
        }
        //

        // Bottom bar init
        binding.includedLayout.bottomBar.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
            R.id.home_button -> {
                navController.navigate(R.id.goToMainMenu)
                true
            }
            R.id.search_button -> {
                navController.navigate(R.id.goToCatalog)
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
        //

        // Tool bar account button realisation
        binding.includedLayout.toolBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId){
                R.id.account_button -> {
                    navController.navigate(R.id.goToProfile)
                    true
                }
                else -> false
            }
        }
        //
    }

    private fun updateToolBar(toolbar: Toolbar?, name: String){
        toolBarStateSet(toolBarTypes, toolbar,name)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(toolBarTypes == ToolBarTypes.SEARCH || toolBarTypes ==ToolBarTypes.BACK){
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

            val editText = myView.findViewById<EditText>(R.id.search_edit_text)
            editText.setImeOptions(EditorInfo.IME_ACTION_DONE)

            editText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val view: View? = this.currentFocus
                    if (view != null) {
                        // creating a variable
                        // for input manager and initializing it.
                        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                        //hiding keyboard.
                        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                    }

                    editText.clearFocus()
                    return@setOnEditorActionListener true
                }
                false
            }
            // Handlers for a text actions
            editText.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) = Unit
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val text = s?.toString()?: String()
                    if(text.isNotEmpty()){
                        searchViewModel.searchText.value = text;
                    }
                }
                override fun afterTextChanged(s: Editable?) = Unit
            })


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

            val temp = binding.includedLayout.bottomBar.selectedItemId;
            getToolbarLogoIcon(toolbar)?.setOnClickListener {
                navController.navigateUp()
                binding.includedLayout.bottomBar.selectedItemId = tempBottomBarId
            }
            tempBottomBarId = temp
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