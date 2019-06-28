package com.awin.recruitment

import com.awin.recruitment.entities.EnrichedTransaction
import com.awin.recruitment.entities.Product
import com.awin.recruitment.entities.RawTransaction
import spock.lang.Specification

class TransactionEnricherTest extends Specification {

    def "Empty product list"() {
        given:
        RawTransaction emptyTransaction = new RawTransaction(001, new Date(), new ArrayList<Product>())

        when:
        EnrichedTransaction enrichedTransaction = TransactionEnricher.enrichTransaction(emptyTransaction)

        then:
        enrichedTransaction.getTotalBill() == 0
    }

    def "Single product list"() {
        given:
        List<Product> products = new ArrayList()
        Product p1 = new Product("Product1", 23.50)
        products.add(p1)

        RawTransaction rt = new RawTransaction(002, new Date(), products)

        when:
        EnrichedTransaction et = TransactionEnricher.enrichTransaction(rt)

        then:
        et.getTotalBill() == 23.50
    }

    def "Multiple product list"() {
        given:
        List<Product> products = new ArrayList()
        Product p1 = new Product("Product1", 23.50)
        Product p2 = new Product("Product2", 11.20)
        Product p3 = new Product("Product3", 17.99)
        products.addAll(p1, p2, p3)

        RawTransaction rt = new RawTransaction(003, new Date(), products)

        when:
        EnrichedTransaction et = TransactionEnricher.enrichTransaction(rt)

        then:
        et.getTotalBill() == 52.69
    }

    def "Repeated product list"() {
        given:
        List<Product> products = new ArrayList()
        Product p1 = new Product("Product1", 23.50)
        products.add(p1)
        products.add(p1)
        products.add(p1)

        RawTransaction rt = new RawTransaction(004, new Date(), products)

        when:
        EnrichedTransaction et = TransactionEnricher.enrichTransaction(rt)

        then:
        et.getTotalBill() == 70.50
    }

}
