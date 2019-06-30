package com.juangomez.heroes_jetpack.ui.view.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.juangomez.heroes_jetpack.R
import com.juangomez.heroes_jetpack.domain.model.SuperHero
import com.juangomez.heroes_jetpack.ui.presenter.SuperHeroesPresenter
import com.juangomez.heroes_jetpack.ui.utils.setImageBackground

class SuperHeroViewHolder(
    itemView: View,
    private val presenter: SuperHeroesPresenter
) : RecyclerView.ViewHolder(itemView) {

    private val photoImageView: ImageView = itemView.findViewById(R.id.iv_super_hero_photo)
    private val nameTextView: TextView = itemView.findViewById(R.id.tv_super_hero_name)
    private val avengersBadgeView: View = itemView.findViewById(R.id.iv_avengers_badge)

    fun render(superHero: SuperHero) {
        hookListeners(superHero)
        renderSuperHeroPhoto(superHero.photo)
        renderSuperHeroName(superHero.name)
        renderAvengersBadge(superHero.isAvenger)
    }

    private fun hookListeners(superHero: SuperHero) {
        itemView.setOnClickListener { presenter.onSuperHeroClicked(superHero) }
    }

    private fun renderSuperHeroPhoto(photo: String?) {
        photoImageView.setImageBackground(photo)
    }

    private fun renderSuperHeroName(name: String) {
        nameTextView.text = name
    }

    private fun renderAvengersBadge(isAvenger: Boolean) {
        avengersBadgeView.visibility = if (isAvenger) View.VISIBLE else View.GONE
    }
}