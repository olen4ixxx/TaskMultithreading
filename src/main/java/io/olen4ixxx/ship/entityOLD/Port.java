package io.olen4ixxx.ship.entityOLD;

public class Port {
    private final Pier[] piers;
    private final ContainerPlace[] portStorage;
    private static final int NUMBER_OF_CONTAINERS_START_DEFAULT = 10;
//    private final AtomicReference<Pier>[] piers;
//    private final AtomicReference<ContainerPlace>[] portStorage;

    private static class SingletonHolder {
        public static final Port HOLDER_INSTANCE = new Port(2,20);
    }

    public static Port getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    private Port(int piersNumber, int storageCapacity) { // TODO: 29.11.2021
        piers = new Pier[piersNumber];
        portStorage = new ContainerPlace[storageCapacity];
        for (int i = 0; i < piers.length; i++) {
            piers[i] = new Pier();
        }
        for (int i = 0; i < NUMBER_OF_CONTAINERS_START_DEFAULT; i++) {
            portStorage[i] = new ContainerPlace(false);
        }
        for (int i = NUMBER_OF_CONTAINERS_START_DEFAULT; i < portStorage.length; i++) {
            portStorage[i] = new ContainerPlace(true);
        }
    }

    public Pier[] getPiers() {
        return piers;
    }

    public ContainerPlace[] getPortStorage() {
        return portStorage;
    }

//    private int takePier() throws InterruptedException {
//
//        AtomicInteger pierNumber = new AtomicInteger(-1); // FIXME: 29.11.2021
//        //Ищем свободное место и паркуемся
////        locking.lock();
////        try {
//        synchronized (piers){
//            int i = 0;
//            while (i < piers.length) {
//                if (piers[i].isFree()) {      //Если место свободно
//                    piers[i].setBusy();  //занимаем его
//                    pierNumber.set(i);         //Наличие свободного места гарантирует семафор
//                    logger.info("Ship №{} is at pier №{}.", shipId, piers[i].getPierId()); // TODO: 28.11.2021
//                    break;
//                }
//                i++;
//            }
////        } finally {
////            locking.unlock();
//        }
//        TimeUnit.SECONDS.sleep(1);
//        return pierNumber.intValue();
//    }
//
//    private void releasePier(int pierNumber) {
////        locking.lock();
////        try {
//        synchronized (piers){
//            logger.info("Ship №{} leaves pier №{}.", shipId, piers[pierNumber].getPierId());
//            piers[pierNumber].setFree();//Освобождаем место
////        } finally {
////            locking.unlock();
//        }
//
//    }
}
