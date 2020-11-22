package comparator;

import com.rainypeople.tmall.pojo.Product;

import java.util.Comparator;

public class ProductPriceComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        return p2.getPromotePrice().compareTo(p1.getPromotePrice());
    }
}
