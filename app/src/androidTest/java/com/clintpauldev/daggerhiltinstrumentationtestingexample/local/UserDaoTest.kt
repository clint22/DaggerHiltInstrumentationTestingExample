package com.clintpauldev.daggerhiltinstrumentationtestingexample.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.clintpauldev.daggerhiltinstrumentationtestingexample.data.local.User
import com.clintpauldev.daggerhiltinstrumentationtestingexample.data.local.UserDao
import com.clintpauldev.daggerhiltinstrumentationtestingexample.data.local.UserDatabase
import com.clintpauldev.daggerhiltinstrumentationtestingexample.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@ExperimentalCoroutinesApi
@HiltAndroidTest
@SmallTest
class UserDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: UserDatabase
    private lateinit var userDao: UserDao

    @Before
    fun setup() {
        hiltRule.inject()
        userDao = database.userDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertUser() = runBlockingTest {
        val user = User(
            name = "Clint",
            age = 26,
            id = 1
        )
        userDao.insertUser(user)
        val allUsers = userDao.observeAllUsers().getOrAwaitValue()
        assertThat(allUsers).contains(user)

    }
}