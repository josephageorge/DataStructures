package warehouse;

/*
 * Use this class to put it all together.
 */ 
public class Everything {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

        Warehouse newWarehouse = new Warehouse();

        int n = StdIn.readInt();

        for (int i=0; i<n; i++){
            String queryType = StdIn.readString();
            if (queryType.equals("add")){
                int day = StdIn.readInt();
                int ID = StdIn.readInt();
                String prodName = StdIn.readString();
                int stock = StdIn.readInt();
                int demand = StdIn.readInt();
                newWarehouse.addProduct(ID, prodName, stock, day, demand);

            }
            
            if (queryType.equals("restock")){
                int ID = StdIn.readInt();
                int restock = StdIn.readInt();
                newWarehouse.restockProduct(ID, restock);
            }

             
            if (queryType.equals("purchase")){
                int day = StdIn.readInt();
                int ID = StdIn.readInt();
                int amount = StdIn.readInt();
                newWarehouse.purchaseProduct(ID, day, amount);;
            }


            if (queryType.equals("delete")){
                int ID = StdIn.readInt();
                newWarehouse.deleteProduct(ID);
            }
            
        }

        StdOut.println(newWarehouse);       
    }
}