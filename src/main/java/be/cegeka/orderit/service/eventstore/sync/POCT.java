package be.cegeka.orderit.service.eventstore.sync;

import be.cegeka.orderit.service.eventstore.api.AnalysisNumber;

import java.io.Serializable;

class POCT implements Serializable {

    private final AnalysisNumber analysis;
    private final String value;
    /**
     * has this POCT been recorded in ORDDTL ?
     */
    private boolean availableForValidation = false;
    /**
     * has this POCT been synced with LAB400 request ?
     */
    private boolean addedToRequest = false;
    private String user;
    private String device;

    public POCT(AnalysisNumber analysis) {
        this(analysis, null);
    }

    public POCT(AnalysisNumber analysis, String value) {
        this.analysis = analysis;
        this.value = value;
    }

    public POCT(AnalysisNumber analysis, String value, String user) {
        this.analysis = analysis;
        this.value = value;
        this.user = user;
    }

    public POCT(AnalysisNumber analysis, String value, String user, String device) {
        this.analysis = analysis;
        this.value = value;
        this.user = user;
        this.device = device;
    }

    public AnalysisNumber getAnalysis() {
        return analysis;
    }

    public String getValue() {
        return value;
    }

    public boolean hasResult() {
        return this.value != null;
    }

    public void addedToRequest() {
        this.addedToRequest = true;
    }

    public boolean isAddedToRequest() {
        return addedToRequest;
    }

    public boolean isAvailableForValidation() {
        return availableForValidation;
    }

    public void readyForValidation() {
        this.availableForValidation = true;
    }

    public boolean isReadyForValidation() {
        return this.availableForValidation || this.addedToRequest;
    }

    public String getUser() {
        return user;
    }

    public String getDevice() {
        return device;
    }

    public void setAvailableForValidation(boolean availableForValidation) {
        this.availableForValidation = availableForValidation;
    }

    public void setAddedToRequest(boolean addedToRequest) {
        this.addedToRequest = addedToRequest;
    }
}
