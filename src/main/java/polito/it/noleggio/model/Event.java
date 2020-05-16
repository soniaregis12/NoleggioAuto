package polito.it.noleggio.model;

import java.time.LocalTime;

public class Event implements Comparable<Event> {
	
	private LocalTime time;
	private EventType type;
	
	// Specifico quali sono i tipi di eventi che possono verificarsi, e sono riassunti nell'attributo type
	public enum EventType{
		NEW_CLIENT, CAR_RETURNED
	}

	public Event(LocalTime time, EventType type) {
		super();
		this.time = time;
		this.type = type;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	@Override
	public int compareTo(Event o) {
		return this.time.compareTo(o.time);
	}

	@Override
	public String toString() {
		return "Event [time=" + time + ", type=" + type + "]";
	}
	
}
