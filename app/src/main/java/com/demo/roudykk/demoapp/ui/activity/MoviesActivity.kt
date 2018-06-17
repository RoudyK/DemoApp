package com.demo.roudykk.demoapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import butterknife.ButterKnife
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.api.model.MoviesResult
import kotlinx.android.synthetic.main.activity_movies.*

class MoviesActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ButterKnife.bind(this)
        setContentView(R.layout.activity_movies)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (intent != null && intent.hasExtra(MOVIES_RESULT)) {
            val moviesResult = intent.getParcelableExtra<MoviesResult>(MOVIES_RESULT)
            title = moviesResult.title
        }
    }

    companion object {
        private const val MOVIES_RESULT = "MOVIES_RESULT"

        fun launch(context: Context, moviesResult: MoviesResult) {
            val intent = Intent(context, MoviesActivity::class.java)
            intent.putExtra(MOVIES_RESULT, moviesResult)
            context.startActivity(intent)
        }
    }
}