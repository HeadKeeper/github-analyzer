package by.bsuir.headkeepers.githubanalyzer.api

import ExploreRepositoriesQuery

import by.bsuir.headkeepers.githubanalyzer.support.Settings

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.response.CustomTypeAdapter
import com.apollographql.apollo.response.CustomTypeValue

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.joda.time.DateTime
import type.CustomType

import type.SearchType

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.concurrent.TimeUnit

class DataFetcher {

    fun exploreRepositories(completion: (result: Pair<List<ExploreRepositoriesQuery.Edge>?, Error?>) -> Unit) {
        val lastWeekDate = LocalDate.now().minusDays(7)
        val formattedDateText = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH).format(lastWeekDate)
        val queryCall = ExploreRepositoriesQuery
            .builder()
            .query("created:>$formattedDateText sort:stars-desc")
            .first(25)
            .type(SearchType.REPOSITORY)
            .build()

        apolloClient.query(queryCall)
            .enqueue(object : ApolloCall.Callback<ExploreRepositoriesQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    completion(Pair(null, Error(e.message)))
                }

                override fun onResponse(response: com.apollographql.apollo.api.Response<ExploreRepositoriesQuery.Data>) {
                    val errors = response.errors()
                    if (!errors.isEmpty()) {
                        val message = errors[0]?.message() ?: ""
                        completion(Pair(null, Error(message)))
                    } else {
                        completion(Pair(response.data()?.search()?.edges() ?: listOf(), null))
                    }
                }
            })
    }

    companion object {

        private val httpClient: OkHttpClient by lazy {
            OkHttpClient.Builder()
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .addNetworkInterceptor(NetworkInterceptor())
                .build()
        }


        private val apolloClient: ApolloClient by lazy {
            ApolloClient.builder()
                .serverUrl(Settings.get("github.api"))
                .okHttpClient(httpClient)
                .addCustomTypeAdapter(CustomType.URI, URIConverter())
                .addCustomTypeAdapter(CustomType.DATETIME, DateTimeConverter())
                .build()
        }

        private class NetworkInterceptor : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                return chain.proceed(
                    chain.request().newBuilder()
                        .header("Authorization", "Bearer ${Settings.get("github.token")}")
                        .build()
                )
            }
        }

        private class URIConverter : CustomTypeAdapter<String> {
            override fun encode(value: String): CustomTypeValue<*> {
                return CustomTypeValue.fromRawValue(value)
            }

            override fun decode(value: CustomTypeValue<*>): String {
                return value.value.toString()
            }
        }

        private class DateTimeConverter : CustomTypeAdapter<DateTime> {
            override fun encode(value: DateTime): CustomTypeValue<*> {
                return CustomTypeValue.fromRawValue(value)
            }

            override fun decode(value: CustomTypeValue<*>): DateTime {
                return DateTime(value.value.toString())
            }
        }

    }

}