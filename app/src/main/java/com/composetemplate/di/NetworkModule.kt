package com.composetemplate.di

import com.composetemplate.BuildConfig
import com.composetemplate.utility.Constants
import com.hadiyarajesh.flower_retrofit.FlowerCallAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .build()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .also {
                /**
                 * Only add [HttpLoggingInterceptor] on debug build
                 */
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(getLoggingInterceptor())
                }
            }
            .build()
    }

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi)) // Register Moshi as a JSON converter for serialization and deserialization of objects.
            .addCallAdapterFactory(FlowerCallAdapterFactory.create()) // Register Flower as a response converter for supporting method return types other than Call<T>.
            .build()
    }
}
