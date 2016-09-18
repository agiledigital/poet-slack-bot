
package services.httpclient;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Timetracking {

    @SerializedName("originalEstimate")
    @Expose
    private String originalEstimate;
    @SerializedName("remainingEstimate")
    @Expose
    private String remainingEstimate;
    @SerializedName("originalEstimateSeconds")
    @Expose
    private Integer originalEstimateSeconds;
    @SerializedName("remainingEstimateSeconds")
    @Expose
    private Integer remainingEstimateSeconds;

    /**
     * 
     * @return
     *     The originalEstimate
     */
    public String getOriginalEstimate() {
        return originalEstimate;
    }

    /**
     * 
     * @param originalEstimate
     *     The originalEstimate
     */
    public void setOriginalEstimate(String originalEstimate) {
        this.originalEstimate = originalEstimate;
    }

    /**
     * 
     * @return
     *     The remainingEstimate
     */
    public String getRemainingEstimate() {
        return remainingEstimate;
    }

    /**
     * 
     * @param remainingEstimate
     *     The remainingEstimate
     */
    public void setRemainingEstimate(String remainingEstimate) {
        this.remainingEstimate = remainingEstimate;
    }

    /**
     * 
     * @return
     *     The originalEstimateSeconds
     */
    public Integer getOriginalEstimateSeconds() {
        return originalEstimateSeconds;
    }

    /**
     * 
     * @param originalEstimateSeconds
     *     The originalEstimateSeconds
     */
    public void setOriginalEstimateSeconds(Integer originalEstimateSeconds) {
        this.originalEstimateSeconds = originalEstimateSeconds;
    }

    /**
     * 
     * @return
     *     The remainingEstimateSeconds
     */
    public Integer getRemainingEstimateSeconds() {
        return remainingEstimateSeconds;
    }

    /**
     * 
     * @param remainingEstimateSeconds
     *     The remainingEstimateSeconds
     */
    public void setRemainingEstimateSeconds(Integer remainingEstimateSeconds) {
        this.remainingEstimateSeconds = remainingEstimateSeconds;
    }

}
