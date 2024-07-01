package adapters

import Movie
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ychebpr2.R


class MovieAdapters(private var movies: List<Movie>, val context: Context, private val MovieClickListener:OnMovieClickListener) : RecyclerView.Adapter<MovieAdapters.MovieViewHolder>() {

    interface OnMovieClickListener {
        fun onMovieClick(movieUrl: String)
    }

    private var movieClickListener: OnMovieClickListener? = null



    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val movieName: TextView = view.findViewById(R.id.movieName)
        val movieRating: TextView = view.findViewById(R.id.reiting)
        val movieLayout: LinearLayout = view.findViewById(R.id.layoutId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies.sortedByDescending { it.rating }[position]

        if (movie.rating < 7) {
            holder.movieRating.setTextColor(Color.RED)
        } else {
            holder.movieRating.setTextColor(Color.GREEN)
        }

        holder.movieName.text = movie.movie
        holder.movieRating.text = movie.rating.toString()

        holder.movieLayout.setOnClickListener {
            movieClickListener?.onMovieClick(movie.imdb_url)
        }
    }

    fun updateMovies(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    override fun getItemCount() = movies.size
}
