package com.rakamin.moviedbapp.domain.model.response

import com.google.gson.annotations.SerializedName

data class DetailMovieResponse(
    @SerializedName("original_language") val originalLanguage: String? = null,
    @SerializedName("imdb_id") val imdbId: String? = null,
    @SerializedName("video") val video: Boolean? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("backdrop_path") val backdropPath: String? = null,
    @SerializedName("revenue") val revenue: Int? = null,
    @SerializedName("genres") val genres: List<GenresItem?>? = null,
    @SerializedName("popularity") val popularity: Double? = null,
    @SerializedName("production_countries") val productionCountries: List<ProductionCountriesItem?>? = null,
    @SerializedName("id") val id: Int? = null,
    @SerializedName("vote_count") val voteCount: Int? = null,
    @SerializedName("budget") val budget: Int? = null,
    @SerializedName("overview") val overview: String? = null,
    @SerializedName("original_title") val originalTitle: String? = null,
    @SerializedName("runtime") val runtime: Int? = null,
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("original_country") val originCountry: List<String?>? = null,
    @SerializedName("spoken_languages") val spokenLanguages: List<SpokenLanguagesItem?>? = null,
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompaniesItem?>? = null,
    @SerializedName("release_date") val releaseDate: String? = null,
    @SerializedName("vote_average") val voteAverage: Double? = null,
    @SerializedName("belongs_to_collection") val belongsToCollection: BelongsToCollection? = null,
    @SerializedName("tagline") val tagline: String? = null,
    @SerializedName("adult") val adult: Boolean? = null,
    @SerializedName("homepage") val homepage: String? = null,
    @SerializedName("status") val status: String? = null
)

data class GenresItem(
    val name: String? = null,
    val id: Int? = null
)

data class SpokenLanguagesItem(
    val name: String? = null,
    val iso6391: String? = null,
    val englishName: String? = null
)

data class ProductionCompaniesItem(
    val logoPath: String? = null,
    val name: String? = null,
    val id: Int? = null,
    val originCountry: String? = null
)

data class ProductionCountriesItem(
    val iso31661: String? = null,
    val name: String? = null
)

data class BelongsToCollection(
    val backdropPath: String? = null,
    val name: String? = null,
    val id: Int? = null,
    val posterPath: String? = null
)
