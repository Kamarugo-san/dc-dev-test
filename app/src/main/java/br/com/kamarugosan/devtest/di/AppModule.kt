package br.com.kamarugosan.devtest.di

import br.com.kamarugosan.devtest.api.WeatherbitApi
import br.com.kamarugosan.devtest.repository.WeatherbitRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideWeatherBitApi(): WeatherbitApi =
        Retrofit.Builder()
            .baseUrl(WeatherbitApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherbitApi::class.java)

    @Provides
    fun provideWeatherbitRepository(): WeatherbitRepository =
        WeatherbitRepository(provideWeatherBitApi())
}