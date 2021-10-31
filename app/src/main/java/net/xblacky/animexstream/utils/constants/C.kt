package net.xblacky.animexstream.utils.constants

class C {
    companion object{

        const val GIT_DOWNLOAD_URL = "https://github.com/mukul500/AnimeXStream/"

        //Error Codes
        const val RESPONSE_UNKNOWN: Int = 1000
        const val ERROR_CODE_DEFAULT: Int = -1000
        const val NO_INTERNET_CONNECTION = 1001

        //Base URLS
<<<<<<< HEAD
        var BASE_URL = "https://www1.gogoanime.ai"
=======
        var BASE_URL = "https://gogoanime.pe/"
>>>>>>> fb7552126aa9242ce5565fc81890be4fafae82e0
        const val EPISODE_LOAD_URL = "https://ajax.gogocdn.net/ajax/load-list-episode"
        const val SEARCH_URL = "/search.html"

        //Model Type
        const val TYPE_RECENT_SUB = 1
        const val TYPE_POPULAR_ANIME =2
        const val TYPE_RECENT_DUB = 3
        const val TYPE_GENRE = 4
        const val TYPE_MOVIE = 5
        const val TYPE_NEW_SEASON = 6
        const val TYPE_DEFAULT= -1

        // Retrofit Request TYPE

        const val RECENT_SUB = 1
        const val RECENT_DUB = 2

        const val MAX_LIMIT_FOR_SUB_DUB = 10


        const val NEWEST_SEASON_POSITION = 2
        const val RECENT_SUB_POSITION = 0
        const val RECENT_DUB_POSITION = 1
        const val POPULAR_POSITION = 4
        const val MOVIE_POSITION = 3

        //Episode URL Type
        const val TYPE_MEDIA_URL = 100
        const val TYPE_M3U8_URL = 101

        //Anime Info URL Type
        const val TYPE_ANIME_INFO = 1000
        const val TYPE_EPISODE_LIST = 1001
        const val M3U8_REGEX_PATTERN = "(http|https)://([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?"

        //Anime Search Types
        const val TYPE_SEARCH_NEW = 2000
        const val TYPE_SEARCH_UPDATE = 2001

        //Network Requests Header
        const val USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36"
<<<<<<< HEAD
        const val ORIGIN = "https://www1.gogoanime.ai"
        const val  REFERER = "https://www1.gogoanime.ai/"
=======
        const val ORIGIN = "https://goload.one"
        const val  REFERER = "https://goload.one"
>>>>>>> fb7552126aa9242ce5565fc81890be4fafae82e0

        //Realm
        const val MAX_TIME_M3U8_URL = 2 * 60 * 60 *1000
        const val MAX_TIME_FOR_ANIME = 2 * 24 * 60 *60 * 1000
    }
}