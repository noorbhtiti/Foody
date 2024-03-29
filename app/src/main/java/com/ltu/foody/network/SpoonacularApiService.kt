package com.ltu.foody.network

import com.ltu.foody.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Tag
import java.util.concurrent.TimeUnit


/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


/**
 * Add a httpclient logger for debugging purpose
 * object.
 */
fun getLoggerIntercepter(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return logging
}

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */

private val foodListRetrofit = Retrofit.Builder()
    .client(
        OkHttpClient.Builder()
            .addInterceptor(getLoggerIntercepter())
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()
    )
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.API_BASE_URL)
    .build()

interface SpoonacularApiService{


    @GET("random?apiKey=${Constants.API_KEY}&number=20&") //TODO set the number to 20
    suspend fun getRandomMeals(
        @Query("tags")
        tag : String
    ): RandomMealResponse

    @GET("{id}/information?apiKey=${Constants.API_KEY}")
    suspend fun getMealDetails(
        @Path("id")
        id : String
    ): MealDetailsResponse
//TODO: Videos
}

object SpoonacularApi{
    val foodListRetrofitService: SpoonacularApiService by lazy { foodListRetrofit.create(SpoonacularApiService::class.java) }
}