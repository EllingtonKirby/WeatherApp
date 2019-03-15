package com.ellington.weatherapp.di.modules

import android.content.Context
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File

@Module
class NetworkingModule {
    @Provides
    fun providesCacheDir(application: Context): File = File(application.cacheDir, "PicassoImageCache")

    @Provides
    fun providesImageCache(file: File): Cache = Cache(file, 1024 * 4)

    @Provides
    fun providesClient(cache: Cache): OkHttpClient = OkHttpClient.Builder().cache(cache).build()

    @Provides
    fun providesDownloader(client: OkHttpClient): OkHttp3Downloader = OkHttp3Downloader(client)

    @Provides
    fun providesPicasso(application: Context, downloader: OkHttp3Downloader): Picasso = Picasso.Builder(application).downloader(downloader).build()
}