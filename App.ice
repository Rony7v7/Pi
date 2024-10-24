module Demo{

    interface Master{
        void addWorker(Worker* w);
        void addClient(Client* c);
        void calculatePi(int n);
        void requestPoints(int n);
    }
    
    interface Client{
        void requestPi(int n);
        void onUpdate(double pi);
    }


    interface Worker{
        void throwPoints(int n);
    }
}