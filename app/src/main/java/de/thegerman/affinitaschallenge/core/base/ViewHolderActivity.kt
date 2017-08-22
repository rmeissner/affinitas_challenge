package de.thegerman.affinitaschallenge.core.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import de.thegerman.onekay.core.base.ContextProxy
import javax.inject.Inject


abstract class ViewHolderActivity<B, VH: ViewHolder<B>> : AppCompatActivity(), ContextProxy {

    @Inject
    lateinit var viewHolder: VH

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inject()

        setContentView(getLayoutID())
        viewHolder.bind(createBinding())
    }

    @LayoutRes
    abstract fun getLayoutID(): Int

    abstract fun createBinding(): B

    abstract fun inject()

    override fun onStart() {
        super.onStart()
        viewHolder.start()
    }

    override fun onStop() {
        viewHolder.stop()
        super.onStop()
    }

    override fun onDestroy() {
        viewHolder.unbind()
        super.onDestroy()
    }
}