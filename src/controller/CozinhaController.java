package controller;

import java.util.concurrent.Semaphore;

public class CozinhaController extends Thread {

	private int countCostela = 0;
	private int countMacarrao = 0;
	private int tempo;
	private int idThread;
	private Semaphore semaforo;

	public CozinhaController(int idThread, Semaphore semaforo) {
		this.idThread = idThread;
		this.semaforo = semaforo;
	}

	@Override
	public void run() {
		Cozinha();
	}

	private void Cozinha() {
		if (idThread % 2 == 0) {
			tempo = (int) ((Math.random() * 300) + 500);
			String prato = "Macarrao Carbonara";
			try {
				int temp = tempo/100;
				System.out.println("Prato: Macarrao Carbonara iniciado pela thread #" + idThread + " e demorar� " + temp + " segundos para ficar pronto.");
				while (countMacarrao < 100) {
					
					sleep(100);
					countMacarrao += tempo / 100;
					System.out.println("O prato da thread " + idThread +  " est�: " + countMacarrao + "% conclu�do");
					if(countMacarrao >= 90) {
					System.out.println("O prato da thread " + idThread +  " est� 100% conclu�do");
					break;
					}
				}
				EntregarPrato(idThread, prato);

			} catch (Exception e) {
				e.getMessage();
			}
		} else {
			tempo = (int) ((Math.random() * 600) + 600);
			String prato = "Costela";
			try {
				int temp = tempo/100;
				System.out.println("Prato: Costela iniciado pela thread #" + idThread + " e demorar� " + temp + " segundos para ficar pronto.");
				while (countCostela < 100) {
					sleep(100);
					countCostela += tempo / 100;
					System.out.println("O prato da thread " +idThread +  " est�: " + countCostela + "% conclu�do");
					if(countCostela >= 90) {
					System.out.println("O prato da thread " +idThread +  " est� 100% conclu�do");
					break;
					}
				}
				EntregarPrato(idThread, prato);
			} catch (Exception e) {
				e.getMessage();
			}
		}
	}

	private void EntregarPrato(int id, String prato) {
		try {
			semaforo.acquire();
			System.out.println("O prato " + prato + " da thread #" + idThread + " foi entregue com sucesso!");
			sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			semaforo.release();
		}

	}
}
