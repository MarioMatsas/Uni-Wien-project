package Server_side;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import Shared_elements.LogEntry;

public class RemoteObject {
	private static LogEntry[] storage = new LogEntry[0];
	private static int nextEntryPointer = 0;

	public boolean logSingleEntry(LogEntry entry) throws InterruptedException {
		assert (!Objects.isNull(entry));
		increaseLogStorage(1);
		storage[nextEntryPointer++] = entry;
		// Print storage after the addition
		// Simulate some delay
        Thread.sleep(500);
		System.out.println(this.toString());
		System.out.println(storage);
		return false;
	}

	public boolean removeOldLogs(int amountToRemove) throws InterruptedException {

		assert (amountToRemove > 0);

		amountToRemove = Math.min(amountToRemove, nextEntryPointer);

		int preserveAtIndex = 0;
		for (int i = amountToRemove; i < nextEntryPointer; i++) {
			storage[preserveAtIndex++] = storage[i];
		}

		nextEntryPointer -= amountToRemove;
		for (int i = nextEntryPointer; i < storage.length; i++) {
			storage[i] = null;
		}
		// Simulate some delay
        Thread.sleep(500);
		System.out.println(storage);
		// Print storage after the removal
		System.out.println(this.toString());
		return false;
	}

	public boolean addLogsInBulk(LogEntry[] logBulk) throws InterruptedException {
		assert (!Objects.isNull(logBulk));

		increaseLogStorage(logBulk.length);
		for (int i = 0; i < logBulk.length; i++) {
			logSingleEntry(logBulk[i]);
		}
		return true;
		//return "Log bulk of size " + logBulk.length + " has been handled!";
	}

	public LogEntry[] search(String searchTerm) throws InterruptedException {
		var matchingEntries = new ArrayList<>();

		for (int i = 0; i < nextEntryPointer; i++) {
			var logEntry = storage[i];
			if (logEntry.matchesSearchTerm(searchTerm)) {
				matchingEntries.add(logEntry);
			}
		}
		Thread.sleep(500);
		return matchingEntries.toArray(new LogEntry[matchingEntries.size()]);
	}

	private int increaseLogStorage(int increaseBy) {
		assert (increaseBy > 0);

		int oldStorageSpace = storage.length;
		int newStorageSpace = nextEntryPointer + increaseBy;

		if (oldStorageSpace >= newStorageSpace) {
			return 0;
		}

		var newStorage = new LogEntry[newStorageSpace];

		for (int i = 0; i < storage.length; i++) {
			newStorage[i] = storage[i];
		}

		this.storage = newStorage;
		return newStorageSpace - oldStorageSpace;
	}

	@Override
	public String toString() {
		return "Stored=" + Arrays.toString(storage) + ", nextEntryPointer=" + nextEntryPointer;
	}
}