package com.example.ychebpr2.ui.notifications

import Api.MovieApi
import Movie
import adapters.MovieAdapters
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ychebpr2.R
import com.example.ychebpr2.databinding.FragmentMovieBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class MovieFragment : Fragment(), MovieAdapters.OnMovieClickListener {

    private lateinit var binding: FragmentMovieBinding
    private lateinit var movieApi: MovieApi
    private lateinit var retrofit: Retrofit
    private lateinit var adapter: MovieAdapters
    private lateinit var originalMovies: List<Movie>
    private var allMovies: List<Movie>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireContext()

        retrofit = Retrofit.Builder()
            .baseUrl("https://dummyapi.online/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        movieApi = retrofit.create(MovieApi::class.java)

        binding.rvMovie.layoutManager = LinearLayoutManager(context)

        if (allMovies == null) {
            fetchMovies(context)
        } else {
            adapter = MovieAdapters(allMovies!!, context, this)
            binding.rvMovie.adapter = adapter
        }

        setupSearchView()
    }

    private fun fetchMovies(context: Context) {
        movieApi.getMovie().enqueue(object : Callback<List<Movie>> {
            override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                if (response.isSuccessful) {
                    originalMovies = response.body() ?: emptyList()
                    allMovies = originalMovies
                    adapter = MovieAdapters(originalMovies, context, this@MovieFragment)
                    binding.rvMovie.adapter = adapter
                } else {
                    Log.d("MoviesFragment", "Response not successful")
                }
            }

            override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                Log.d("MoviesFragment", "Request failed", t)
            }
        })
    }


    private fun setupSearchView() {
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterMovies(newText)
                return true
            }
        })
    }

    private fun filterMovies(query: String?) {
        val filteredMovies = if (query.isNullOrEmpty()) {
            originalMovies
        } else {
            originalMovies.filter { it.movie.contains(query, ignoreCase = true) }
        }
        adapter.updateMovies(filteredMovies)
    }

    override fun onMovieClick(movieUrl: String) {
        val bundle = Bundle().apply {
            putString("url", movieUrl)

        }
        findNavController().navigate(R.id.action_navigation_notifications_to_movieFragment4,bundle)
    }
}
