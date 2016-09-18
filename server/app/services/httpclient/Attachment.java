
package services.httpclient;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Attachment {

    @SerializedName("self")
    @Expose
    private String self;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("filename")
    @Expose
    private String filename;
    @SerializedName("author")
    @Expose
    private Author author;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("mimeType")
    @Expose
    private String mimeType;
    @SerializedName("content")
    @Expose
    private String content;

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
     *     The filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * 
     * @param filename
     *     The filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * 
     * @return
     *     The author
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * 
     * @param author
     *     The author
     */
    public void setAuthor(Author author) {
        this.author = author;
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
     *     The size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * 
     * @param size
     *     The size
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * 
     * @return
     *     The mimeType
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * 
     * @param mimeType
     *     The mimeType
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * 
     * @return
     *     The content
     */
    public String getContent() {
        return content;
    }

    /**
     * 
     * @param content
     *     The content
     */
    public void setContent(String content) {
        this.content = content;
    }

}
