package practice3;

import java.math.BigDecimal;
import java.util.List;

public class Order {

    private List<OrderLineItem> orderLineItemList;
    private List<BigDecimal> discounts;
    private BigDecimal tax;

    public Order(List<OrderLineItem> orderLineItemList, List<BigDecimal> discounts) {
        this.orderLineItemList = orderLineItemList;
        this.discounts = discounts;
        this.tax = new BigDecimal(0.1);
    }

    public BigDecimal calculate() {
        return new Tax().compute();
    }

    private class Tax {
        BigDecimal subTotal = new BigDecimal(0);
        private BigDecimal taxTotal;

        public Tax() {
        }

        public BigDecimal compute() {
            addItemsPrice();
            subtractDsicounts();

            // calculate taxTotal
            taxTotal = subTotal.multiply(Order.this.tax);

            return subTotal.add(taxTotal);
        }

        private void subtractDsicounts() {
            // Subtract discounts
            for (BigDecimal discount : discounts) {
                subTotal = subTotal.subtract(discount);
            }
        }

        private void addItemsPrice() {
            // Total up line items
            for (OrderLineItem lineItem : orderLineItemList) {
                subTotal = subTotal.add(lineItem.getPrice());
            }
        }
    }
}
