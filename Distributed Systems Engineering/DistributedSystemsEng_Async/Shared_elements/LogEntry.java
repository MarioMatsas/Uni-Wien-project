package Shared_elements;
import java.io.Serializable;

public class LogEntry implements Serializable{
	private final String logEntry;

	@Override
	public String toString() {
		return "logEntry=" + logEntry;
	}

	public LogEntry(String logEntry) {
		super();
		assert (logEntry != null);
		this.logEntry = logEntry;
	}

	public String getLogEntry() {
		return logEntry;
	}

	public boolean matchesSearchTerm(String searchTerm) {
		return logEntry.contains(searchTerm);
	}
}