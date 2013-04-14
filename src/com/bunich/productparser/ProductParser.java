package com.bunich.productparser;

import java.io.*;

public class ProductParser {

    public static void main(String args[]){
        try {
            createXMLFile(createProductFileList(args), "products.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File createXMLFile(File files[], String path) throws IOException {
        File xml = new File(path);
        writeStringToFile("<?xml version=\"1.0\" encoding=\"UTF-8\"?><products>", xml);
        for (File f : files){
            writeProductsToXMLFromFile(f, xml);
        }
        writeStringToFile("</products>", xml);
        return xml;
    }

    private static void writeProductsToXMLFromFile(File f, File xml) throws IOException {
        for (String s : createStringFromFile(f).split("\n")){
            writeStringToFile(createXMLString(s), xml);
        }
    }

    private static void writeStringToFile(String string, File file) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "Cp1251"));
        try {
            bufferedWriter.write(string);
        } catch (IOException e) {
            throw e;
        }
    }
    public static String createXMLString(String string){
        String[] product = string.split(" ");
        StringBuffer xmlString = new StringBuffer("<product name=\"");
        xmlString.append(product[0]);
        xmlString.append("\"><date>");
        xmlString.append(product[3]);
        xmlString.append("</date><count>");
        xmlString.append(product[2]);
        xmlString.append("</count><price>");
        xmlString.append(product[1]);
        xmlString.append("</price><product>");
        return xmlString.toString();
    }

    public static File[] createProductFileList(String paths[]) {
        File[] fileList = new File[paths.length];
        for (int i = 0; i<paths.length; i++){
            fileList[i] = new File(paths[i]);
        }
        return fileList;
    }

    public static String createStringFromFile(File file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "Cp1251"));
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
}
