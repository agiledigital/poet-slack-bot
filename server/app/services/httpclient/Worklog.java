
package services.httpclient;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Worklog {

    @SerializedName("startAt")
    @Expose
    private Integer startAt;
    @SerializedName("maxResults")
    @Expose
    private Integer maxResults;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("worklogs")
    @Expose
    private List<Object> worklogs = new ArrayList<Object>();

    /**
     * 
     * @return
     *     The startAt
     */
    public Integer getStartAt() {
        return startAt;
    }

    /**
     * 
     * @param startAt
     *     The startAt
     */
    public void setStartAt(Integer startAt) {
        this.startAt = startAt;
    }

    /**
     * 
     * @return
     *     The maxResults
     */
    public Integer getMaxResults() {
        return maxResults;
    }

    /**
     * 
     * @param maxResults
     *     The maxResults
     */
    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
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
     *     The worklogs
     */
    public List<Object> getWorklogs() {
        return worklogs;
    }

    /**
     * 
     * @param worklogs
     *     The worklogs
     */
    public void setWorklogs(List<Object> worklogs) {
        this.worklogs = worklogs;
    }

}
