package com.bunich.productparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ProductParser {

    public static void main(String args[]){
        for(String s: args){
            System.out.println(s);
        }
        for (File f : createProductFileList(args)){
            try {
                System.out.println(createStringFromFile(f));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static File[] createProductFileList(String paths[]) {
        File[] fileList = new File[paths.length];
        for (int i = 0; i<paths.length; i++){
            fileList[i] = new File(paths[i]);
        }
        return fileList;
    }
    public static String createStringFromFile(File file) throws IOException {
        FileInputStream stream = new FileInputStream(file);
        try {
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            return Charset.defaultCharset().decode(bb).toString();
        }
        finally {
            stream.close();
        }
    }
/*
   public static String getProductString(File productFile){

        return
    }
*/
}
