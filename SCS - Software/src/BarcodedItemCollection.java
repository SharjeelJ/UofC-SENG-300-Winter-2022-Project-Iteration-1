import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.products.BarcodedProduct;

import java.math.BigDecimal;
import java.util.ArrayList;

public class BarcodedItemCollection
{
    ArrayList <BarcodedProduct> products = new ArrayList <>();
    ArrayList <BarcodedItem> items = new ArrayList <>();

    public void addItem(BarcodedItem item)
    {
        items.add(item);
    }

    public void addProduct(BarcodedProduct product)
    {
        products.add(product);

    }

    public BigDecimal getPrice(Barcode barcode)
    {
        BigDecimal price = null;
        for (int i = 0; i < items.size(); i++)
        {
            if (items.get(i).getBarcode() == barcode)
            {
                price = products.get(i).getPrice();
                break;
            }
        }

        return price;
    }

    public double getExpectedWeight(Barcode barcode)
    {
        double weight = 0.0;
        for (BarcodedItem item : items)
        {
            if (item.getBarcode() == barcode)
            {
                weight = item.getWeight();
                break;
            }
        }
        return weight;
    }
}
