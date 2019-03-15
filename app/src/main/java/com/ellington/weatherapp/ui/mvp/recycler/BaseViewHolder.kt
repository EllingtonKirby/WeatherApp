package com.ellington.weatherapp.ui.mvp.recycler

import android.content.Context
import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.extensions.LayoutContainer

abstract class BaseViewHolder<in T>(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer {
    protected val context: Context = itemView.context
    protected val resources: Resources = itemView.resources
    override val containerView: View = itemView
}