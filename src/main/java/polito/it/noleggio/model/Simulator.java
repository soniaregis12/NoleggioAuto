package polito.it.noleggio.model;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.PriorityQueue;

import polito.it.noleggio.model.Event.EventType;

public class Simulator {
	
	// CODA DEGLI EVENTI
	private PriorityQueue<Event> queue = new PriorityQueue<Event>();

	// PARAMENTRI DI SIMULAZIONE
	private int NC;	// numero di macchine disponibili
	private Duration T_IN = Duration.of(10, ChronoUnit.MINUTES);
	private final LocalTime oraApertura = LocalTime.of(8, 00);
	private final LocalTime oraChiusura = LocalTime.of(17, 00);
	
	// ...E RELATIVI METODI PER IMPOSTARE I PARAMETRI
	public void setNumCars(int nC) {
		NC = nC;
	}
	public void setClientFrequency(Duration t_IN) {
		T_IN = t_IN;
	}
	
	// MODELLO DEL MONDO
	private int nAuto; // auto disponibili nel deposito
	
	// VALORI DA CALCOLARE
	private int clienti;
	private int insoddisfatti;
	
	//...E RELATIVI GETTER
	
	public int getClienti() {
		return clienti;
	}
	public int getInsoddisfatti() {
		return insoddisfatti;
	}
	
	// SIMULAZIONE VERA E PROPRIA
	
	public void run() {
		
		// Preparazione iniziale (mondo + coda eventi)
		this.nAuto = this.NC;
		this.clienti = 0;
		this.insoddisfatti = 0;
		this.queue.clear();
		
		LocalTime oraArrivoCliente = this.oraApertura;
		
		do {
			Event e = new Event(oraArrivoCliente, EventType.NEW_CLIENT);
			queue.add(e);
			oraArrivoCliente = oraArrivoCliente.plus(this.T_IN);
		}while(oraArrivoCliente.isBefore(this.oraChiusura));
		
		// Esecuzione del ciclo di simulazione
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll(); // Estraggo eventi coda prioritaria in ordine di tempo
			System.out.println(e.toString());
			processEvent(e);
			
		}
	}
	
	private void processEvent(Event e) {
		
		switch(e.getType()) {
		
		case NEW_CLIENT:
			
			if(this.nAuto > 0) {
				// Aggiorna modello del mondo
				this.nAuto--;
				//Aggiorna risultati
				this.clienti++;
				//Genera nuovi eventi
				double num = Math.random(); // [0,1)
				Duration travel ;
				if(num < 1.0/3.0) {
					travel = Duration.of(1, ChronoUnit.HOURS);
				}else if(num <2.0/3.0){
					travel = Duration.of(2, ChronoUnit.HOURS);
				}else {
					travel = Duration.of(3, ChronoUnit.HOURS);
				}
				Event nuovo = new Event(e.getTime().plus(travel), EventType.CAR_RETURNED);
				this.queue.add(nuovo);
			}else {
				this.clienti++;
				this.insoddisfatti++;
			}
			break;
			
		case CAR_RETURNED:
			this.nAuto++;
			
			break;
		}
	}
}	
