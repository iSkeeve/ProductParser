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

    /**
     * Метод подготавливает список файлов для дальнейшей обработки.
     * @param paths массив путей к текстовым файлам, в которых хранятся списки продуктов.
     * @return Массив объектов класса File, содержащих списки продуктов.
     */
    public static File[] createProductFileList(String paths[]) {
        File[] fileList = new File[paths.length];
        for (int i = 0; i<paths.length; i++){
            fileList[i] = new File(paths[i]);
        }
        return fileList;
    }

    /**
     * Создает xml файл содержащий информацию по продуктам содержащимся в текстовых файлах из массива files[]
     * @param files массив тектовых файлов, содержащих информацию по продуктам
     * @param path путь к создаваемому xml файлу.
     * @return xml файл, с информацией по продуктам.
     * @throws IOException
     */
    public static File createXMLFile(File files[], String path) throws IOException {
        File xml = new File(path);
        prepareFileForWriting(xml);
        writeStringToFile(String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?>%n<products>"), xml);
        /*Делаем запись для каждого продукта из списка files[]*/
        for (File f : files){
            writeProductsToXMLFromFile(f, xml);
        }
        writeStringToFile(String.format("%n</products>"), xml);
        return xml;
    }

    private static void prepareFileForWriting(File file) throws IOException {
        /*Если файл не существует создаем новый*/
        if (!file.createNewFile()){
        /*Если существует, стираем содержимое файла*/
            writeStringToFile("", file, false);
        }
    }

    private static void writeProductsToXMLFromFile(File f, File xml) throws IOException {
        for (String s : createStringFromFile(f).split(System.getProperty("line.separator"))){
            /*Сабстринг нужен для удаления разделителя строк*/
            writeStringToFile(createXMLString(s.substring(0, s.length()-1)), xml);
        }
    }

    private static void writeStringToFile(String string, File file) throws IOException {
        writeStringToFile(string, file, true);
    }

    private static void writeStringToFile(String string, File file, boolean appendable) throws IOException {
        PrintWriter printWriter = new PrintWriter(new FileWriter(file, appendable));
        printWriter.print(string);
        printWriter.close();
    }

    public static String createXMLString(String string){
        String[] product = string.split(" ");
        StringBuilder xmlString = new StringBuilder(String.format("%n\t<product name=\""));
        xmlString.append(product[0]);
        xmlString.append(String.format("\">%n\t\t<date>"));
        xmlString.append(product[3]);
        xmlString.append(String.format("</date>%n\t\t<count>"));
        xmlString.append(product[2]);
        xmlString.append(String.format("</count>%n\t\t<price>"));
        xmlString.append(product[1]);
        xmlString.append(String.format("</price>%n\t</product>"));
        return xmlString.toString();
    }

    public static String createStringFromFile(File file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "Cp1251"));
        StringBuilder output = new StringBuilder();
        try {
            String line;
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
