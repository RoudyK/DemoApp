package com.roudykk.data.mapper

import com.roudykk.data.test.MoviesAssertionUtil
import com.roudykk.data.test.MoviesEntityFactory
import org.junit.Test

class PersonEntityMapperTest : EntityMapperTest {

    private val personEntityMapper = PersonEntityMapper()

    @Test
    override fun mapFromEntity() {
        val personEntity = MoviesEntityFactory.makePersonEntity()
        val person = this.personEntityMapper.mapFromEntity(personEntity)

        MoviesAssertionUtil.assertPersonEquals(personEntity, person)
    }

    @Test
    override fun mapToEntity() {
        val person = MoviesEntityFactory.makePerson()
        val personEntity = this.personEntityMapper.mapToEntity(person)

        MoviesAssertionUtil.assertPersonEquals(personEntity, person)
    }
}