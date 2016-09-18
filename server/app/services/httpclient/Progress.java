
package services.httpclient;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Progress {

    @SerializedName("progress")
    @Expose
    private Integer progress;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("percent")
    @Expose
    private Integer percent;

    /**
     * 
     * @return
     *     The progress
     */
    public Integer getProgress() {
        return progress;
    }

    /**
     * 
     * @param progress
     *     The progress
     */
    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    /**
     * 
     * @return
     *     The total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * 
     * @param total
     *     The total
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * 
     * @return
     *     The percent
     */
    public Integer getPercent() {
        return percent;
    }

    /**
     * 
     * @param percent
     *     The percent
     */
    public void setPercent(Integer percent) {
        this.percent = percent;
    }

}
