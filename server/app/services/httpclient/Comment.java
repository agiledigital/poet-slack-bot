
package services.httpclient;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Comment {

    @SerializedName("startAt")
    @Expose
    private Integer startAt;
    @SerializedName("maxResults")
    @Expose
    private Integer maxResults;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("comments")
    @Expose
    private List<Comment_> comments = new ArrayList<Comment_>();

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
     *     The comments
     */
    public List<Comment_> getComments() {
        return comments;
    }

    /**
     * 
     * @param comments
     *     The comments
     */
    public void setComments(List<Comment_> comments) {
        this.comments = comments;
    }

}
