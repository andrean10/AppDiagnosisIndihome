package com.kontrakanprojects.appbekamcbr.view.info.sectionpager

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.kontrakanprojects.appbekamcbr.R
import com.kontrakanprojects.appbekamcbr.view.info.daftarpenyakit.DaftarPenyakitFragment
import com.kontrakanprojects.appbekamcbr.view.info.daftarsolusi.DaftarSolusiFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_disease,
    R.string.tab_solution
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return DaftarPenyakitFragment.newInstance()
            1 -> return DaftarSolusiFragment.newInstance()
            else -> return Fragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }


    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }
}