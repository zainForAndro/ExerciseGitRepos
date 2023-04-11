package com.exercise.repos.dispatcher

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.exercise.repos.data.local.AppDataBase
import com.exercise.repos.data.local.dao.GitDao
import com.exercise.repos.data.local.repos.GitLocalRepo
import com.exercise.repos.data.models.GitLocalData
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@Suppress("UNCHECKED_CAST")
@ExperimentalCoroutinesApi
class GitDispatcherTest : BaseDispatcher(){

    private lateinit var localRepo: GitLocalRepo
    private lateinit var dao: GitDao
    private lateinit var dataBase: AppDataBase


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
    fun testLocalData() {
        runTest {
            localRepo.insertData(response)
            val localResponse = localRepo.getLocalData()
            assertThat(localResponse).isEqualTo(this@GitDispatcherTest.response)

        }
    }

    @Test
    fun testDeletion() {
        runTest {
            localRepo.insertData(response)

            localRepo.deletePreviousData()
            val data = localRepo.getLocalData()
            assertThat(data.isNullOrEmpty()).isTrue()
        }
    }

    @Test
    fun testListeners(){
        runTest {
            getData<List<GitLocalData>>(DataSource.LOCAL, {
                assertThat(it).isEqualTo(response)
            }, {
                assertThat(it).isEqualTo("error")
            })
        }

    }

    override suspend fun <T> getData(
        dataSource: DataSource,
        response: (response: T?) -> Unit,
        error: (error: String) -> Unit
    ) {
        val data = localRepo.getLocalData()
        if (data.isNullOrEmpty()){
            error("error")
        } else
            response(data as T)
    }

    @After
    fun tearDown() {
        dataBase.close()
    }


    private val response = listOf(
        GitLocalData(
            1,
            "go",
            "golang/go",
            "The Go programming language",
            "Go",
            "1.0",
            "https://avatars.githubusercontent.com/u/4314092?v=4"
        ),
        GitLocalData(
            2,
            "ant-design",
            "ant-design/ant-design",
            "An enterprise-class UI design language and React UI library",
            "TypeScript",
            "1.0",
            "https://avatars.githubusercontent.com/u/12101536?v=4"
        ),
        GitLocalData(
            3,
            "swift",
            "apple/swift",
            "The Swift Programming Language",
            "C++",
            "1.0",
            "https://avatars.githubusercontent.com/u/10639145?v=4"
        )
    )

}