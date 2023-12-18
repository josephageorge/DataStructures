package warehouse;

/*
 * Use this class to test to addProduct method.
 */
public class AddProduct {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
        Warehouse newWarehouse = new Warehouse();

        int numProducts = StdIn.readInt();
        
        for (int i= 0; i<numProducts; i++){
            int prodDay = StdIn.readInt();
            int prodId = StdIn.readInt();
            String prodName = StdIn.readString();
            int prodStock = StdIn.readInt();
            int prodDemand = StdIn.readInt();
            newWarehouse.addProduct(prodId, prodName, prodStock, prodDay, prodDemand);
            
        }
        StdOut.println(newWarehouse);

	// Use this file to test addProduct
    }
}