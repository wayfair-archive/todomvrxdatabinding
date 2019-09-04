package com.wayfair.todomvrxdatabinding

import android.view.View.OnClickListener
import androidx.annotation.DrawableRes
import androidx.annotation.MenuRes
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import com.wayfair.todomvrxdatabinding.ktx.hideKeyboard
import javax.inject.Inject

/**
 * This class takes care of the navigation going through the [Toolbar] of those fragments that
 * live within a [DrawerLayout] and are tied to a [NavigationView].
 *
 * The navigation is set via databinding.
 */
class NavigationViewBinding @Inject constructor(private val fragment: Fragment) {

    private val drawerLayout: DrawerLayout by lazy {
        fragment.requireActivity().findViewById<DrawerLayout>(R.id.drawerLayout)
    }

    private fun findNavController() = fragment.findNavController()

    @get:MenuRes
    var toolbarMenuResId: Int = R.menu.empty_menu
    var menuItemClickListener: Toolbar.OnMenuItemClickListener? = null
    var toolbarTitle: String? = null

    @get:DrawableRes
    val toolbarNavigationIcon: Int
        get() = if (findNavController().currentDestination?.isDrawerDestination() == true) {
            R.drawable.ic_menu
        } else {
            R.drawable.ic_back
        }

    val toolbarNavigationIconClickListener = OnClickListener {
        if (findNavController().currentDestination?.isDrawerDestination() == true) {
            drawerLayout.openDrawer(GravityCompat.START)
        } else {
            it.hideKeyboard()
            findNavController().navigateUp()
        }
    }

    private fun NavDestination.isDrawerDestination() = id == R.id.tasksFragment || id == R.id.statisticsFragment
}
