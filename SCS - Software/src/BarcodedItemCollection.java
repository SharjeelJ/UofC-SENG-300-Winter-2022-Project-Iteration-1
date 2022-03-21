import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.function.BiFunction;

import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.products.BarcodedProduct;

public class BarcodedItemCollection  
{
    ArrayList<BarcodedProduct> products = new ArrayList<BarcodedProduct>();
    ArrayList<BarcodedItem> items = new ArrayList<BarcodedItem>();

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
        for(int i = 0; i < items.size(); i++)
        {
           if(items.get(i).getBarcode() == barcode)
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
        for(int i = 0; i < items.size(); i++)
        {
           if(items.get(i).getBarcode() == barcode)
           {
                weight = items.get(i).getWeight();
                break;
           }
        }
        return weight;
    }
}