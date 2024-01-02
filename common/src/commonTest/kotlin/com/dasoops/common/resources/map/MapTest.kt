package com.dasoops.common.resources.map

const val targetDir = "D:/temp/target.json"

class MapTest {
    /*@Test
    fun test() {
        val mapList = R.maps.map { map ->
            val eventList: List<Event> = map.event.map l@{
                val type = when (it) {
                    is AssaultEvent -> "Assault"
                    is AwardEvent -> "Award"
                    is MonopolizeEvent -> "Monopolize"
                }
                val id = if (it.id == "empty") {
                    "${type}$${UUID.randomUUID()}"
                } else {
                    it.id
                }
                return@l when (it) {
                    is AssaultEvent -> AssaultEvent(
                        id = id,
                        time = it.time,
                        show = it.show,
                        index = it.index,
                        target = it.target,
                    )

                    is AwardEvent -> AwardEvent(
                        id = id,
                        time = it.time,
                        show = it.show,
                        index = it.index,
                        target = it.target,
                        description = it.description,
                    )

                    is MonopolizeEvent -> MonopolizeEvent(
                        id = id,
                        time = it.time,
                        show = it.show,
                        index = it.index,
                        description = it.description
                    )
                }
            }
            Map(
                name = map.name,
                image = map.image,
                event = eventList,
            )
        }
        File(targetDir).apply {
            if (!exists()) {
                createNewFile()
            }
            this.writeText(mapList.toJsonString(Json { this.prettyPrint = true }))
        }
    }*/
}