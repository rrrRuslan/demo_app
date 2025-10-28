package com.demo.presentation.widget.topBar.data

import com.demo.presentation.R


sealed class TopBarIcon(
    open val action: () -> Unit,
) {

    data class Back(
        override val action: () -> Unit,
    ) : TopBarIcon(action)

    data class Search(
        override val action: () -> Unit,
    ) : TopBarIcon(action)

    data class Menu(
        override val action: () -> Unit,
    ) : TopBarIcon(action)

    data class Share(
        override val action: () -> Unit,
    ) : TopBarIcon(action)

    data class Star(
        val isActive: Boolean,
        override val action: () -> Unit,
    ) : TopBarIcon(action)

    data class Add(
        override val action: () -> Unit,
    ) : TopBarIcon(action)

    val icon: Int
        get() = when (this) {
            is Back -> R.drawable.ic_back_arrow
            is Search -> R.drawable.ic_search
            is Share -> R.drawable.ic_share
            is Add -> R.drawable.ic_add
            is Menu -> R.drawable.ic_menu
            is Star -> if (isActive) {
                R.drawable.ic_favorite_star_filled
            } else {
                R.drawable.ic_favorite_star
            }
        }

    val description: Int
        get() = when (this) {
            is Back -> R.string.back
            is Search -> R.string.search
            is Share -> R.string.download
            is Add -> R.string.add
            is Menu -> R.string.menu
            is Star -> R.string.places
        }

}
