package com.volie.lolguidestats.ui.fragment.icon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.volie.lolguidestats.databinding.FragmentProfileIconBinding
import com.volie.lolguidestats.helper.Constant
import com.volie.lolguidestats.helper.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileIconFragment : Fragment() {
    private var _mBinding: FragmentProfileIconBinding? = null
    private val mBinding get() = _mBinding!!
    private val mViewModel: ProfileIconViewModel by viewModels()

    private val mAdapter: ProfileIconRVAdapter by lazy {
        ProfileIconRVAdapter {
            Glide.with(requireContext())
                .load("${Constant.BASE_URL}img/profileicon/${it.image.full}")
                .into(mBinding.ivProfileIcon)

            mBinding.flIconDetail.visibility = View.VISIBLE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _mBinding = FragmentProfileIconBinding.inflate(inflater, container, false)
        mBinding.rvProfileIcon.adapter = mAdapter
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.ivBack.setOnClickListener {
            mBinding.flIconDetail.visibility = View.GONE
        }

        observeLiveData()
        mViewModel.getProfileIcons()
    }

    private fun observeLiveData() {
        mViewModel.icons.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    resource.data?.let {
                        val iconMap = it.data
                        val iconList = iconMap.values.toList()
                        mAdapter.submitList(iconList)
                    }
                }

                Status.ERROR -> {}
                Status.LOADING -> {}
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _mBinding = null
    }
}