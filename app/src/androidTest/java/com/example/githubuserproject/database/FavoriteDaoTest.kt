package com.example.githubuserproject.database
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.githubuserproject.data.db.FavoriteDao
import com.example.githubuserproject.data.db.FavoriteDatabase
import com.example.githubuserproject.data.db.FavoriteUser
import com.example.githubuserproject.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class FavoriteDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: FavoriteDatabase
    private lateinit var dao: FavoriteDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FavoriteDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.favoriteDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertFavUser() = runBlockingTest {
        val favUSer = FavoriteUser("nanox", "https://avatars.githubusercontent.com/u/71170?v=4")
        dao.save(favUSer)

        val allFavUser = dao.getAllFavUser().getOrAwaitValue()

        assertThat(allFavUser).contains(favUSer)
    }

    @Test
    fun deleteFavUser() = runBlockingTest {
        val favUSer = FavoriteUser("nanox", "https://avatars.githubusercontent.com/u/71170?v=4")
        dao.save(favUSer)
        dao.removeFav(favUSer.username)

        val allFavUser = dao.getAllFavUser().getOrAwaitValue()

        assertThat(allFavUser).doesNotContain(favUSer)
    }

    @Test
    fun getFavUserbyUsername() = runBlockingTest {
        val favUSer = FavoriteUser("nanox", "https://avatars.githubusercontent.com/u/71170?v=4")
        dao.save(favUSer)

        val user = dao.getFavoriteUserByUsername(favUSer.username).getOrAwaitValue()

        assertThat(user.username).isEqualTo("nanox")
        assertThat(user.avatarUrl).isEqualTo("https://avatars.githubusercontent.com/u/71170?v=4")
    }
}