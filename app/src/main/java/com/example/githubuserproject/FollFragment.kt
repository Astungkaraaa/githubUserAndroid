package com.example.githubuserproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserproject.adapter.FollowAdapter
import com.example.githubuserproject.databinding.FragmentFollBinding
import com.example.githubuserproject.repositories.FollowViewModel


class FollFragment : Fragment() {

    private  var _binding : FragmentFollBinding? = null
    private val binding get() = _binding!!

    private lateinit var followVm : FollowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followVm = ViewModelProvider(requireActivity()).get(FollowViewModel::class.java)

        var position : Int = 0
        var username : String? = ""

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvListuser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvListuser.addItemDecoration(itemDecoration)

        if (position == 1){
            followVm.getFollowers(username!!)
            followVm.user.observe(viewLifecycleOwner){
                val adapter = FollowAdapter(it)
                binding.rvListuser.adapter = adapter
            }

            followVm.loading.observe(viewLifecycleOwner){
                setLoading(it)
            }

            followVm.errorToastMessage.observe(requireActivity()) {
                it?.let {
                    showToast(it)
                }
            }

        } else {
            followVm.getFollowing(username!!)
            followVm.userFollowing.observe(viewLifecycleOwner){
                val adapter = FollowAdapter(it)
                binding.rvListuser.adapter = adapter
            }

            followVm.loading.observe(viewLifecycleOwner){
                setLoading(it)
            }

            followVm.errorToastMessage.observe(requireActivity(), {
                it?.let {
                    showToast(it)
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setLoading(isLoading : Boolean) {
        binding.loadingbawah.visibility = if(isLoading) View.VISIBLE else View.GONE
        binding.rvListuser.visibility = if(isLoading) View.GONE else View.VISIBLE
    }

    companion object {
        val ARG_USERNAME: String = ""
        val ARG_POSITION: String = "Position"
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }


}