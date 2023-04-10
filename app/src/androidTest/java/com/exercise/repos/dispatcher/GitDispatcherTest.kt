package com.exercise.repos.dispatcher

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.exercise.repos.data.local.AppDataBase
import com.exercise.repos.data.local.dao.GitDao
import com.exercise.repos.data.local.repos.GitLocalRepo
import com.exercise.repos.data.models.GitData
import com.exercise.repos.data.models.Response
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GitDispatcherTest {

    private lateinit var localRepo: GitLocalRepo
    private lateinit var dao: GitDao
    private lateinit var dataBase : AppDataBase



    @Before
    fun setUp() {
        dataBase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDataBase::class.java
        ).allowMainThreadQueries().build()

        dao = dataBase.getDao()
        localRepo = GitLocalRepo(dao)
    }

    @Test
    fun testLocalData(){
        runTest {
            localRepo.insertData(response.list)
            val localResponse = localRepo.getLocalData()
            assertThat(localResponse).isEqualTo(this@GitDispatcherTest.response.list)

        }
    }

    @Test
    fun testDeletion(){
        runTest {
            localRepo.insertData(response.list)


            localRepo.deletePreviousData()
            val data = localRepo.getLocalData()
            assertThat(data.isNullOrEmpty()).isTrue()
        }
    }

    @After
    fun tearDown(){
        dataBase.close()
    }


    private val response = Response(listOf(
        GitData(1, "go", "golang/go", "The Go programming language", "Go", "1.0", null),
                GitData(2,"ant-design", "ant-design/ant-design", "An enterprise-class UI design language and React UI library", "TypeScript", "1.0", null),
            GitData( 3,"swift", "apple/swift", "The Swift Programming Language", "C++", "1.0", null)
    ))

}