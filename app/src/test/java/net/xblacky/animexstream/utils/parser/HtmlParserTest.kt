package net.xblacky.animexstream.utils.parser

import com.google.common.truth.Truth.assertThat
import net.xblacky.animexstream.utils.constants.C
import org.junit.Test

//TODO Handle Static , Wrong, Empty, Null Response


class HtmlParserTest {

    //Check Parsing of HTML Contents
    @Test
    //Give a static response from network & Checks Parser parses data normally
    fun parseRecentSub_StaticResponse_ListOfAnimeMetaModel() {

        //Given Data
        val networkResponse = NetworkResponse.recentSubResponse

        //Processing Data
        val result = HtmlParser.parseRecentSubOrDub(networkResponse, C.TYPE_RECENT_SUB)

        //Check Response Type & Size of Parsed Data
        assertThat(result.size).isEqualTo(20)

    }

    @Test(expected = HtmlParser.ParserEmptyDataException::class)
    //Give a Empty Response & Checks does throw ParserEmptyResponseException
    fun parseRecentSub_emptyResponse_ParserEmptyResponseException() {

        //Given Data
        val networkResponse = ""

        //Processing Data
        HtmlParser.parseRecentSubOrDub(networkResponse, C.TYPE_RECENT_SUB)

    }

    @Test(expected = HtmlParser.ParserListFetchException::class)
    //Give a wrong response & Checks Parser throw FetchException
    fun parseRecentSub_wrongResponse_ParserFetchListException() {

        //Given Data
        val networkResponse = NetworkResponse.popularResponse

        //Processing Data
        HtmlParser.parseRecentSubOrDub(networkResponse, C.TYPE_RECENT_SUB)

    }


    @Test
    //Give a static response from network & Checks Parser parses data normally
    fun parsePopular_StaticResponse_ListOfAnimeMetaModel() {

        //Given Data
        val networkResponse = NetworkResponse.popularResponse

        //Processing Data
        val result = HtmlParser.parsePopular(networkResponse, C.TYPE_POPULAR_ANIME)

        assertThat(result.size).isEqualTo(10)

    }

    @Test(expected = HtmlParser.ParserEmptyDataException::class)
    //Give a Empty Response & Checks does throw ParserEmptyResponseException
    fun parsePopular_emptyResponse_ParserEmptyResponseException() {

        //Given Data
        val networkResponse = ""

        //Processing Data
        HtmlParser.parsePopular(networkResponse, C.TYPE_POPULAR_ANIME)

    }

    @Test(expected = HtmlParser.ParserListFetchException::class)
    //Give a wrong response & Checks Parser throw FetchException
    fun parsePopular_wrongResponse_ParserFetchListException() {

        //Given Data
        val networkResponse = NetworkResponse.recentSubResponse

        //Processing Data
        HtmlParser.parsePopular(networkResponse, C.TYPE_POPULAR_ANIME)

    }


    @Test
    //Give a static response from network & Checks Parser parses data normally
    fun parseMovie_StaticResponse_ListOfAnimeMetaModel() {

        //Given Data
        val networkResponse = NetworkResponse.movieResponse

        //Processing Data
        val result = HtmlParser.parseMovie(networkResponse, C.TYPE_MOVIE)

        //Check Response Type & Size of Parsed Data
        assertThat(result.size).isEqualTo(20)

    }

    //Give a Empty Response & Checks does throw ParserEmptyResponseException
    @Test(expected = HtmlParser.ParserEmptyDataException::class)
    fun parseMovie_emptyResponse_ParserEmptyResponseException() {

        //Given Data
        val networkResponse = ""

        //Processing Data
        HtmlParser.parseMovie(networkResponse, C.TYPE_MOVIE)

    }

    @Test(expected = HtmlParser.ParserListFetchException::class)
    //Give a wrong response & Checks Parser throw FetchException
    fun parseMovie_wrongResponse_ParserFetchListException() {

        //Given Data
        val networkResponse = NetworkResponse.popularResponse

        //Processing Data
        HtmlParser.parseMovie(networkResponse, C.TYPE_MOVIE)

        //Check Response
    }


}