package uk.co.ribot.androidboilerplate.test.common

import java.util.ArrayList
import java.util.UUID

import uk.co.ribot.androidboilerplate.data.model.bean.Subject
import uk.co.ribot.androidboilerplate.data.model.entity.InTheatersEntity
import uk.co.ribot.androidboilerplate.data.model.pojo.Image
import uk.co.ribot.androidboilerplate.data.model.pojo.Person
import uk.co.ribot.androidboilerplate.data.model.pojo.Rating

/**
 * Factory class that makes instances of data models with random field values.
 * The aim of this class is to help setting up test fixtures.
 */
object TestDataFactory {

    fun randomUuid(): String {
        return UUID.randomUUID().toString()
    }

    fun makeInTheatersEntity(count: Int): InTheatersEntity {
        return InTheatersEntity.create(count, 0, 32, "正在上映的电影-北京", makeListSubject(count))
    }

    fun makeListSubject(number: Int): List<Subject> {
        val subjects = ArrayList<Subject>()
        for (i in 0 until number) {
            subjects.add(makeSubject(randomUuid(), i))
        }
        return subjects
    }

    fun makeSubject(uniqueSuffix: String, titleId: Int): Subject {
        return Subject.builder()
                .id(uniqueSuffix)
                .rating(makeRating())
                .genres(makeGenres())
                .title(titleId.toString() + "大话西游3")
                .casts(makeListPerson(3))
                .collect_count(17903)
                .original_title("大话西游3")
                .subtype("movie")
                .directors(makeListPerson(1))
                .year("2016")
                .images(makeImage())
                .alt("https://movie.douban.com/subject/26284595/")
                .build()
    }

    fun makeRating(): Rating {
        return Rating.create(10, 4.1, "20", 0)
    }

    fun makeGenres(): List<String> {
        val genres = ArrayList<String>()
        genres.add("喜剧")
        genres.add("爱情")
        genres.add("奇幻")
        return genres
    }

    fun makeImage(): Image {
        return Image.create("https://img3.doubanio.com/img/celebrity/small/14025.jpg",
                "https://img3.doubanio.com/img/celebrity/large/14025.jpg",
                "https://img3.doubanio.com/img/celebrity/medium/14025.jpg")
    }

    fun makePerson(): Person {
        return Person.create("https://movie.douban.com/celebrity/1275667/",
                makeImage(), "韩庚", "1275667")
    }

    fun makeListPerson(number: Int): MutableList<Person> {
        val persons = ArrayList<Person>()
        for (i in 0 until number) {
            persons.add(makePerson())
        }
        return persons
    }

}
