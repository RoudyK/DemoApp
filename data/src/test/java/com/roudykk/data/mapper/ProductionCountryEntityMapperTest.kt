package com.roudykk.data.mapper

import com.roudykk.data.test.MoviesAssertionUtil
import com.roudykk.data.test.MoviesEntityFactory
import org.junit.Test

class ProductionCountryEntityMapperTest: EntityMapperTest {

    private val productionCountryEntityMapper = ProductionCountryEntityMapper()

    @Test
    override fun mapFromEntity() {
        val productionCountryEntity = MoviesEntityFactory.makeProductionCountryEntity()
        val productionCountry = this.productionCountryEntityMapper.mapFromEntity(productionCountryEntity)

        MoviesAssertionUtil.assertProductionCountryEquals(productionCountryEntity, productionCountry)
    }

    @Test
    override fun mapToEntity() {
        val productionCountry = MoviesEntityFactory.makeProductionCountry()
        val productionCountryEntity =this.productionCountryEntityMapper.mapToEntity(productionCountry)

        MoviesAssertionUtil.assertProductionCountryEquals(productionCountryEntity, productionCountry)
    }
}