module Demo{

    interface Client{
        void requestPi(int n);
        void onUpdate(double pi);
    }

    interface Master{
        void addWorker(Worker* w);
        void addClient(Client* c);
        void calculatePi();
        void requestPoints(int n);
    }

    interface Worker{
        void throwPoints(int n);
    }
}