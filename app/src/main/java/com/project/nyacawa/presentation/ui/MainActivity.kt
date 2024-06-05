package com.project.nyacawa.presentation.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
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
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.project.nyacawa.R
import com.project.nyacawa.data.AnimeData
import com.project.nyacawa.data.User
import com.project.nyacawa.data.UserDataCache
import com.project.nyacawa.databinding.ActivityMainBinding
import com.project.nyacawa.domain.logic.SearchViewModel
import com.project.nyacawa.domain.logic.ToolBarTypes
import com.project.nyacawa.domain.logic.UserViewModel
import com.project.nyacawa.presentation.ui.fragments.AnimePlayerFragment
import androidx.fragment.app.activityViewModels

fun AppCompatActivity.hideKeyboard() {
    val view: View? = this.currentFocus
    view?.let {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var toolBarTypes: ToolBarTypes = ToolBarTypes.MAIN_MENU
    private lateinit var toolBarTemp: ToolBarTypes
    private var tempBottomBarId: Int = 0
    private var lastAnimeView: AnimeData? = null

    private val searchViewModel: SearchViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()

    companion object {
        val USER_DATA: String = "USER_DATA"
    }

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

        navController.addOnDestinationChangedListener { _, destination, args ->
            Log.d("Navigation", "Destination changed: ${destination.label}")

            toolBarTemp = toolBarTypes
            toolBarTypes = when (destination.id) {
                R.id.registration -> ToolBarTypes.NONE
                R.id.authorization -> ToolBarTypes.NONE
                R.id.mainMenu -> ToolBarTypes.MAIN_MENU
                R.id.profile -> ToolBarTypes.BACK
                R.id.animeSearchList -> ToolBarTypes.SEARCH
                R.id.catalog -> ToolBarTypes.SEARCH
                R.id.animePlayerFragment -> ToolBarTypes.BACK

                else -> ToolBarTypes.BACK
            }

            when (destination.id) {
                R.id.registration -> binding.includedLayout.bottomBar.visibility = View.GONE
                R.id.authorization -> binding.includedLayout.bottomBar.visibility = View.GONE
                R.id.animePlayerFragment -> lastAnimeView =
                    args?.getParcelable(AnimePlayerFragment.ARG_ITEM_1)

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
                    if (lastAnimeView == null) {
                        Toast.makeText(
                            this,
                            getString(R.string.you_haven_t_seen_anything), Toast.LENGTH_SHORT
                        ).show()
                        return@setOnItemSelectedListener false
                    } else {
                        val bundle = Bundle()
                        bundle.putParcelable(AnimePlayerFragment.ARG_ITEM_1, lastAnimeView)
                        navController.navigate(R.id.goToAnimePlayer, bundle)
                    }
                    true
                }

                R.id.more_button -> {
                    val bundle = Bundle()
                    bundle.putParcelable(USER_DATA, userViewModel.user.value)
                    navController.navigate(R.id.goToProfile, bundle)
                    true
                }

                else -> false
            }
        }
        //

        // Tool bar account button realisation
//        binding.includedLayout.toolBar.setOnMenuItemClickListener { menuItem ->
//            when (menuItem.itemId){
//                R.id.account_button -> {
//                    navController.navigate(R.id.goToProfile)
//                    true
//                }
//                else -> false
//            }
//        }

        setUserData()
    }

    private fun updateToolBar(toolbar: Toolbar?, name: String) {
        if (toolBarTemp != toolBarTypes) {
            toolBarStateSet(toolBarTypes, toolbar, name)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (toolBarTypes == ToolBarTypes.SEARCH || toolBarTypes == ToolBarTypes.BACK) {
            menuInflater.inflate(R.menu.menu_main, menu)
        }
        return true
    }

    private fun setUserData() {
        val userCache = UserDataCache(this)
        val userData = userCache.getUserCache()
        Log.d("[MAIN]", "Load from cash ${userData.userId} ")
        if (userData.isUserCashed()) {
            userViewModel.getUserById(userData.userId) {
                if(it!=null){
                    userViewModel.setUser(it)
                }
            }
        } else {
            navController.navigate(R.id.action_mainMenu_to_authorization)
            toolBarTypes = ToolBarTypes.NONE
            updateToolBar(binding.includedLayout.toolBar, getString(R.string.authorisation))
        }
    }

    private fun toolBarStateSet(state: ToolBarTypes, toolbar: Toolbar?, titleText: String?) {
        when (state) {
            ToolBarTypes.SEARCH -> setSearch(toolbar)
            ToolBarTypes.MAIN_MENU -> setMainMenu(toolbar)
            ToolBarTypes.BACK -> setBack(toolbar, titleText)
            ToolBarTypes.BACK_WITH_ACCOUNT -> setBackAccount(toolbar, titleText)
            ToolBarTypes.NONE -> resetToolbar(toolbar)
        }
    }

    private fun setSearch(toolbar: Toolbar?) {
        if (toolbar != null) {
            resetToolbar(toolbar)
            toolbar.setLogo(R.drawable.search_no_active_ico)

            val myView: View = layoutInflater.inflate(R.layout.view_search, null)
            val container = FrameLayout(this)
            val containerParams = Toolbar.LayoutParams(
                Toolbar.LayoutParams.MATCH_PARENT,
                Toolbar.LayoutParams.WRAP_CONTENT
            )

            val editText = myView.findViewById<EditText>(R.id.search_edit_text)
            editText.setImeOptions(EditorInfo.IME_ACTION_DONE)

            var hasNavigated = false

            editText.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus && !hasNavigated) {
                    hasNavigated = true
                    navController.navigate(R.id.goToAnimeSearchList)
                }
            }

            editText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val view: View? = this.currentFocus
                    if (view != null) {
                        // creating a variable
                        // for input manager and initializing it.
                        val inputMethodManager =
                            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                        //hiding keyboard.
                        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                    }
                    searchViewModel.searchText.value = editText.text.toString()
                    editText.clearFocus()
                    return@setOnEditorActionListener true
                }
                false
            }

            // Handlers for a text actions
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) = Unit

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val text = s?.toString() ?: String()
                    searchViewModel.searchText.value = text
                }

                override fun afterTextChanged(s: Editable?) = Unit
            })


            container.layoutParams = containerParams
            val padding = resources.getDimensionPixelSize(R.dimen.neutral_margin)
            container.setPadding(padding, 0, 0, 0)
            container.addView(myView)
            toolbar.addView(container)
        }
    }

    override fun navigateUpTo(upIntent: Intent?): Boolean {
        currentFocus?.clearFocus()
        return super.navigateUpTo(upIntent)
    }

    override fun onBackPressed() {
        val currentDestinationId = navController.currentDestination?.id
        val exitFragmentId = R.id.authorization

        if (currentDestinationId == exitFragmentId) {
            finishAffinity()
        } else {
            super.onBackPressed()
        }
    }


    private fun setMainMenu(toolbar: Toolbar?) {
        if (toolbar != null) {
            resetToolbar(toolbar)
            toolbar.setLogo(R.drawable.bell_ico)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

    }

    private fun setBack(toolbar: Toolbar?, titleText: String?) {
        if (toolbar != null) {
            resetToolbar(toolbar)
            toolbar.title = titleText
            supportActionBar?.setDisplayShowTitleEnabled(true)
            toolbar.setLogo(R.drawable.back_ico)

            val temp = binding.includedLayout.bottomBar.selectedItemId
            getToolbarLogoIcon(toolbar)?.setOnClickListener {
                navController.navigateUp()
                binding.includedLayout.bottomBar.selectedItemId = tempBottomBarId
            }
            tempBottomBarId = temp
        }

    }

    private fun setBackAccount(toolbar: Toolbar?, titleText: String?) {
        if (toolbar != null) {
            setBack(toolbar, titleText)
        }
    }

    private fun resetToolbar(toolbar: Toolbar?) {
        if (toolbar != null) {
            toolbar.title = ""
            toolbar.subtitle = ""

            toolbar.navigationIcon = null
            toolbar.removeAllViews()
        }
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