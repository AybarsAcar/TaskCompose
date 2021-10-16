package com.aybarsacar.todocompose.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.aybarsacar.todocompose.data.models.Priority
import com.aybarsacar.todocompose.util.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(Constants.PREFERENCE_NAME)


@ViewModelScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val _context: Context) {

  private object PreferenceKeys {
    val sortStateKey = stringPreferencesKey(Constants.PREFERENCE_KEY)
  }

  private val _dataStore = _context.dataStore


  suspend fun persistSortState(priority: Priority) {

    _dataStore.edit {
      // persist our priority name in preferences
      it[PreferenceKeys.sortStateKey] = priority.name
    }
  }


  // variable to read the sort state stored in DataStore Preferences
  val readSortState: Flow<String> = _dataStore.data
    .catch {
      if (it is IOException) {
        emit(emptyPreferences())
      } else {
        throw it
      }
    }
    .map {
      val sortState = it[PreferenceKeys.sortStateKey] ?: Priority.NONE.name

      sortState
    }
}