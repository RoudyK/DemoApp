package com.demo.roudykk.demoapp.ui.fragment

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
import com.demo.roudykk.demoapp.api.Api
import com.demo.roudykk.demoapp.api.models.Person
import com.demo.roudykk.demoapp.controllers.ProfileController
import com.demo.roudykk.demoapp.db.PreferenceRepo
import com.demo.roudykk.demoapp.extensions.initThreads
import com.demo.roudykk.demoapp.images.AppImageLoader
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_person_details.*


class PersonDetailsFragment : BottomSheetDialogFragment() {
    private lateinit var person: Person
    private lateinit var fContext: Context
    private lateinit var profileController: ProfileController
    private var disposable: Disposable? = null

    override fun onAttach(context: Context) {
        this.fContext = context
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
            this.initRv()
            this.populatePreview(person)
            this.loadPerson(person.id)
        }
    }

    private fun initRv() {
        this.personRv.layoutManager = LinearLayoutManager(fContext)
        this.personRv.itemAnimator = DefaultItemAnimator()
        this.profileController = ProfileController(fContext)
        this.personRv.setController(this.profileController)
    }

    private fun populatePreview(person: Person) {
        AppImageLoader.loadImage(this.fContext, person.getImageUrl(), R.drawable.ic_person_48dp, personIv)
        this.personNameTv.text = person.name
    }

    private fun loadPerson(personId: Int) {
        this.progressbar.visibility = View.VISIBLE
        this.disposable = Api.personApi().getPersonDetails(personId)
                .initThreads()
                .subscribe({ person ->
                    this.progressbar.visibility = View.GONE
                    this.person = person
                    this.profileController.setData(this.person)
                }, {
                    this.reloadTv.visibility = View.VISIBLE
                    this.progressbar.visibility = View.GONE
                })
    }

    override fun onDestroy() {
        this.disposable?.dispose()
        super.onDestroy()
    }

    @OnClick(R.id.reloadTv)
    fun reload() {
        this.loadPerson(this.person.id)
    }

    companion object {
        private const val PERSON = "PERSON"

        fun newInstance(person: Person): PersonDetailsFragment {
            val bundle = Bundle()
            bundle.putParcelable(PERSON, person)
            val fragment = PersonDetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}