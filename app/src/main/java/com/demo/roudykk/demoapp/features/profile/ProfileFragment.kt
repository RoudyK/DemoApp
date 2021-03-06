package com.demo.roudykk.demoapp.features.profile

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.db.PreferenceRepo
import com.demo.roudykk.demoapp.extensions.getThemeWrapper
import com.demo.roudykk.demoapp.extensions.viewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.roudykk.presentation.model.PersonView
import com.roudykk.presentation.viewmodel.PersonViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject


class ProfileFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var profileController: ProfileController

    private lateinit var person: PersonView
    private val personViewModel: PersonViewModel  by viewModel()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.cloneInContext(context.getThemeWrapper()).inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if (arguments != null && arguments!!.containsKey(PERSON)) {
            this.person = arguments!!.getParcelable(PERSON)!!

            this.initViewModel()
            this.initRv()
            this.personViewModel.fetchPerson(this.person.id)
        }
    }

    private fun initViewModel() {
        this.personViewModel.getPerson().observe(this,
                Observer { resource ->
                    if (resource.data != null) {
                        this.person = resource.data!!
                    }
                    this.profileController.setData(resource, this.person)
                })
    }

    private fun initRv() {
        this.personRv.layoutManager = LinearLayoutManager(context)
        this.personRv.itemAnimator = DefaultItemAnimator()
        this.profileController.errorAction = { this.personViewModel.fetchPerson(this.person.id) }
        this.personRv.setController(this.profileController)
    }

    companion object {
        private const val PERSON = "PERSON"

        fun newInstance(person: PersonView): ProfileFragment {
            val bundle = Bundle()
            bundle.putParcelable(PERSON, person)
            val fragment = ProfileFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}