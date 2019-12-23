package vn.gogo.trip.ui.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment

open class BaseFullScreenDialog: DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requestFeature(Window.FEATURE_NO_TITLE)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
//        val displayRectangle = Rect()
//        val window = activity?.window
//        window?.decorView?.getWindowVisibleDisplayFrame(displayRectangle)
//        dialog?.window?.apply {
//            setWindowAnimations(R.style.TranslateUpDownAnimation)
//            val windowLayoutParams = WindowManager.LayoutParams()
//            windowLayoutParams.copyFrom(attributes)
//            setBackgroundDrawableResource(R.color.trout)
//            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//        }
    }
}