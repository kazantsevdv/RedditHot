package com.kazantsev.reddithot.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import com.kazantsev.reddithot.App
import com.kazantsev.reddithot.databinding.FragmertRedditBinding
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject
import javax.inject.Provider


class RedditFragment : Fragment() {
    @Inject
    lateinit var viewModeProvider: Provider<RadditViewModel.Factory>

    private val viewModel: RadditViewModel by viewModels { viewModeProvider.get() }

    private var _viewBinding: FragmertRedditBinding? = null
    private val viewBinding get() = checkNotNull(_viewBinding)

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        AdapterList()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        App.component.inject(this)
        _viewBinding = FragmertRedditBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        loadData()
    }


    private fun loadData() {

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.getPosts().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun setupUI() {


        viewBinding.rvData.adapter = adapter
            .withLoadStateHeaderAndFooter(
                header = LoaderStateAdapter({ adapter.retry() }, {
                    Snackbar.make(
                        viewBinding.root,
                        it,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }),
                footer = LoaderStateAdapter({ adapter.retry() }, {
                    Snackbar.make(
                        viewBinding.root,
                        it,
                        Snackbar.LENGTH_SHORT
                    ).show()
                })

            )
        viewBinding.refresh.setOnRefreshListener { adapter.refresh() }
        loadStateRefresh()
    }


    private fun loadStateRefresh() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest { loadStates ->
                val refresh = loadStates.refresh
                viewBinding.refresh.isRefreshing = loadStates.refresh is LoadState.Loading
                if (refresh is LoadState.Error) {
                    Snackbar.make(
                        viewBinding.root,
                        refresh.error.localizedMessage ?: "",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }
}