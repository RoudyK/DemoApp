package com.roudykk.data.mapper

import com.roudykk.data.test.MoviesAssertionUtil
import com.roudykk.data.test.MoviesEntityFactory
import org.junit.Test

class ProductionCompanyEntityMapperTest: EntityMapperTest {

    private val productionCompanyEntityMapper = ProductionCompanyEntityMapper()

    @Test
    override fun mapFromEntity() {
        val productionCompanyEntity = MoviesEntityFactory.makeProductionCompanyEntity()
        val productionCompany = this.productionCompanyEntityMapper.mapFromEntity(productionCompanyEntity)

        MoviesAssertionUtil.assertProductionCompanyEquals(productionCompanyEntity, productionCompany)
    }

    @Test
    override fun mapToEntity() {
        val productionCompany = MoviesEntityFactory.makeProductionCompany()
        val productionCompanyEntity = this.productionCompanyEntityMapper.mapToEntity(productionCompany)

        MoviesAssertionUtil.assertProductionCompanyEquals(productionCompanyEntity, productionCompany)
    }
}