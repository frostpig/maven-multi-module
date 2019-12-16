import java.io.Serializable;

public class KafkaMessage implements Serializable {

	private static final long serialVersionUID = 4323253939443621818L;
	
    private String host;

	private String project;

	private String message;

    private String logmark;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getLogmark() {
        return logmark;
    }

    public void setLogmark(String logmark) {
        this.logmark = logmark;
    }

    public KafkaMessage() {
	}
	
    public String getProject() {
        return project;
    }

    
    public void setProject(String projectShorName) {
        this.project = projectShorName;
    }

    public String getHost() {
        return host;
    }

    
    public void setHost(String host) {
        this.host = host;
    }
    public String getMessage() {
        return message;
    }

    
    public void setMessage(String data) {
		this.message = data;
	}

	@Override
	public String toString() {
		StringBuilder temp = new StringBuilder();
		temp.append("{project:").append(project).append(",message:")
				.append(message).append(",host:")
                .append(host).append(",logmark:").append(logmark).append("}");
		return temp.toString();
	}
}
