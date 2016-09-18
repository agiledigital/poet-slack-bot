
package services.httpclient;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Fields {

    @SerializedName("issuetype")
    @Expose
    private Issuetype issuetype;
    @SerializedName("customfield_10190")
    @Expose
    private Object customfield10190;
    @SerializedName("customfield_10191")
    @Expose
    private Object customfield10191;
    @SerializedName("timespent")
    @Expose
    private Object timespent;
    @SerializedName("project")
    @Expose
    private Project project;
    @SerializedName("fixVersions")
    @Expose
    private List<FixVersion> fixVersions = new ArrayList<FixVersion>();
    @SerializedName("customfield_10111")
    @Expose
    private String customfield10111;
    @SerializedName("aggregatetimespent")
    @Expose
    private Object aggregatetimespent;
    @SerializedName("resolution")
    @Expose
    private Object resolution;
    @SerializedName("customfield_10510")
    @Expose
    private Object customfield10510;
    @SerializedName("customfield_11513")
    @Expose
    private Object customfield11513;
    @SerializedName("customfield_11512")
    @Expose
    private Object customfield11512;
    @SerializedName("customfield_11515")
    @Expose
    private Object customfield11515;
    @SerializedName("customfield_11514")
    @Expose
    private Object customfield11514;
    @SerializedName("customfield_11517")
    @Expose
    private Object customfield11517;
    @SerializedName("customfield_11516")
    @Expose
    private Object customfield11516;
    @SerializedName("customfield_11519")
    @Expose
    private Object customfield11519;
    @SerializedName("resolutiondate")
    @Expose
    private Object resolutiondate;
    @SerializedName("workratio")
    @Expose
    private Integer workratio;
    @SerializedName("lastViewed")
    @Expose
    private String lastViewed;
    @SerializedName("watches")
    @Expose
    private Watches watches;
    @SerializedName("customfield_10180")
    @Expose
    private Object customfield10180;
    @SerializedName("customfield_10181")
    @Expose
    private Object customfield10181;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("customfield_11110")
    @Expose
    private Object customfield11110;
    @SerializedName("priority")
    @Expose
    private Priority priority;
    @SerializedName("customfield_11310")
    @Expose
    private String customfield11310;
    @SerializedName("customfield_11511")
    @Expose
    private Object customfield11511;
    @SerializedName("labels")
    @Expose
    private List<Object> labels = new ArrayList<Object>();
    @SerializedName("customfield_11510")
    @Expose
    private Object customfield11510;
    @SerializedName("customfield_10612")
    @Expose
    private Object customfield10612;
    @SerializedName("timeestimate")
    @Expose
    private Integer timeestimate;
    @SerializedName("aggregatetimeoriginalestimate")
    @Expose
    private Integer aggregatetimeoriginalestimate;
    @SerializedName("versions")
    @Expose
    private List<Object> versions = new ArrayList<Object>();
    @SerializedName("issuelinks")
    @Expose
    private List<Object> issuelinks = new ArrayList<Object>();
    @SerializedName("assignee")
    @Expose
    private Assignee assignee;
    @SerializedName("updated")
    @Expose
    private String updated;
    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("components")
    @Expose
    private List<Object> components = new ArrayList<Object>();
    @SerializedName("customfield_10170")
    @Expose
    private Object customfield10170;
    @SerializedName("customfield_10171")
    @Expose
    private Object customfield10171;
    @SerializedName("timeoriginalestimate")
    @Expose
    private Integer timeoriginalestimate;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("customfield_10054")
    @Expose
    private Object customfield10054;
    @SerializedName("timetracking")
    @Expose
    private Timetracking timetracking;
    @SerializedName("customfield_11612")
    @Expose
    private Object customfield11612;
    @SerializedName("customfield_11611")
    @Expose
    private Object customfield11611;
    @SerializedName("customfield_11613")
    @Expose
    private Object customfield11613;
    @SerializedName("attachment")
    @Expose
    private List<Attachment> attachment = new ArrayList<Attachment>();
    @SerializedName("aggregatetimeestimate")
    @Expose
    private Integer aggregatetimeestimate;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("creator")
    @Expose
    private Creator creator;
    @SerializedName("subtasks")
    @Expose
    private List<Object> subtasks = new ArrayList<Object>();
    @SerializedName("reporter")
    @Expose
    private Reporter reporter;
    @SerializedName("aggregateprogress")
    @Expose
    private Aggregateprogress aggregateprogress;
    @SerializedName("customfield_11410")
    @Expose
    private Object customfield11410;
    @SerializedName("customfield_11610")
    @Expose
    private Object customfield11610;
    @SerializedName("customfield_11412")
    @Expose
    private Object customfield11412;
    @SerializedName("customfield_11411")
    @Expose
    private Object customfield11411;
    @SerializedName("environment")
    @Expose
    private Object environment;
    @SerializedName("customfield_10911")
    @Expose
    private String customfield10911;
    @SerializedName("duedate")
    @Expose
    private String duedate;
    @SerializedName("progress")
    @Expose
    private Progress progress;
    @SerializedName("comment")
    @Expose
    private Comment comment;
    @SerializedName("worklog")
    @Expose
    private Worklog worklog;

    /**
     * 
     * @return
     *     The issuetype
     */
    public Issuetype getIssuetype() {
        return issuetype;
    }

    /**
     * 
     * @param issuetype
     *     The issuetype
     */
    public void setIssuetype(Issuetype issuetype) {
        this.issuetype = issuetype;
    }

    /**
     * 
     * @return
     *     The customfield10190
     */
    public Object getCustomfield10190() {
        return customfield10190;
    }

    /**
     * 
     * @param customfield10190
     *     The customfield_10190
     */
    public void setCustomfield10190(Object customfield10190) {
        this.customfield10190 = customfield10190;
    }

    /**
     * 
     * @return
     *     The customfield10191
     */
    public Object getCustomfield10191() {
        return customfield10191;
    }

    /**
     * 
     * @param customfield10191
     *     The customfield_10191
     */
    public void setCustomfield10191(Object customfield10191) {
        this.customfield10191 = customfield10191;
    }

    /**
     * 
     * @return
     *     The timespent
     */
    public Object getTimespent() {
        return timespent;
    }

    /**
     * 
     * @param timespent
     *     The timespent
     */
    public void setTimespent(Object timespent) {
        this.timespent = timespent;
    }

    /**
     * 
     * @return
     *     The project
     */
    public Project getProject() {
        return project;
    }

    /**
     * 
     * @param project
     *     The project
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * 
     * @return
     *     The fixVersions
     */
    public List<FixVersion> getFixVersions() {
        return fixVersions;
    }

    /**
     * 
     * @param fixVersions
     *     The fixVersions
     */
    public void setFixVersions(List<FixVersion> fixVersions) {
        this.fixVersions = fixVersions;
    }

    /**
     * 
     * @return
     *     The customfield10111
     */
    public String getCustomfield10111() {
        return customfield10111;
    }

    /**
     * 
     * @param customfield10111
     *     The customfield_10111
     */
    public void setCustomfield10111(String customfield10111) {
        this.customfield10111 = customfield10111;
    }

    /**
     * 
     * @return
     *     The aggregatetimespent
     */
    public Object getAggregatetimespent() {
        return aggregatetimespent;
    }

    /**
     * 
     * @param aggregatetimespent
     *     The aggregatetimespent
     */
    public void setAggregatetimespent(Object aggregatetimespent) {
        this.aggregatetimespent = aggregatetimespent;
    }

    /**
     * 
     * @return
     *     The resolution
     */
    public Object getResolution() {
        return resolution;
    }

    /**
     * 
     * @param resolution
     *     The resolution
     */
    public void setResolution(Object resolution) {
        this.resolution = resolution;
    }

    /**
     * 
     * @return
     *     The customfield10510
     */
    public Object getCustomfield10510() {
        return customfield10510;
    }

    /**
     * 
     * @param customfield10510
     *     The customfield_10510
     */
    public void setCustomfield10510(Object customfield10510) {
        this.customfield10510 = customfield10510;
    }

    /**
     * 
     * @return
     *     The customfield11513
     */
    public Object getCustomfield11513() {
        return customfield11513;
    }

    /**
     * 
     * @param customfield11513
     *     The customfield_11513
     */
    public void setCustomfield11513(Object customfield11513) {
        this.customfield11513 = customfield11513;
    }

    /**
     * 
     * @return
     *     The customfield11512
     */
    public Object getCustomfield11512() {
        return customfield11512;
    }

    /**
     * 
     * @param customfield11512
     *     The customfield_11512
     */
    public void setCustomfield11512(Object customfield11512) {
        this.customfield11512 = customfield11512;
    }

    /**
     * 
     * @return
     *     The customfield11515
     */
    public Object getCustomfield11515() {
        return customfield11515;
    }

    /**
     * 
     * @param customfield11515
     *     The customfield_11515
     */
    public void setCustomfield11515(Object customfield11515) {
        this.customfield11515 = customfield11515;
    }

    /**
     * 
     * @return
     *     The customfield11514
     */
    public Object getCustomfield11514() {
        return customfield11514;
    }

    /**
     * 
     * @param customfield11514
     *     The customfield_11514
     */
    public void setCustomfield11514(Object customfield11514) {
        this.customfield11514 = customfield11514;
    }

    /**
     * 
     * @return
     *     The customfield11517
     */
    public Object getCustomfield11517() {
        return customfield11517;
    }

    /**
     * 
     * @param customfield11517
     *     The customfield_11517
     */
    public void setCustomfield11517(Object customfield11517) {
        this.customfield11517 = customfield11517;
    }

    /**
     * 
     * @return
     *     The customfield11516
     */
    public Object getCustomfield11516() {
        return customfield11516;
    }

    /**
     * 
     * @param customfield11516
     *     The customfield_11516
     */
    public void setCustomfield11516(Object customfield11516) {
        this.customfield11516 = customfield11516;
    }

    /**
     * 
     * @return
     *     The customfield11519
     */
    public Object getCustomfield11519() {
        return customfield11519;
    }

    /**
     * 
     * @param customfield11519
     *     The customfield_11519
     */
    public void setCustomfield11519(Object customfield11519) {
        this.customfield11519 = customfield11519;
    }

    /**
     * 
     * @return
     *     The resolutiondate
     */
    public Object getResolutiondate() {
        return resolutiondate;
    }

    /**
     * 
     * @param resolutiondate
     *     The resolutiondate
     */
    public void setResolutiondate(Object resolutiondate) {
        this.resolutiondate = resolutiondate;
    }

    /**
     * 
     * @return
     *     The workratio
     */
    public Integer getWorkratio() {
        return workratio;
    }

    /**
     * 
     * @param workratio
     *     The workratio
     */
    public void setWorkratio(Integer workratio) {
        this.workratio = workratio;
    }

    /**
     * 
     * @return
     *     The lastViewed
     */
    public String getLastViewed() {
        return lastViewed;
    }

    /**
     * 
     * @param lastViewed
     *     The lastViewed
     */
    public void setLastViewed(String lastViewed) {
        this.lastViewed = lastViewed;
    }

    /**
     * 
     * @return
     *     The watches
     */
    public Watches getWatches() {
        return watches;
    }

    /**
     * 
     * @param watches
     *     The watches
     */
    public void setWatches(Watches watches) {
        this.watches = watches;
    }

    /**
     * 
     * @return
     *     The customfield10180
     */
    public Object getCustomfield10180() {
        return customfield10180;
    }

    /**
     * 
     * @param customfield10180
     *     The customfield_10180
     */
    public void setCustomfield10180(Object customfield10180) {
        this.customfield10180 = customfield10180;
    }

    /**
     * 
     * @return
     *     The customfield10181
     */
    public Object getCustomfield10181() {
        return customfield10181;
    }

    /**
     * 
     * @param customfield10181
     *     The customfield_10181
     */
    public void setCustomfield10181(Object customfield10181) {
        this.customfield10181 = customfield10181;
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
     *     The customfield11110
     */
    public Object getCustomfield11110() {
        return customfield11110;
    }

    /**
     * 
     * @param customfield11110
     *     The customfield_11110
     */
    public void setCustomfield11110(Object customfield11110) {
        this.customfield11110 = customfield11110;
    }

    /**
     * 
     * @return
     *     The priority
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * 
     * @param priority
     *     The priority
     */
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * 
     * @return
     *     The customfield11310
     */
    public String getCustomfield11310() {
        return customfield11310;
    }

    /**
     * 
     * @param customfield11310
     *     The customfield_11310
     */
    public void setCustomfield11310(String customfield11310) {
        this.customfield11310 = customfield11310;
    }

    /**
     * 
     * @return
     *     The customfield11511
     */
    public Object getCustomfield11511() {
        return customfield11511;
    }

    /**
     * 
     * @param customfield11511
     *     The customfield_11511
     */
    public void setCustomfield11511(Object customfield11511) {
        this.customfield11511 = customfield11511;
    }

    /**
     * 
     * @return
     *     The labels
     */
    public List<Object> getLabels() {
        return labels;
    }

    /**
     * 
     * @param labels
     *     The labels
     */
    public void setLabels(List<Object> labels) {
        this.labels = labels;
    }

    /**
     * 
     * @return
     *     The customfield11510
     */
    public Object getCustomfield11510() {
        return customfield11510;
    }

    /**
     * 
     * @param customfield11510
     *     The customfield_11510
     */
    public void setCustomfield11510(Object customfield11510) {
        this.customfield11510 = customfield11510;
    }

    /**
     * 
     * @return
     *     The customfield10612
     */
    public Object getCustomfield10612() {
        return customfield10612;
    }

    /**
     * 
     * @param customfield10612
     *     The customfield_10612
     */
    public void setCustomfield10612(Object customfield10612) {
        this.customfield10612 = customfield10612;
    }

    /**
     * 
     * @return
     *     The timeestimate
     */
    public Integer getTimeestimate() {
        return timeestimate;
    }

    /**
     * 
     * @param timeestimate
     *     The timeestimate
     */
    public void setTimeestimate(Integer timeestimate) {
        this.timeestimate = timeestimate;
    }

    /**
     * 
     * @return
     *     The aggregatetimeoriginalestimate
     */
    public Integer getAggregatetimeoriginalestimate() {
        return aggregatetimeoriginalestimate;
    }

    /**
     * 
     * @param aggregatetimeoriginalestimate
     *     The aggregatetimeoriginalestimate
     */
    public void setAggregatetimeoriginalestimate(Integer aggregatetimeoriginalestimate) {
        this.aggregatetimeoriginalestimate = aggregatetimeoriginalestimate;
    }

    /**
     * 
     * @return
     *     The versions
     */
    public List<Object> getVersions() {
        return versions;
    }

    /**
     * 
     * @param versions
     *     The versions
     */
    public void setVersions(List<Object> versions) {
        this.versions = versions;
    }

    /**
     * 
     * @return
     *     The issuelinks
     */
    public List<Object> getIssuelinks() {
        return issuelinks;
    }

    /**
     * 
     * @param issuelinks
     *     The issuelinks
     */
    public void setIssuelinks(List<Object> issuelinks) {
        this.issuelinks = issuelinks;
    }

    /**
     * 
     * @return
     *     The assignee
     */
    public Assignee getAssignee() {
        return assignee;
    }

    /**
     * 
     * @param assignee
     *     The assignee
     */
    public void setAssignee(Assignee assignee) {
        this.assignee = assignee;
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

    /**
     * 
     * @return
     *     The status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The components
     */
    public List<Object> getComponents() {
        return components;
    }

    /**
     * 
     * @param components
     *     The components
     */
    public void setComponents(List<Object> components) {
        this.components = components;
    }

    /**
     * 
     * @return
     *     The customfield10170
     */
    public Object getCustomfield10170() {
        return customfield10170;
    }

    /**
     * 
     * @param customfield10170
     *     The customfield_10170
     */
    public void setCustomfield10170(Object customfield10170) {
        this.customfield10170 = customfield10170;
    }

    /**
     * 
     * @return
     *     The customfield10171
     */
    public Object getCustomfield10171() {
        return customfield10171;
    }

    /**
     * 
     * @param customfield10171
     *     The customfield_10171
     */
    public void setCustomfield10171(Object customfield10171) {
        this.customfield10171 = customfield10171;
    }

    /**
     * 
     * @return
     *     The timeoriginalestimate
     */
    public Integer getTimeoriginalestimate() {
        return timeoriginalestimate;
    }

    /**
     * 
     * @param timeoriginalestimate
     *     The timeoriginalestimate
     */
    public void setTimeoriginalestimate(Integer timeoriginalestimate) {
        this.timeoriginalestimate = timeoriginalestimate;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The customfield10054
     */
    public Object getCustomfield10054() {
        return customfield10054;
    }

    /**
     * 
     * @param customfield10054
     *     The customfield_10054
     */
    public void setCustomfield10054(Object customfield10054) {
        this.customfield10054 = customfield10054;
    }

    /**
     * 
     * @return
     *     The timetracking
     */
    public Timetracking getTimetracking() {
        return timetracking;
    }

    /**
     * 
     * @param timetracking
     *     The timetracking
     */
    public void setTimetracking(Timetracking timetracking) {
        this.timetracking = timetracking;
    }

    /**
     * 
     * @return
     *     The customfield11612
     */
    public Object getCustomfield11612() {
        return customfield11612;
    }

    /**
     * 
     * @param customfield11612
     *     The customfield_11612
     */
    public void setCustomfield11612(Object customfield11612) {
        this.customfield11612 = customfield11612;
    }

    /**
     * 
     * @return
     *     The customfield11611
     */
    public Object getCustomfield11611() {
        return customfield11611;
    }

    /**
     * 
     * @param customfield11611
     *     The customfield_11611
     */
    public void setCustomfield11611(Object customfield11611) {
        this.customfield11611 = customfield11611;
    }

    /**
     * 
     * @return
     *     The customfield11613
     */
    public Object getCustomfield11613() {
        return customfield11613;
    }

    /**
     * 
     * @param customfield11613
     *     The customfield_11613
     */
    public void setCustomfield11613(Object customfield11613) {
        this.customfield11613 = customfield11613;
    }

    /**
     * 
     * @return
     *     The attachment
     */
    public List<Attachment> getAttachment() {
        return attachment;
    }

    /**
     * 
     * @param attachment
     *     The attachment
     */
    public void setAttachment(List<Attachment> attachment) {
        this.attachment = attachment;
    }

    /**
     * 
     * @return
     *     The aggregatetimeestimate
     */
    public Integer getAggregatetimeestimate() {
        return aggregatetimeestimate;
    }

    /**
     * 
     * @param aggregatetimeestimate
     *     The aggregatetimeestimate
     */
    public void setAggregatetimeestimate(Integer aggregatetimeestimate) {
        this.aggregatetimeestimate = aggregatetimeestimate;
    }

    /**
     * 
     * @return
     *     The summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * 
     * @param summary
     *     The summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * 
     * @return
     *     The creator
     */
    public Creator getCreator() {
        return creator;
    }

    /**
     * 
     * @param creator
     *     The creator
     */
    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    /**
     * 
     * @return
     *     The subtasks
     */
    public List<Object> getSubtasks() {
        return subtasks;
    }

    /**
     * 
     * @param subtasks
     *     The subtasks
     */
    public void setSubtasks(List<Object> subtasks) {
        this.subtasks = subtasks;
    }

    /**
     * 
     * @return
     *     The reporter
     */
    public Reporter getReporter() {
        return reporter;
    }

    /**
     * 
     * @param reporter
     *     The reporter
     */
    public void setReporter(Reporter reporter) {
        this.reporter = reporter;
    }

    /**
     * 
     * @return
     *     The aggregateprogress
     */
    public Aggregateprogress getAggregateprogress() {
        return aggregateprogress;
    }

    /**
     * 
     * @param aggregateprogress
     *     The aggregateprogress
     */
    public void setAggregateprogress(Aggregateprogress aggregateprogress) {
        this.aggregateprogress = aggregateprogress;
    }

    /**
     * 
     * @return
     *     The customfield11410
     */
    public Object getCustomfield11410() {
        return customfield11410;
    }

    /**
     * 
     * @param customfield11410
     *     The customfield_11410
     */
    public void setCustomfield11410(Object customfield11410) {
        this.customfield11410 = customfield11410;
    }

    /**
     * 
     * @return
     *     The customfield11610
     */
    public Object getCustomfield11610() {
        return customfield11610;
    }

    /**
     * 
     * @param customfield11610
     *     The customfield_11610
     */
    public void setCustomfield11610(Object customfield11610) {
        this.customfield11610 = customfield11610;
    }

    /**
     * 
     * @return
     *     The customfield11412
     */
    public Object getCustomfield11412() {
        return customfield11412;
    }

    /**
     * 
     * @param customfield11412
     *     The customfield_11412
     */
    public void setCustomfield11412(Object customfield11412) {
        this.customfield11412 = customfield11412;
    }

    /**
     * 
     * @return
     *     The customfield11411
     */
    public Object getCustomfield11411() {
        return customfield11411;
    }

    /**
     * 
     * @param customfield11411
     *     The customfield_11411
     */
    public void setCustomfield11411(Object customfield11411) {
        this.customfield11411 = customfield11411;
    }

    /**
     * 
     * @return
     *     The environment
     */
    public Object getEnvironment() {
        return environment;
    }

    /**
     * 
     * @param environment
     *     The environment
     */
    public void setEnvironment(Object environment) {
        this.environment = environment;
    }

    /**
     * 
     * @return
     *     The customfield10911
     */
    public String getCustomfield10911() {
        return customfield10911;
    }

    /**
     * 
     * @param customfield10911
     *     The customfield_10911
     */
    public void setCustomfield10911(String customfield10911) {
        this.customfield10911 = customfield10911;
    }

    /**
     * 
     * @return
     *     The duedate
     */
    public String getDuedate() {
        return duedate;
    }

    /**
     * 
     * @param duedate
     *     The duedate
     */
    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    /**
     * 
     * @return
     *     The progress
     */
    public Progress getProgress() {
        return progress;
    }

    /**
     * 
     * @param progress
     *     The progress
     */
    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    /**
     * 
     * @return
     *     The comment
     */
    public Comment getComment() {
        return comment;
    }

    /**
     * 
     * @param comment
     *     The comment
     */
    public void setComment(Comment comment) {
        this.comment = comment;
    }

    /**
     * 
     * @return
     *     The worklog
     */
    public Worklog getWorklog() {
        return worklog;
    }

    /**
     * 
     * @param worklog
     *     The worklog
     */
    public void setWorklog(Worklog worklog) {
        this.worklog = worklog;
    }

}
