package be.cegeka.orderit.service.eventstore.api

import org.assertj.core.api.Assertions
import org.junit.Test

class InternalAnalysisAggregateIdTest {

    @Test
    fun assertEquality() {
        val one = InternalAnalysisAggregateId("abc")
        val two = InternalAnalysisAggregateId("abc")
        Assertions.assertThat(one).isEqualTo(one)
        Assertions.assertThat(two).isEqualTo(one)
        Assertions.assertThat(one).isEqualTo(two)
        Assertions.assertThat(two)
                .isEqualTo(two)
                .hasSameHashCodeAs(one)
    }

    @Test
    fun assertInEquality() {
        val one = InternalAnalysisAggregateId("abc")
        val two = InternalAnalysisAggregateId("45367")
        Assertions.assertThat(one).isNotEqualTo(two)
        Assertions.assertThat(two).isNotEqualTo(one)
        Assertions.assertThat(two.hashCode()).isNotEqualTo(one.hashCode())
    }
}