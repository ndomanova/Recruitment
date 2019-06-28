package com.awin.recruitment;
import com.awin.recruitment.entities.EnrichedTransaction;
import com.awin.recruitment.entities.RawTransaction;
import com.awin.recruitment.library.Consumer;
import com.awin.recruitment.library.Producer;

import java.util.ArrayList;
import java.util.List;

public class TransactionEnricher implements Consumer<RawTransaction> {


    private Producer<EnrichedTransaction> producer;

    public TransactionEnricher(Producer<EnrichedTransaction> producer) {
        this.producer = producer;
    }

    @Override
    public void consume(Iterable<RawTransaction> messages) {
        List<EnrichedTransaction> transactions = new ArrayList<>();
        for (RawTransaction rt: messages) {
            transactions.add(enrichTransaction(rt));
        }

        producer.produce(transactions);
    }

    private static EnrichedTransaction enrichTransaction(RawTransaction transaction) {
        return new EnrichedTransaction(transaction);
    }
}
