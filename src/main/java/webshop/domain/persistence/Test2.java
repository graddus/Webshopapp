package webshop.domain.persistence;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import webshop.domain.klant.Categorie;
import webshop.domain.klant.Product;  
public class Test2 {  
public static void main(String[] args) throws IOException {  
ProductDAO dao=new ProductDAO();
Product p=dao.getProductByID(2);
int counter=0;
for (Categorie c:dao.getAllCategories()){
	ImageIO.write(c.getAfbeelding(), "jpg",new File("D:\\categorie"+counter+".jpg"));
	counter+=1;
}
ImageIO.write(p.getAfbeelding(), "jpg",new File("D:\\product.jpg"));
}}