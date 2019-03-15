package com.ellington.weatherapp.ui.mvp.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment : Fragment() {

  @get:LayoutRes
  protected abstract val layoutResourceId: Int

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(layoutResourceId, container, false)
  }

  /**
   *  On attach iterate through the fragments parent tree to find the activity that implements
   *  {@link HasSupportFragmentInjector} and obtain the relevant {@link AndroidInjector} from that
   *  activities {@link DispatchingAndroidInjector}
   *
   *  The sub-component that injects the needed dependencies into this fragment is constructed
   *  at compilation via the referenced fragment provider.
   *
   *  Fragment providers are linked to the parent activities listing in the activity builder.
   *
   *  When the parent activities sub-component is created, the associated provider demands the
   *  creation of a sub component for the fragment.
   *
   *  The sub component is created referencing the fragment's module, informing it of the
   *  dependencies that the fragment requires
   *
   **/
  override fun onAttach(context: Context?) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }


  public fun View.showSnackbar(
    s: String,
    len: Int = Snackbar.LENGTH_LONG,
    f: Snackbar.() -> Unit
  ) {
    val snack = Snackbar.make(this, s, len)
    snack.f()
    snack.show()
  }
}