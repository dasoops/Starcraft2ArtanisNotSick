package com.dasoops.common.resources.localization

internal fun String.fill(vararg value: Pair<String, String>): String {
    var result: String = this
    value.forEach {
        result = result.replace("\${${it.first}}", it.second)
    }
    return result
}