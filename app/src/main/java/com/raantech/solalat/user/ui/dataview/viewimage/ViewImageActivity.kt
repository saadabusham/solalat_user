package com.raantech.solalat.user.ui.dataview.viewimage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import com.raantech.solalat.user.R
import com.raantech.solalat.user.data.common.Constants
import com.raantech.solalat.user.databinding.ActivityViewImageBinding
import com.raantech.solalat.user.ui.base.activity.BaseBindingActivity
import com.raantech.solalat.user.utils.extensions.loadImage
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ViewImageActivity : BaseBindingActivity<ActivityViewImageBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            R.layout.activity_view_image,
            hasToolbar = true,
            hasBackButton = true,
            showBackArrow = true,
            hasTitle = false
        )
        binding?.data = intent.getStringExtra(Constants.BundleData.IMAGE)
        supportPostponeEnterTransition()
        intent.getStringExtra(Constants.BundleData.IMAGE)?.let {
            binding?.imgPicture?.loadImage(it) {
                supportStartPostponedEnterTransition()
            }
        }
    }

    companion object {
        fun start(
            context: Activity,
            item: String,
            image: View
        ) {

            val p1: androidx.core.util.Pair<View, String> = androidx.core.util.Pair(
                image,
                image.transitionName
            )
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                context,
                p1
            )
            val intent = Intent(context, ViewImageActivity::class.java)
            intent.putExtra(Constants.BundleData.IMAGE, item)
            context.startActivity(intent, options.toBundle())
        }
    }
}