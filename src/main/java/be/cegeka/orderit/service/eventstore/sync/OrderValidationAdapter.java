package be.cegeka.orderit.service.eventstore.sync;

import be.cegeka.orderit.service.eventstore.api.AnalysisNumber;

import java.util.Collection;

/**
 * Service adapter for ODRHDR and ODRDTL tables
 */
public interface OrderValidationAdapter {

    /**
     * Add a record to ODRDTL (with flg3='N') for each POCT analyses
     *
     * @param institute
     * @param orderId
     * @param analyses  the list of POCT analyses for which auto validation must be enabled
     */
    void registerPOCTAnalyses(String institute, String orderId, Collection<AnalysisNumber> analyses, boolean poctEnabled);

    /**
     * Set flag3='Y' in ORDHDR and ORDDTL. The POCT analyses have already been stored in ORDDTL prior to this call.
     *
     * @param institute
     * @param orderId
     * @param analyses  the list of POCT analyses for which auto validation must be enabled
     */

    void enableAutoValidationFor(String institute, String orderId, Collection<AnalysisNumber> analyses);

    /**
     * Remove the corresponding ORDDTL record for this POCT analysis.
     *
     * @param institute
     * @param orderId
     * @param analysis
     */
    void unregisterPOCTAnalysis(String institute, String orderId, AnalysisNumber analysis);
}

