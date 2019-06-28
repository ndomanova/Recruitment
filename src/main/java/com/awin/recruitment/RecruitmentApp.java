package com.awin.recruitment;

import com.awin.recruitment.entities.EnrichedTransaction;
import com.awin.recruitment.entities.Product;
import com.awin.recruitment.entities.RawTransaction;
import com.awin.recruitment.infrastructure.spring.ClassPathXmlApplicationContextFactory;
import com.awin.recruitment.library.Producer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class RecruitmentApp {

    private static Random random = new Random(0);
    private static int transactionCounter = 0;

    private  RecruitmentApp() { }

    public static void main(
        String[] args
    ) throws InterruptedException {

        ClassPathXmlApplicationContext applicationContext = ClassPathXmlApplicationContextFactory.create();

        System.out.println("Recruitment app is running");

        BlockingQueue<List<RawTransaction>> queue = new ArrayBlockingQueue<>(10);

        Thread transactionThread = new Thread(() -> {
            while (true) {
                try {
                    List<RawTransaction> rawTransactionList = new ArrayList<>();
                    int numberOfTransaction = random.nextInt(50)+1;

                    for (int i = 0; i < numberOfTransaction; i++) {
                        rawTransactionList.add(createRandomTransaction());
                    }
                    queue.put(rawTransactionList);

                    Thread.sleep(random.nextInt(3000)+3000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread consumerThread = new Thread(() -> {
            TransactionEnricher transactionEnricher = new TransactionEnricher(messages -> {
                for (EnrichedTransaction et: messages) {
                    System.out.println(et.toString());
                    System.out.println("----------");
                }
            });

            while (true) {
                try {
                    List<RawTransaction> transactions = queue.take();
                    transactionEnricher.consume(transactions);

                    Thread.sleep(random.nextInt(3000)+6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        transactionThread.start();
        consumerThread.start();

        transactionThread.join();
        consumerThread.join();

    }

    private static RawTransaction createRandomTransaction() {
        return new RawTransaction(++transactionCounter, new Date(), generateListOfProducts());
    }

    private static List<Product> generateListOfProducts() {
        List<Product> listOfProducts = new ArrayList<>();
        int numberOfProducts = random.nextInt(20)+1;
        for (int i = 0; i < numberOfProducts; i++) {
            listOfProducts.add(createRandomProduct());
        }

        return listOfProducts;
    }

   private static Product createRandomProduct() {
        int productNumber = random.nextInt(100);
        String productName = "Product"+productNumber;
        double productPrice = Math.round(random.nextDouble() * 10000);
        productPrice /= 100;

        return new Product(productName, productPrice);

    }


}
