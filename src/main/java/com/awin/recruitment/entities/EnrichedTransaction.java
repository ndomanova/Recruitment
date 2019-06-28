package com.awin.recruitment.entities;


public class EnrichedTransaction extends RawTransaction {

    final private double totalBill;

    public EnrichedTransaction(RawTransaction transaction) {
        super(transaction.getId(), transaction.getDate(), transaction.getListOfProducts());
        totalBill = calculateTotalBill();
    }

    private double calculateTotalBill() {
        return getListOfProducts().stream().mapToDouble(Product::getPrice).sum();
    }

    public double getTotalBill() { return totalBill; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ");
        sb.append(this.getId());
        sb.append("\r\nDATE: ");
        sb.append(this.getDate());
        sb.append("\r\nPRODUCTS:\r\n");
        getListOfProducts().forEach(sb::append);
        sb.append("TOTAL SUM: ");
        sb.append(getTotalBill());
        return sb.toString();
    }
}
