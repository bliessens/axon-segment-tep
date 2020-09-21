package be.cegeka.orderit.service.eventstore.sync;

import java.io.Serializable;

public class ResultValue implements Serializable {
    //The distinction is made because we can add information to the GLP log for an untouched result,
    //  but we need to add the analysis to the request with ANLSTORE
    public static final String UNTOUCHED = null;
    public static final String TOUCHED_BUT_NO_RESULT = "";

    private static final long serialVersionUID = -3927914908171761037L;


}