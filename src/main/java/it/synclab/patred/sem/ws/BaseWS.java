package it.synclab.patred.sem.ws;

import it.synclab.patred.sem.annotations.NoTransactional;

public class BaseWS {

	@Override
	@NoTransactional
	protected void finalize() throws Throwable {
		super.finalize();
	}

}
