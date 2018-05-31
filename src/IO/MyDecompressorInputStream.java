package IO;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {
    private InputStream in;

    public MyDecompressorInputStream(InputStream in){
        this.in = in;

    }

    @Override
    public int read(byte[] b) throws IOException {
        //return super.read(b);
        byte[] compress_array = in.readAllBytes();
        decompress_the_array(b,compress_array);
        return b.length;
    }

    private void decompress_the_array(byte[] b, byte[] compress_array){
        int beginning_of_data_compress=first_part_of_compression(b, compress_array);
        decompress_the_second_part(b, compress_array,beginning_of_data_compress);
    }

    private int first_part_of_compression( byte[] b, byte[] compressArray){
        int last_comma = 0;
        int location_in_compressArray = 0;
        int i;
        for (i = 0; i < compressArray.length; i++) {
            if (location_in_compressArray < 6) {
                if (compressArray[i] == 0) {
                    if (compressArray[i - 1] != 0) {
                        location_in_compressArray++;
                        last_comma = i;
                    } else {
                        if (last_comma != i - 1) {
                            location_in_compressArray+=1;
                            last_comma = i;
                        }
                    }
                }
                b[i]=compressArray[i];
            }
            else if (location_in_compressArray >= 6)
                break;
        }
        return i;
    }

    private void decompress_the_second_part(byte[] b, byte[] compressArray, int start){
        int counter;
        byte current_byte = 1;
        int index_of_b_the_newDecompress=start;
        for (int i = start; i < compressArray.length; i++) {
            counter = compressArray[i] & 0xFF;
            while (counter > 0) {
                b[index_of_b_the_newDecompress] = current_byte;
                index_of_b_the_newDecompress++;
                counter--;
            }
            current_byte = Change_current_byte(current_byte);
        }
    }

    /**
     * changing the current byte from 0 to 1 or from 1 to 0
     * @param current - the current byte
     * @return the opposite from the current byte
     */
    private byte Change_current_byte(byte current){
        if(current == 0)
            return 1;
        return 0;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }
}