package com.bunich.productparser;

import java.io.*;

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
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        StringBuffer output = new StringBuffer();
        try {
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                output.append(line);
                output.append(System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bufferedReader.close();
        }
        return output.toString();
    }
/*
   public static String getProductString(File productFile){

        return
    }
*/
}
