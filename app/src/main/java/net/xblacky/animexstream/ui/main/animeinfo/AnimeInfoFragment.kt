package net.xblacky.animexstream.ui.main.animeinfo

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.fragment_animeinfo.*
import kotlinx.android.synthetic.main.fragment_animeinfo.view.*
import kotlinx.android.synthetic.main.fragment_animeinfo.view.animeInfoRoot
import kotlinx.android.synthetic.main.loading.view.*
import net.xblacky.animexstream.R
import net.xblacky.animexstream.ui.main.animeinfo.epoxy.AnimeInfoController
import net.xblacky.animexstream.ui.main.home.HomeFragmentDirections
import net.xblacky.animexstream.utils.ItemOffsetDecoration
import net.xblacky.animexstream.utils.Tags.GenreTags
import net.xblacky.animexstream.utils.Utils
import net.xblacky.animexstream.utils.model.AnimeInfoModel
import net.xblacky.animexstream.utils.model.EpisodeModel

class AnimeInfoFragment : Fragment(), AnimeInfoController.EpisodeClickListener {

    private lateinit var rootView: View
    private lateinit var viewModelFactory: AnimeInfoViewModelFactory
    private lateinit var viewModel: AnimeInfoViewModel
    private lateinit var episodeController: AnimeInfoController


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.fragment_animeinfo, container, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPreviews()
        viewModelFactory =
            AnimeInfoViewModelFactory(AnimeInfoFragmentArgs.fromBundle(requireArguments()).categoryUrl!!)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AnimeInfoViewModel::class.java)
        setupRecyclerView()
        setObserver()
        transitionListener()
        setOnClickListeners()
    }

    private fun setPreviews() {
        val imageUrl = AnimeInfoFragmentArgs.fromBundle(requireArguments()).animeImageUrl
        val animeTitle = AnimeInfoFragmentArgs.fromBundle(requireArguments()).animeName
        animeInfoTitle.text = animeTitle
        rootView.animeInfoImage.apply {
            Glide.with(this).load(imageUrl).into(this)
        }


    }

    private fun setObserver() {
        viewModel.animeInfoModel.observe(viewLifecycleOwner, {
            it?.let {
                updateViews(it)
            }
        })

        viewModel.episodeList.observe(viewLifecycleOwner, {
            it?.let {
                rootView.animeInfoRoot.visibility = View.VISIBLE
                episodeController.setData(it)
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, {

            if (it.isLoading) {
                rootView.loading.visibility = View.VISIBLE
            } else {
                rootView.loading.visibility = View.GONE
            }
        })



        viewModel.isFavourite.observe(viewLifecycleOwner, {
            if (it) {
                favourite.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_favorite,
                        null
                    )
                )
            } else {
                favourite.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_unfavorite,
                        null
                    )
                )
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.shared_element)

    }

    private fun updateViews(animeInfoModel: AnimeInfoModel) {

//        Glide.with(this)
//            .load(animeInfoModel.imageUrl)
//            .transition(withCrossFade())
//            .into(rootView.animeInfoImage)

        animeInfoReleased.text = animeInfoModel.releasedTime
        animeInfoStatus.text = animeInfoModel.status
        animeInfoType.text = animeInfoModel.type
        animeInfoTitle.text = animeInfoModel.animeTitle
        toolbarText.text = animeInfoModel.animeTitle
        flowLayout.removeAllViews()
        animeInfoModel.genre.forEach {
            flowLayout.addView(
                GenreTags(requireContext()).getGenreTag(
                    genreName = it.genreName,
                    genreUrl = it.genreUrl
                )
            )
        }


        episodeController.setAnime(animeInfoModel.animeTitle)
        animeInfoSummary.text = animeInfoModel.plotSummary
        rootView.favourite.visibility = View.VISIBLE
        rootView.typeLayout.visibility = View.VISIBLE
        rootView.releasedLayout.visibility = View.VISIBLE
        rootView.statusLayout.visibility = View.VISIBLE
        rootView.animeInfoRoot.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        episodeController = AnimeInfoController(this)
        episodeController.spanCount = Utils.calculateNoOfColumns(requireContext(), 150f)
        rootView.animeInfoRecyclerView.adapter = episodeController.adapter
        val itemOffsetDecoration = ItemOffsetDecoration(context, R.dimen.episode_offset_left)
        rootView.animeInfoRecyclerView.addItemDecoration(itemOffsetDecoration)
        rootView.animeInfoRecyclerView.apply {
            layoutManager =
                GridLayoutManager(context, Utils.calculateNoOfColumns(requireContext(), 150f))
            (layoutManager as GridLayoutManager).spanSizeLookup = episodeController.spanSizeLookup

        }
    }

    private fun transitionListener() {
        rootView.motionLayout.setTransitionListener(
            object : MotionLayout.TransitionListener {
                override fun onTransitionTrigger(
                    p0: MotionLayout?,
                    p1: Int,
                    p2: Boolean,
                    p3: Float
                ) {

                }

                override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
                    rootView.topView.cardElevation = 0F
                }

                override fun onTransitionChange(
                    p0: MotionLayout?,
                    startId: Int,
                    endId: Int,
                    progress: Float
                ) {
                    if (startId == R.id.start) {
                        rootView.topView.cardElevation = 20F * progress
                        rootView.toolbarText.alpha = progress
                    } else {
                        rootView.topView.cardElevation = 10F * (1 - progress)
                        rootView.toolbarText.alpha = (1 - progress)
                    }
                }

                override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                }

            }
        )
    }

    private fun setOnClickListeners() {
        rootView.favourite.setOnClickListener {
            onFavouriteClick()
        }

        rootView.back.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun onFavouriteClick() {
        if (viewModel.isFavourite.value!!) {
            Snackbar.make(
                rootView,
                getText(R.string.removed_from_favourites),
                Snackbar.LENGTH_SHORT
            ).show()
        } else {
            Snackbar.make(rootView, getText(R.string.added_to_favourites), Snackbar.LENGTH_SHORT)
                .show()
        }
        viewModel.toggleFavourite()
    }

    override fun onResume() {
        super.onResume()
        if (episodeController.isWatchedHelperUpdated()) {
            episodeController.setData(viewModel.episodeList.value)
        }
    }

    override fun onEpisodeClick(episodeModel: EpisodeModel) {
        findNavController().navigate(
            AnimeInfoFragmentDirections.actionAnimeInfoFragmentToVideoPlayerActivity(
                episodeUrl = episodeModel.episodeurl,
                animeName = AnimeInfoFragmentArgs.fromBundle(requireArguments()).animeName,
                episodeNumber = episodeModel.episodeNumber
            )
        )
    }

}