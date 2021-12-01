package io.olen4ixxx.ship.entityOLD;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Semaphore;

public class Ship1 extends Thread {
    private static final Logger logger = LogManager.getLogger();
    private int carNumber;
    //Парковочное место занято - true, свободно - false
    private static final Pier[] PARKING_PLACES = Port.getInstance().getPiers();
    //Устанавливаем флаг "справедливый", в таком случае метод
    //aсquire() будет раздавать разрешения в порядке очереди
    private static final Semaphore SEMAPHORE = new Semaphore(PARKING_PLACES.length, true);

    public Ship1(int carNumber) {
        this.carNumber = carNumber;
    }

    @Override
    public void run() {
        logger.info("Корабль №{} подъехал к парковке.", carNumber);
        try {
            //acquire() запрашивает доступ к следующему за вызовом этого метода блоку кода,
            //если доступ не разрешен, поток вызвавший этот метод блокируется до тех пор,
            //пока семафор не разрешит доступ
            SEMAPHORE.acquire();

            int parkingNumber = -1;

            //Ищем свободное место и паркуемся
            synchronized (PARKING_PLACES){
                for (int i = 0; i < 5; i++)
                    if (PARKING_PLACES[i].isFree()) {      //Если место свободно
                        PARKING_PLACES[i].setBusy();  //занимаем его
                        parkingNumber = i;         //Наличие свободного места, гарантирует семафор
                        logger.info("Корабль №{} припарковался на месте {}.", carNumber, i);
                        break;
                    }
            }

            Thread.sleep((int)(2000 * (1 + 2*Math.random())));       //Уходим за покупками, к примеру

            synchronized (PARKING_PLACES) {
                logger.info("Корабль №{} покинул парковку.", carNumber);
                PARKING_PLACES[parkingNumber].setFree();//Освобождаем место
            }

            //release(), напротив, освобождает ресурс
            SEMAPHORE.release();
        } catch (InterruptedException e) {
        }
    }
}
