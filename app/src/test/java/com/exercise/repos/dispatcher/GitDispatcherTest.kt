package com.exercise.repos.dispatcher

import com.exercise.repos.business.dispatcher.BaseDispatcher
import com.exercise.repos.business.dispatcher.DataSource
import com.exercise.repos.data.models.GitLocalData
import com.exercise.repos.data.models.remote.GitRemoteData
import com.exercise.repos.data.models.remote.GitReposResponse
import com.exercise.repos.data.models.remote.Owner
import com.exercise.repos.data.remote.api.Api
import com.exercise.repos.data.remote.repos.GitRemoteRepo
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

@Suppress("UNCHECKED_CAST")
@ExperimentalCoroutinesApi
class GitDispatcherTest : BaseDispatcher(){

    private lateinit var mockServer: MockWebServer
    private lateinit var remoteRepo: GitRemoteRepo
    private lateinit var api: Api

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()


    @Before
    fun setUp() {
        mockServer = MockWebServer()
        api = Retrofit.Builder().baseUrl(mockServer.url("/")).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
        remoteRepo = GitRemoteRepo(api)
    }


    //happy case
    @Test
    fun testOkayCase() {

        val expectedResponse = MockResponse()
        expectedResponse.setResponseCode(HttpURLConnection.HTTP_OK)
        expectedResponse.setBody(Gson().toJson(response))
        mockServer.enqueue(expectedResponse)
        runTest {
            val response = remoteRepo.getRemoteData().body()
            assertThat(response).isEqualTo(this@GitDispatcherTest.response)
        }

    }

    //server error
    @Test
    fun testServerError() {

        val expectedResponse = MockResponse()
        expectedResponse.setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)

        mockServer.enqueue(expectedResponse)

        runTest {
            val remoteResponse = remoteRepo.getRemoteData()
            assertThat(remoteResponse.code()).isEqualTo(HttpURLConnection.HTTP_INTERNAL_ERROR)
            assertThat(remoteResponse.message()).isEqualTo("Server Error")
//            assertThat(remoteResponse.code()).isEqualTo(response)
//            assertThat(remoteResponse).isEqualTo(this@GitDispatcherTest.response)
        }
    }

    @Test(timeout = 4000)
    fun timeOutError() {

        val expectedResponse = MockResponse()
        expectedResponse.setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
        expectedResponse.setBody(Gson().toJson(null))
        expectedResponse.setBodyDelay(4, TimeUnit.SECONDS)
        mockServer.enqueue(expectedResponse)

        runTest {
            val remoteResponse = remoteRepo.getRemoteData()
            assertThat(remoteResponse.code()).isEqualTo(HttpURLConnection.HTTP_INTERNAL_ERROR)
        }
    }

    @Test
    fun invalidResponseError() {

        val expectedResponse = MockResponse()
        expectedResponse.setResponseCode(HttpURLConnection.HTTP_OK)
        expectedResponse.setBody(Gson().toJson(invalidResponse))
        mockServer.enqueue(expectedResponse)

        runTest {
            val remoteResponse = remoteRepo.getRemoteData()
            assertThat(remoteResponse.code()).isEqualTo(HttpURLConnection.HTTP_OK)
            assertThat(remoteResponse).isNotEqualTo(response)

        }
    }

    @Test
    fun testListeners() {
        runTest {
            getData<List<GitLocalData>>(
                DataSource.REMOTE,
                {
                    assertThat(it).isEqualTo(localResponse)
                },
                {
                    assertThat(it).isEqualTo("invalid-response")
                }
            )
        }
    }


    override suspend fun <T> getData(
        dataSource: DataSource,
        response: (response: T?) -> Unit,
        error: (error: String) -> Unit
    ) {

        val expectedResponse = MockResponse()
        expectedResponse.setResponseCode(HttpURLConnection.HTTP_OK)
        expectedResponse.setBody(Gson().toJson(this.response))
        mockServer.enqueue(expectedResponse)

        val remoteResponse = remoteRepo.getRemoteData().body()
        if (remoteResponse != null && remoteResponse is GitReposResponse){
            response(localResponse as T)
        } else {
            error("invalid-response")
        }

    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }


    private val response = GitReposResponse(
        listOf(
            GitRemoteData(
                "go",
                "golang/go",
                "The Go programming language",
                "Go",
                "1.0",
                Owner("https://avatars.githubusercontent.com/u/4314092?v=4")
            ),
            GitRemoteData(
                "ant-design",
                "ant-design/ant-design",
                "An enterprise-class UI design language and React UI library",
                "TypeScript",
                "1.0",
                Owner("https://avatars.githubusercontent.com/u/12101536?v=4")
            ),
            GitRemoteData(
                "swift",
                "apple/swift",
                "The Swift Programming Language",
                "C++",
                "1.0",
                Owner("https://avatars.githubusercontent.com/u/10639145?v=4")
            )
        )
    )

    private val localResponse = listOf(
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


    private val invalidResponse = Owner("https://avatars.githubusercontent.com/u/10639145?v=4")

}