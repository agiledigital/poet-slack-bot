
package services.httpclient;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Watches {

    @SerializedName("self")
    @Expose
    private String self;
    @SerializedName("watchCount")
    @Expose
    private Integer watchCount;
    @SerializedName("isWatching")
    @Expose
    private Boolean isWatching;

    /**
     * 
     * @return
     *     The self
     */
    public String getSelf() {
        return self;
    }

    /**
     * 
     * @param self
     *     The self
     */
    public void setSelf(String self) {
        this.self = self;
    }

    /**
     * 
     * @return
     *     The watchCount
     */
    public Integer getWatchCount() {
        return watchCount;
    }

    /**
     * 
     * @param watchCount
     *     The watchCount
     */
    public void setWatchCount(Integer watchCount) {
        this.watchCount = watchCount;
    }

    /**
     * 
     * @return
     *     The isWatching
     */
    public Boolean getIsWatching() {
        return isWatching;
    }

    /**
     * 
     * @param isWatching
     *     The isWatching
     */
    public void setIsWatching(Boolean isWatching) {
        this.isWatching = isWatching;
    }

}
