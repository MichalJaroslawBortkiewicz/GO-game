package com.mycompany.app.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class GameManagerTest
{
    @Before
	public void cleanUpSingletonReference() {
		GameManager.resetInstance();
	}

	@Test
	public void testSingleton() {
		GameManager manager = GameManager.getInstance();
		assertNotNull(manager);
        GameManager manager2 = GameManager.getInstance();
        assertEquals(manager, manager2);
	}

	@Test
    @SuppressWarnings("PMD.EmptyControlStatement")
	public void testThreadLocalGameManager() throws InterruptedException {

		List<Thread> threads = new ArrayList<Thread>();

		List<GameManager> references = Collections.synchronizedList(new ArrayList<GameManager>());

		for (int i = 0; i < 10; i++) {
			Thread worker = new Thread(new Worker(references));
			threads.add(worker);
			worker.start();
		}

		for (Thread thread : threads) {
			while (thread.isAlive()) {
				// wait until thread is dead
			}
		}

		for (int i = 1; i < references.size(); i++) {
            GameManager manager = references.get(i);
			for (int j = 0; j < i; j++) {
				assertNotEquals(manager, references.get(j));
			}
		}
	}

	class Worker implements Runnable {
		private List<GameManager> myReferences;

		public Worker(List<GameManager> references) {
			this.myReferences = references;
		}

		@Override
		public void run() {
			this.myReferences.add(GameManager.getInstance());
		}
	}
}
