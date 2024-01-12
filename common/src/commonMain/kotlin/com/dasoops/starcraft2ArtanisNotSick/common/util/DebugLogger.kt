package com.dasoops.starcraft2ArtanisNotSick.common.util

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}
fun debug(content: () -> Any?) {
    logger.debug(content)
}