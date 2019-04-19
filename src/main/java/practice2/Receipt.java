package practice2;

import java.math.BigDecimal;
import java.util.List;

public class Receipt {

    public Receipt() {
        tax = new BigDecimal(0.1);
        tax = tax.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal tax;

    public double CalculateGrandTotal(List<Product> products, List<OrderItem> items) {
        BigDecimal subTotal = subTotalAfterReducePrice(products, items, calculateSubtotal(products, items));

        BigDecimal taxTotal = subTotal.multiply(tax);
        BigDecimal grandTotal = subTotal.add(taxTotal);

        return grandTotal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private BigDecimal subTotalAfterReducePrice(List<Product> products, List<OrderItem> items, BigDecimal subTotal) {
        for (Product product : products) {
            subTotal = subTotal.subtract(taxReduceForProduct(items, product));
        }
        return subTotal;
    }

    private BigDecimal taxReduceForProduct(List<OrderItem> items, Product product) {
        OrderItem curItem = findOrderItemByProduct(items, product);

        return product.getPrice()
                .multiply(product.getDiscountRate())
                .multiply(new BigDecimal(curItem.getCount()));
    }


    private OrderItem findOrderItemByProduct(List<OrderItem> items, Product product) {
        for (OrderItem item : items) {
            if (item.getCode() == product.getCode()) return item;
        }
        return null;
    }

    private BigDecimal calculateSubtotal(List<Product> products, List<OrderItem> items) {
        BigDecimal subTotal = new BigDecimal(0);
        for (Product product : products) {
            OrderItem item = findOrderItemByProduct(items, product);
            BigDecimal itemTotal = product.getPrice().multiply(new BigDecimal(item.getCount()));
            subTotal = subTotal.add(itemTotal);
        }
        return subTotal;
    }
}
