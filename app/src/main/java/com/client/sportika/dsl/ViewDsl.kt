package com.webcubictechnologies.remitnow.dsl

import android.view.ViewManager
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import de.hdodenhof.circleimageview.CircleImageView
import org.jetbrains.anko.custom.ankoView

/**
 * Created by webcubictech2 on 5/1/18.
 */
inline fun ViewManager.ahbottomnavigationview(init: AHBottomNavigation.() -> Unit): AHBottomNavigation
        = ankoView({AHBottomNavigation(it)}, theme = 0, init = init)

inline fun ViewManager.circularImageView(init: CircleImageView.() -> Unit): CircleImageView
        = ankoView({CircleImageView(it)}, theme = 0, init = init)
