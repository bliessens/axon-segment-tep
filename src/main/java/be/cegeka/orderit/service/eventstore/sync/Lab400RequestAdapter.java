package be.cegeka.orderit.service.eventstore.sync;


import be.cegeka.orderit.service.eventstore.api.AnalysisNumber;

/**
 * Service adapter for ANLMANIP and GLPSTORE2
 */
public interface Lab400RequestAdapter {

    /**
     * call ANLMANIP to store analysis & result value;
     * call GLPSTORE2 to log transmission of result value
     *
     * @param institute the Lab400 institute number
     * @param orderId   the Lab400 RQID
     * @param analysis  the POCT analysis test number
     * @param value     the result of the analysis
     */
    void transmitResultValue(String institute, String orderId, AnalysisNumber analysis, String value, String device, String user) ;

    /**
     * call ANLMANIP to store analysis (without result value)
     *
     * @param institute
     * @param orderId
     * @param analysis
     * @param user
     */
    void transmitAnalysis(String institute, String orderId, AnalysisNumber analysis, String user) ;


    /**
     * call ANLMANIP to remove analysis
     *
     * @param institute
     * @param orderId   the Lab400 RQID
     * @param analysis  the POCT analysis test number
     */
    void removeAnalysis(String institute, String orderId, AnalysisNumber analysis, String user) ;
}
