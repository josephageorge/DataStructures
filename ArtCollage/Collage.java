import java.awt.Color;

/*
 * This class contains methods to create and perform operations on a collage of images.
 * 
 * @author Ana Paula Centeno
 */ 

import java.awt.Color;


public class ArtCollage {

    // The orginal picture
    private Picture original;

    // The collage picture
    private Picture collage;

    // The collage Picture consists of collageDimension X collageDimension tiles
    private int collageDimension;

    // A tile consists of tileDimension X tileDimension pixels
    private int tileDimension;
    
    /*
     * One-argument Constructor
     * 1. set default values of collageDimension to 4 and tileDimension to 100
     * 2. initializes original with the filename image
     * 3. initializes collage as a Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see all constructors for the Picture class).
     * 4. update collage to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */
    public ArtCollage (String filename) {
        //1
        this.collageDimension = 4;
        this.tileDimension = 100;

        //2 
        original = new Picture(filename);

        //3
        // make collage to be a black height by height pixel picture
        int height = collageDimension * tileDimension;
        collage = new Picture(height, height);

        //4
        //squish original picture into a black height by height pixel collage
        scale(height, height, original, collage);

        /*
        //1
        // Picture o = new Picture(filename);
        // this.original = o;
        // 400 = 4 x 100
        int height = collageDimension * tileDimension;
        Picture target = new Picture(filename);
        // Take the black picture of 400x400 and squish the original picture into it
        scale(height,height,new Picture(filename),target);
        // just set the original to target
        // now Picture original is a scaled 400x400 version of the picture
        this.original = target;
        
        //1
        //Picture a = new Picture(height, height);
        //collage = a;
        //2
        collage = new Picture(height, height);
        //4. Scale
        Picture mini = new Picture(100, 100);
        scale(100, 100, original, mini);
        //for(int i = 0; i < original.length; i++)
        */
	// WRITE YOUR CODE HERE
    }

    /*
     * Three-arguments Constructor
     * 1. set default values of collageDimension to cd and tileDimension to td
     * 2. initializes original with the filename image
     * 3. initializes collage as a Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see all constructors for the Picture class).
     * 4. update collage to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */                                  //200    // 3
    public ArtCollage (String filename, int td, int cd) {
        this.collageDimension = cd;
        this.tileDimension = td;
        original = new Picture(filename);
        int widthHeight = tileDimension * collageDimension;
        collage = new Picture(widthHeight, widthHeight);
        scale(widthHeight, widthHeight, original, collage);

	// WRITE YOUR CODE HERE
    

    }

    /*
     * Returns the collageDimension instance variable
     *
     * @return collageDimension
     */
    public int getCollageDimension() {
        return collageDimension;
	// WRITE YOUR CODE HERE
    }

    /*
     * Returns the tileDimension instance variable
     *
     * @return tileDimension
     */
    public int getTileDimension() {

	// WRITE YOUR CODE HERE
        return tileDimension;
    }

    /*
     * Returns original instance variable
     *
     * @return original
     */
    public Picture getOriginalPicture() {

	// WRITE YOUR CODE HERE
        return original;
    }

    /*
     * Returns collage instance variable
     *
     * @return collage
     */
    public Picture getCollagePicture() {
        return collage;
	// WRITE YOUR CODE HERE
    }
    
    /*
     * Display the original image
     * Assumes that original has been initialized
     */
    public void showOriginalPicture() {
        original.show();
	// WRITE YOUR CODE HERE
    }

    /*
     * Display the collage image
     * Assumes that collage has been initialized
     */
    public void showCollagePicture() {
        collage.show();
	// WRITE YOUR CODE HERE
    }

    /*
     * Replaces the tile at collageCol,collageRow with the image from filename
     * Tile (0,0) is the upper leftmost tile
     *
     * @param filename image to replace tile
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void replaceTile (String filename,  int collageCol, int collageRow) {
        Picture replacement = new Picture(filename); // will create a replacement picture from the argument given from the command line
        Picture scaledReplacement = new Picture( tileDimension, tileDimension );
        // creates the scaled image from the new image to fit the tile existing 

        scale(tileDimension, tileDimension, replacement, scaledReplacement);
        // scales the image to the tile dimension u want using the scale method

        for(int i = 0; i< collage.height()/tileDimension; i++) {
            for(int j = 0; j< collage.height()/tileDimension; j++) {
                if ( j == collageCol && i == collageRow ) {
                    //copy the image and replace it into this tile
                    copy (i*tileDimension, j*tileDimension, scaledReplacement, collage);
                }
            }
        }

	// WRITE YOUR CODE HERE
    }
    
    /*
     * Makes a collage of tiles from original Picture
     * original will have collageDimension x collageDimension tiles, each tile
     * has tileDimension X tileDimension pixels
     */
    public void makeCollage () {

        //scaledOriginal = the mini version of the original picture
        Picture scaledOriginal = new Picture(tileDimension, tileDimension);
        scale(tileDimension, tileDimension, original, scaledOriginal);
                            
        // copy the mini version of original into the collage a bunch of times
        for (int i = 0; i < collage.height()/tileDimension; i++) {
            for (int j = 0; j < collage.height()/tileDimension; j++) {
                    //i * 600 = i * collage.height()
                copy(i*tileDimension, j*tileDimension, scaledOriginal, collage);
                //collage.show();
                // System.out.print(i*target.height());
                // System.out.print(", ");
                // System.out.print(j*target.height());
            }

            // System.out.println();
        }
        // collage.show();
	// WRITE YOUR CODE HERE
    }

    //helper method to change the image of a tile to a specific component
    // public void changeColor ( String component, int row, int column) {
    //     for( int i = 0; i < tileDimension; i++) {
    //         for (int j = 0; j < tileDimension; j++) {
    //             // go through each pixel and call the setRGB method to change each 

    //         }
    //     }
    // }

    /*
     * Colorizes the tile at (collageCol, collageRow) with component 
     * (see Week 9 slides, the code for color separation is at the 
     *  book's website)
     *
     * @param component is either red, blue or green
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void colorizeTile (String component,  int collageCol, int collageRow) {
        /*if(component.equals("red")) {
            //Color color = Color.parseColor(component);
            int rgb = getRed();
        }
        else if(component.equals("blue")) {
            //Color color = Color.parseColor(component);
            int rgb = getBlue();
        }
        else if(component.equals("green")) {
            //Color color = Color.parseColor(component);
            int rgb = getGreen();
        } */
        
        // Picture pictureRed = new Picture(tileDimension, tileDimension);
        // System.out.println(tileDimension);
        // Picture pictureGreen = new Picture(tileDimension, tileDimension);
        // Picture pictureBlue = new Picture(tileDimension, tileDimension);
        for( int i = 0; i < collage.height()/tileDimension; i++) {
            for (int j = 0; j < collage.height()/tileDimension; j++) {
                //System.out.println(j);
                 // go through each row and column to check if each tile args is the same as the input and call the setRGB method to change each 
                if ( j == collageCol && i == collageRow ) {
                    //Picture pictureTile = new Picture(i,j);

                    for (int pixelRow = 0; pixelRow < tileDimension ; pixelRow++) {

                        for (int pixelCol = 0; pixelCol < tileDimension; pixelCol++) {
                            // will go through each individual pixel and iterate through until the tile dimension has been reached and change the color 
                            //System.out.println(pixelCol);
                            Color color = collage.get(pixelCol + (tileDimension * j), pixelRow + (tileDimension * i));
                            // need to figure out a way to get the color components of a pixel
                            int r = color.getRed();
                            int g = color.getGreen();
                            int b = color.getBlue();
                                if(component.equals("red")) {
                                    collage.set(pixelCol + (tileDimension * j), pixelRow + (tileDimension * i), new Color(r, 0, 0));
                                }
                                else if(component.equals("green")) {
                                    collage.set(pixelCol + (tileDimension * j), pixelRow + (tileDimension * i), new Color(0, g, 0));
                                }
                                else if(component.equals("blue")){
                                    collage.set(pixelCol + (tileDimension * j), pixelRow + (tileDimension * i), new Color(0, 0, b));
                                }
                        }
                    }
                }
            }
        }
	// WRITE YOUR CODE HERE
    }

    /*
     * Greyscale tile at (collageCol, collageRow)
     * (see Week 9 slides, the code for luminance is at the book's website)
     *
     * @param collageCol tile column
     * @param collageRow tile row
     */
    // create helper method luminance for lum 
    public static double lum(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();   
        // int gray = (int)Math.round(.299*r + .587*g + .114*b); 
        // return gray;
        return .299*r + .587*g + .114*b;
    }

    public void greyscaleTile (int collageCol, int collageRow) {
        // same logic of capturing each individual color from each thing/ pixel and getting the components and then calling lum on every pixel
        for( int i = 0; i < collage.height()/tileDimension; i++) {
            for (int j = 0; j < collage.height()/tileDimension; j++) {
                if ( j == collageCol && i == collageRow ) {
                    //Picture pictureTile = new Picture(i,j);
                    for (int pixelRow = 0; pixelRow < tileDimension ; pixelRow++) {

                        for (int pixelCol = 0; pixelCol < tileDimension; pixelCol++) {
                            // get the components of color of each pixel
                            Color color = collage.get(pixelCol + (tileDimension * j), pixelRow + (tileDimension * i));
                            int y = (int) Math.round(lum(color));
                            Color gray = new Color(y, y, y);
                            collage.set(pixelCol + (tileDimension * j), pixelRow + (tileDimension * i), gray);
                        }
                    }
                }
            }
        }


	// WRITE YOUR CODE HERE
    }


    public static void scale(int w, int h, Picture source, Picture target) {

        for (int tcol = 0; tcol < w; tcol++) {
            for (int trow = 0; trow < h; trow++)
            {
                int scol = tcol * source.width() / w;
                int srow = trow * source.height() / h;
                Color color = source.get(scol, srow);
                target.set(tcol, trow, color);
            }
        }
    }

    public static void copy(int row, int col, Picture source, Picture target) {
        for (int i = 0; i < source.height(); i++) {
            for (int j = 0; j < source.height(); j++) {

                // bug
                // Color color = source.get(i+col, j+row);
                Color color = source.get(i, j);
                target.set(i+col, j+row, color);
            }
        }   
    }
        

    // Test client 
    public static void main (String[] args) {

        //creates new art collage and declares it
       ArtCollage ac = new ArtCollage("Ariel.jpg", 200, 3);
       ac.makeCollage();
       ac.showOriginalPicture();
       ac.showCollagePicture();


       //Picture source = new Picture("Ariel.jpg");
       //  //Picture target = new Picture(400, 400);
       //  scale(400, 400, source, target);
       // //source.show();
       // // target.show();

       //  Picture toCopyInto = new Picture(800,800);
       // //  // copy(0,0, target, toCopyInto);
       // //  // copy(0, 400, target, toCopyInto);
       // //  // copy(400,0, target, toCopyInto);
       // //  // copy(400, 400, target, toCopyInto);
       // //  // toCopyInto.show();

       // //  //                      800 / 400 = 2
       //  for (int i = 0; i < toCopyInto.height()/target.height(); i++) {
       //      for (int j = 0; j < toCopyInto.height()/target.height(); j++) {
       //          copy(i*target.height(), j*target.height(), target, toCopyInto);
       //          // System.out.print(i*target.height());
       //          // System.out.print(", ");
       //          // System.out.print(j*target.height());
       //      }

       //      //System.out.println();
       //  }
       //  //toCopyInto.show();

       // //  System.out.println("s");


        // test for replacetile function
       ac.replaceTile("Flo.jpg", 1, 1);
       ac.showCollagePicture();

       ac.colorizeTile("blue",2,1);
       ac.showCollagePicture();
       ac.colorizeTile("red",2,2);
       ac.showCollagePicture();
       ac.colorizeTile("green",0,0);
       ac.showCollagePicture();
       ac.greyscaleTile(1, 0);
       ac.showCollagePicture();
       ArtCollage art = new ArtCollage("Flo.jpg");
       art.makeCollage();
       art.showCollagePicture();
       ArtCollage lastTest = new ArtCollage(args[0], 200, 2);
       lastTest.makeCollage();
        // Replace 3 tiles 
       lastTest.replaceTile(args[1],0,1);
       lastTest.replaceTile(args[2],1,0);
       lastTest.replaceTile(args[3],1,1);
       lastTest.colorizeTile("green",0,0);
       lastTest.showCollagePicture();
       // it works omgggg

    }
}