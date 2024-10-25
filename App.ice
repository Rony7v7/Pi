module Demo {


	interface ForClient {
	    void printResult(double result);
	}

	interface ForWorker {
	    int throwPoints(int n);
	}

	interface ForMaster {

		void addWorker(ForWorker* o);
		void addClient(string name,ForClient* o);
		void calculate(string name, int N);


	}


}
