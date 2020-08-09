package fr.misoda.contact.model.event;

public class EventReceiveDetection {
    private String detectedText;

    public EventReceiveDetection(String detectedText) {
        this.detectedText = detectedText;
    }

    public String getDetectedText() {
        return detectedText;
    }
}
