package com.yazao.base.net

object HttpCode {
    const val TOKEN_EXPIRED_CODE = 2003 // Token失效状态码

    const val SUCCESS = 200
    const val UNAUTHORIZED = 401
    const val FORBIDDEN = 403
    const val NOT_FOUND = 404
    const val REQUEST_TIMEOUT = 408
    const val INTERNAL_SERVER_ERROR = 500
    const val BAD_GATEWAY = 502
    const val SERVICE_UNAVAILABLE = 503
    const val GATEWAY_TIMEOUT = 504
    const val HTTP_VERSION_NOT_SUPPORTED = 505
    const val UNKNOWN_ERROR = -1
    const val UNKNOWN_HOST = -2
    const val CONNECT_TIMEOUT = -3
}