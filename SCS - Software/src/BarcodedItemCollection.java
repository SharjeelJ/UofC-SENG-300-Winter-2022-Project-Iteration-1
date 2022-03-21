import java.math.BigDecimal;
import java.util.ArrayList;

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
        BigDecimal price;
        for(int i = 0; i < items.size(); i++)
        {
           if(items.get(i).getBarcode())
        }
        return price;
    }
}