/*
*---------------------------------INFO-------------------------------------------*
|        [*]Ονοματεπώνυμο:: Αναστάσιος Παντζαρτζής (Anastasios Pantzartzis)      |                                                         |
|        [*]Email::apantzar@csd.auth.gr                                          |
*--------------------------------------------------------------------------------*
 */


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Cores {


    public static void main(String[] args) throws IOException {

        int coreValue = 0;      //first line of the txt will be stored here(coreValue is equal to zero(0)).

        //arrayListNumber1: user demands -> number of core
        ArrayList<Integer> arrayListNumber1 = new ArrayList<>();

        //arrayListNumber2: price provided / core.
        ArrayList<Float> arrayListNumber2 = new ArrayList<>();

        try (BufferedReader in = new BufferedReader( new FileReader((args[0])));)
        {
            String stringL1;

            int lineCounter=1;

            //reading from file txt
            while ((stringL1 = in.readLine()) != null)
            {
                if (lineCounter==1){

                    //convert INT --> STRING
                    coreValue=Integer.parseInt(stringL1);
                    lineCounter++;


                    continue;
                }

                //put lines been spited in file:in dif variables

                String[] stringL2=stringL1.split(" ");
                int xValue=Integer.parseInt(stringL2[0]);
                float yValue=Float.parseFloat(stringL2[1]);



                //adding the element to our arraylist
                arrayListNumber1.add(xValue);
                arrayListNumber2.add(yValue);
                lineCounter++;
            }
        }



        /*
        *---------------------------------------------------------------------------------------------------------------------------*
            [*]exercise 1...
            [*] using coins
            INFO :: MIN COINS (Minimum)
                      --->https://stackoverflow.com/questions/4247662/the-minimum-number-of-coins-the-sum-of-which-is-s/4247707
        *----------------------------------------------------------------------------------------------------------------------------*
         */

        //coins array
        int coins[] = {1, 2, 7, 11};
        Minimum minValueDynamic = new Minimum();

        for (Integer x:arrayListNumber1) {
            minValueDynamic.minCoinDownToUp(x, coins, arrayListNumber1.indexOf(x));
        }



        /*
             [*] exercise 2 ..
             [*] using knapsack
         */
        knapsackClass knapValue = new knapsackClass();
        knapValue.algorithUsedInKnapsack(arrayListNumber2, arrayListNumber1, coreValue);
    }



}
/*
    INFO FOR knapsackClass ::
       -->https://en.wikipedia.org/wiki/Knapsack_problem
       -->https://medium.com/@fabianterh/how-to-solve-the-knapsack-problem-with-dynamic-programming-eb88c706d3cf
 */

 class knapsackClass {

    /**
     * @param pricePercore -> price/core
     * @param coreTotal    -> the value:: coresUsing the client needs
     * @param coresUsing   -> total coresUsing
     */


    /*
     Using Knapsack algorithm
      ->[*] Creates total2d array.
      ->[*] Fill Array : if coreTotal is bigger than the current coresUsing(jCounter) then "total" is equal with the above.
      ->Else becomes the  value MAX from (^) above and (one:: -1 row and :: -coreTotal)
       total price that the client "SUBMITS".


     */
    public void algorithUsedInKnapsack(ArrayList pricePercore, ArrayList coreTotal, int coresUsing) {


        float total2d[][] = new float[pricePercore.size() + 1][coresUsing + 1];

        for (int iCounter = 1; iCounter < pricePercore.size() + 1; iCounter++) {
            total2d[0][iCounter]=0;
            float price = (float) pricePercore.get(iCounter - 1); //VALUE OF PRICE
            int coreAm = (int) coreTotal.get(iCounter - 1);
            for (int jCounter = 1; jCounter < coresUsing + 1; jCounter++) {
                
                if (coreAm > jCounter)
                    total2d[iCounter][jCounter] = total2d[iCounter - 1][jCounter];
                else
                    total2d[iCounter][jCounter] = Math.max(total2d[iCounter - 1][jCounter], total2d[iCounter - 1][jCounter - coreAm] + price * coreAm);
            }
        }
        magicPrinter(pricePercore, coreTotal, coresUsing, total2d);
    }

    /**
     *     [*}Using magicPrinter in order to print results
     *     [*]Select client:
     *       ->change when above (^) number is not equal to (this) current
     */


    public void magicPrinter(ArrayList ar, ArrayList coreTotal, int numberOfCores, float[][] total2d) {
        int valueForSize = ar.size();
        int valueForSizer = numberOfCores;

        String sString;
        sString = "Clients accepted: ";
        while (valueForSize > 0 && valueForSizer > 0) {
            if (total2d[valueForSize][valueForSizer] == total2d[valueForSize - 1][valueForSizer]) {
                valueForSize -= 1;
            }
            else {


                sString += valueForSize;
                valueForSize -= 1;
                valueForSizer -= (int) coreTotal.get(valueForSize);
                if (valueForSize > 0 && valueForSizer > 0) {
                    sString += ",";
                }
            }
        }
        //System.out.println(sString.substring(0, sString.length() - 1));
        System.out.println("Total amount: " + total2d[ar.size()][numberOfCores]);
    }


}


class Minimum {

    /**

     * @param totalValue   -> Amount of cores asked from client.
     * @param comOfCoins   -> The combinations of Coins.
     * @param numberOfClient -> Number of clients
     */


    /*

     [*] Min coin Down to Up to solve the problem (D->U).
     [*] If the input != sorted:
            -> Otherwise jCounter - exampleArr[iCounter] + 1 will be  Integer.Max_value + 1 (possible to be low negative)
     [*] Finally, (Integer.MAX_VALUE - 1) if solution is not possible enough.

     */


    public void minCoinDownToUp(int totalValue, int comOfCoins[], int numberOfClient){

        int iTAr[] = new int[totalValue + 1];
        int iRAr[] = new int[totalValue + 1];

        //init first element -> 0
        iTAr[0] = 0;

        for(int iCounter=1; iCounter <= totalValue; iCounter++){

            iTAr[iCounter] = Integer.MAX_VALUE-1;
            iRAr[iCounter] = -1;
        }
        for(int jCounter=0; jCounter < comOfCoins.length; jCounter++){

            for(int internalIcounter=1; internalIcounter <= totalValue; internalIcounter++){
                if(internalIcounter >= comOfCoins[jCounter]){

                    if (iTAr[internalIcounter - comOfCoins[jCounter]] + 1 < iTAr[internalIcounter]) {
                        iTAr[internalIcounter] = 1 + iTAr[internalIcounter - comOfCoins[jCounter]];
                        iRAr[internalIcounter] = jCounter;
                    }
                }
            }
        }
        magicPrinterCompinationOfCoins(iRAr, comOfCoins,numberOfClient);

    }

    private void magicPrinterCompinationOfCoins(int rArray[], int strCoinsAr[], int numberOfClient) {
        if (rArray[rArray.length - 1] == -1) {
            System.out.print("No solution is possible");
            return;
        }
        /*
            [*] adder: icores needed
            [*] adder: starts from zero(0)
                -> is int => init in zero when created
            [*]dynamic allocation
                ->for adder using "new"
         */


        int adder[]=new int[strCoinsAr.length];

        //startPoint -> list size - 1

        int startPoint = rArray.length - 1;

        while ( startPoint != 0 ) {
            int xCounter = rArray[startPoint];
            adder[xCounter]+=1;
            startPoint = startPoint - strCoinsAr[xCounter];
        }


        /*

            [*] Let's print :: printing results for client
            [*] limit < strCoinsAr
         */
        System.out.print("Client "+(numberOfClient+1)+":");
        int value1 =0;
        int value2=0;
        int value3 =0;
        int valuetotal=0;
        for (int printerCounter=0;printerCounter<strCoinsAr.length;printerCounter++){




            if(printerCounter==0)//Checking::if printerCounter is zero
                value1=adder[printerCounter];

                //System.out.printf(" %d %d-core", adder[printerCounter],strCoinsAr[printerCounter]);//PRINT FOR TEST

            else if(printerCounter!=strCoinsAr.length-1)
                //System.out.printf(", %d %d-core", adder[printerCounter],strCoinsAr[printerCounter]);//PRINT FOR TEST
                value2=adder[printerCounter];
                //strCoinsAr[printerCounter]= strCoinsAr[printerCounter];

            else {
                //System.out.printf(" and %d %d-core ", adder[printerCounter],strCoinsAr[printerCounter]);//PRINT FOR TEST
                value3 = adder[printerCounter];
            }

            valuetotal = value1 + value2 + value3;
           // System.out.printf(" %d",valuetotal);//PRINT FOR TEST




        }
        System.out.printf(" %d ",valuetotal);
        System.out.println("VMs");//PRINT
    }
}




