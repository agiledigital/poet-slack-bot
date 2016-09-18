
package services.httpclient;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Comment_ {

    @SerializedName("self")
    @Expose
    private String self;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("author")
    @Expose
    private Author_ author;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("updateAuthor")
    @Expose
    private UpdateAuthor updateAuthor;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("updated")
    @Expose
    private String updated;

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
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The author
     */
    public Author_ getAuthor() {
        return author;
    }

    /**
     * 
     * @param author
     *     The author
     */
    public void setAuthor(Author_ author) {
        this.author = author;
    }

    /**
     * 
     * @return
     *     The body
     */
    public String getBody() {
        return body;
    }

    /**
     * 
     * @param body
     *     The body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * 
     * @return
     *     The updateAuthor
     */
    public UpdateAuthor getUpdateAuthor() {
        return updateAuthor;
    }

    /**
     * 
     * @param updateAuthor
     *     The updateAuthor
     */
    public void setUpdateAuthor(UpdateAuthor updateAuthor) {
        this.updateAuthor = updateAuthor;
    }

    /**
     * 
     * @return
     *     The created
     */
    public String getCreated() {
        return created;
    }

    /**
     * 
     * @param created
     *     The created
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * 
     * @return
     *     The updated
     */
    public String getUpdated() {
        return updated;
    }

    /**
     * 
     * @param updated
     *     The updated
     */
    public void setUpdated(String updated) {
        this.updated = updated;
    }

}
