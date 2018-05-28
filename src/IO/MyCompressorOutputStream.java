package IO;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class MyCompressorOutputStream extends OutputStream {
    private OutputStream out;

    public MyCompressorOutputStream(OutputStream out) {
        this.out=out;
    }

    /**
     * this function does the compression of an array of byte.
     * The compression starts with 1.
     * @param b - the byte's array which will be compress.
     * @throws IOException - exception
     */
    @Override
    public void write(byte[] b) throws IOException {
        ArrayList<Byte> compressArray = new ArrayList<>();
        int i = first_part_of_compression(compressArray,b);
        second_part_of_compression(compressArray,b,i);
        byte[] to_return = arrayList_2array(compressArray);
        out.write(to_return);
    }


    private int first_part_of_compression(ArrayList<Byte> compressArray, byte[] b){
        int last_comma = 0;
        int location_in_b = 0;
        int i;
        for (i = 0; i < b.length; i++) {
            if (location_in_b < 6) {
                if (b[i] == 0) {
                    if (b[i - 1] != 0) {
                        location_in_b++;
                        last_comma = i;
                    } else {
                        if (last_comma != i - 1) {
                            location_in_b++;
                            last_comma = i;
                        }
                    }
                }
                compressArray.add(b[i]);
            }
            else break;
        }
        return i;
    }


    private void second_part_of_compression(ArrayList<Byte> compressArray, byte[] b, int start){

        int counter = 0;
        int current_byte = 1;
        for (int i = start; i <= b.length; i++) {
            if(i <= b.length - 1 && b[i] == current_byte){
                counter++;
            }
            else if(i == b.length || b[i] != current_byte){
                if(counter > 255){
                    int amount = counter/255;
                    byte to_insert = (byte) 255;
                    byte rest = (byte)(counter - counter * amount);
                    while(amount > 0){
                        compressArray.add(to_insert);
                        amount--;
                    }
                    if(rest > 0)
                        compressArray.add(rest);
                }
                else{
                    byte to_insert = (byte) counter;
                    compressArray.add(to_insert);
                }
                current_byte = Change_current_byte(current_byte);
                counter = 1;
            }
        }
    }

    /**
     * changing the current byte from 0 to 1 or from 1 to 0
     * @param current - the current byte
     * @return the opposite from the current byte
     */
    private int Change_current_byte(int current){
        if(current == 0)
            return 1;
        return 0;
    }

    private byte[] arrayList_2array(ArrayList<Byte> to_array_of_Bytes){
        byte[] to_return = new byte[to_array_of_Bytes.size()];
        int index=0;
        for (Byte b: to_array_of_Bytes){
            to_return[index] = b;
            index++;
        }
        return to_return;
    }

    @Override
    public void write(int b) throws IOException {

    }
}
