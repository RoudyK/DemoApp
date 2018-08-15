package com.demo.roudykk.demoapp.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.view.ContextThemeWrapper
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.OnClick
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.controllers.ProfileController
import com.demo.roudykk.demoapp.db.PreferenceRepo
import com.demo.roudykk.demoapp.images.AppImageLoader
import com.demo.roudykk.demoapp.injection.ViewModelFactory
import com.roudykk.presentation.model.PersonView
import com.roudykk.presentation.state.ResourceState
import com.roudykk.presentation.viewmodel.PersonViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_person_details.*
import javax.inject.Inject


class PersonDetailsFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var profileController: ProfileController

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var person: PersonView
    private lateinit var personViewModel: PersonViewModel

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contextThemeWrapper = if (PreferenceManager.getDefaultSharedPreferences(context)
                        .getBoolean(PreferenceRepo.PREFERENCE_DARK_THEME, false)) {
            ContextThemeWrapper(activity, R.style.AppTheme_Dark)
        } else {
            ContextThemeWrapper(activity, R.style.AppTheme)
        }
        val view = inflater.cloneInContext(contextThemeWrapper).inflate(R.layout.fragment_person_details, container, false)
        ButterKnife.bind(this, view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null && arguments!!.containsKey(PERSON)) {
            this.person = arguments!!.getParcelable(PERSON)

            this.initViewModel()
            this.initRv()
            this.populatePreview(this.person)
            this.personViewModel.fetchPerson(this.person.id)
        }
    }

    private fun initViewModel() {
        this.personViewModel = ViewModelProviders
                .of(this, this.viewModelFactory)
                .get(PersonViewModel::class.java)

        this.personViewModel.getPerson().observe(this,
                Observer { resource ->
                    when (resource?.status) {
                        ResourceState.LOADING -> {
                            this.progressbar.visibility = View.VISIBLE
                            this.reloadTv.visibility = View.GONE
                        }
                        ResourceState.SUCCESS -> {
                            this.progressbar.visibility = View.GONE
                            this.person = resource.data!!
                            this.profileController.setData(this.person)
                        }
                        ResourceState.ERROR -> {
                            this.reloadTv.visibility = View.VISIBLE
                            this.progressbar.visibility = View.GONE
                        }
                    }

                })
    }

    private fun initRv() {
        this.personRv.layoutManager = LinearLayoutManager(context)
        this.personRv.itemAnimator = DefaultItemAnimator()
        this.personRv.setController(this.profileController)
    }

    private fun populatePreview(person: PersonView) {
        if (context != null) {
            AppImageLoader.loadImage(context!!, person.getImageUrl(), R.drawable.ic_person_48dp, personIv)
            this.personNameTv.text = person.name
        }
    }

    @OnClick(R.id.reloadTv)
    fun reload() {
        this.personViewModel.fetchPerson(this.person.id)
    }

    companion object {
        private const val PERSON = "PERSON"

        fun newInstance(person: PersonView): PersonDetailsFragment {
            val bundle = Bundle()
            bundle.putParcelable(PERSON, person)
            val fragment = PersonDetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}