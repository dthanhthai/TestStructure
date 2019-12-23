package vn.gogo.trip.ui.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager

abstract class BaseActivity : AppCompatActivity() {

    open var containerResId: Int = 0

    fun replaceFragment(
        fragment: BaseFragment, addToBackStack: Boolean = false,
        containerId: Int = containerResId, tag: String? = fragment.TAG
    ) {
        if (!isFinishing) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
//            if(supportFragmentManager.backStackEntryCount != 0) {
//                fragmentTransaction.setCustomAnimations(
//                    R.anim.enter_from_right,
//                    R.anim.exit_to_left,
//                    R.anim.enter_from_left,
//                    R.anim.exit_to_right
//                )
//            }
            fragmentTransaction.replace(containerId, fragment, tag)

            if (addToBackStack) {
                fragmentTransaction.addToBackStack(null)
            }
            fragmentTransaction.commit()
        }
    }

    fun addFragment(
        fragment: BaseFragment,
        addToBackStack: Boolean,
        containerId: Int = containerResId
    ) {
        if (!isFinishing) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.add(containerId, fragment, fragment.TAG)
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(null)
            }
            fragmentTransaction.commit()
        }
    }

    fun clearBackStack() {
        try {
            supportFragmentManager.run {
                if (backStackEntryCount > 0) {
                    for (i in 0 until backStackEntryCount) {
                        popBackStack()
                    }
                }
            }
        } catch (e: IllegalStateException) {
        }
    }

    private fun finishOrPopBackStack(fragmentManager: FragmentManager) {
        if (fragmentManager.backStackEntryCount > 1) {
            fragmentManager.popBackStack()
        } else {
            finish()
        }
    }

    override fun onBackPressed() {
        finishOrPopBackStack(supportFragmentManager)
//        super.onBackPressed()
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
//        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
    }

    override fun finish() {
        super.finish()
//        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right)
    }
}