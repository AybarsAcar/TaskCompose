package com.aybarsacar.todocompose.di

import android.content.Context
import androidx.room.Room
import com.aybarsacar.todocompose.data.TodoDatabase
import com.aybarsacar.todocompose.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

  @Singleton
  @Provides
  fun provideDatabase(@ApplicationContext context: Context) =
    Room.databaseBuilder(context, TodoDatabase::class.java, Constants.DATABASE_NAME).build()


  @Singleton
  @Provides
  fun provideDao(database: TodoDatabase) = database.todoDao()
}