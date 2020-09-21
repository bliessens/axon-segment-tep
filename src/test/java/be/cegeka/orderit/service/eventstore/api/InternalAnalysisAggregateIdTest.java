package be.cegeka.orderit.service.eventstore.api;

import be.cegeka.orderit.service.eventstore.api.InternalAnalysisAggregateId;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InternalAnalysisAggregateIdTest {

    @Test
    public void assertEquality() {

        final InternalAnalysisAggregateId one = new InternalAnalysisAggregateId("abc");
        final InternalAnalysisAggregateId two = new InternalAnalysisAggregateId("abc");

        assertThat(one).isEqualTo(one);
        assertThat(two).isEqualTo(one);
        assertThat(one).isEqualTo(two);
        assertThat(two)
                .isEqualTo(two)
                .hasSameHashCodeAs(one);
    }

    @Test
    public void assertInEquality() {

        final InternalAnalysisAggregateId one = new InternalAnalysisAggregateId("abc");
        final InternalAnalysisAggregateId two = new InternalAnalysisAggregateId("45367");

        assertThat(one).isNotEqualTo(two);
        assertThat(two).isNotEqualTo(one);

        assertThat(two.hashCode()).isNotEqualTo(one.hashCode());
    }
}